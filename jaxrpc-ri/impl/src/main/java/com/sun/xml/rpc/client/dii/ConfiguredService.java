/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.xml.rpc.client.dii;

import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.TypeMapping;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.client.ServiceExceptionImpl;
import com.sun.xml.rpc.processor.modeler.ModelerException;
import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPEncodingConstants;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.util.Holders;

/**
 * @author JAX-RPC Development Team
 */

public class ConfiguredService extends BasicService {
    protected java.net.URL wsdlDocumentLocation;
    protected ServiceInfo configuration;
    protected DynamicProxyBuilder dynamicProxyBuilder;
    private ServiceException serviceException;
    private SOAPEncodingConstants soapEncodingConstants = null;

    private void init(SOAPVersion ver) {
        soapEncodingConstants =
                SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public ConfiguredService(QName name, URL wsdlLocation) {
        this(name, wsdlLocation, SOAPVersion.SOAP_11);
    }

    public ConfiguredService(QName name, URL wsdlLocation, SOAPVersion ver) {
        super(name);
        init(ver);
        //created by ServiceFactoryImpl.createService(service QName, wsdlLocaltion url)
        wsdlDocumentLocation = wsdlLocation;

        //create the ServiceInfoBuilder which will examine the service, collect service
        //information
        ServiceInfoBuilder configurationBuilder =
                new ServiceInfoBuilder(wsdlLocation.toExternalForm(), name);
        try {
            configuration = configurationBuilder.buildServiceInfo();
        } catch (ModelerException ex) {
            serviceException = new ServiceException(ex);
        } catch (ServiceException ex) {
            serviceException = ex;
        }
        if (configuration == null)
            return;

        //service information is collected, create the DynamicProxyBuilder
        dynamicProxyBuilder = createDynamicProxyBuilder();

        //add all the port names to the configured service
        Iterator eachPortName = configuration.getPortNames();
        while (eachPortName.hasNext()) {
            QName currentPortName = (QName) eachPortName.next();
            addPort(currentPortName);
        }
    }

    public ServiceException getServiceException() {
        return serviceException;
    }

    protected DynamicProxyBuilder createDynamicProxyBuilder() {
        return new DynamicProxyBuilder(internalTypeRegistry,
                getHandlerRegistry(),
                configuration);
    }

    public java.net.URL getWSDLDocumentLocation() {
        return wsdlDocumentLocation;
    }

    public Call[] getCalls(QName portName) throws ServiceException {
        ArrayList calls = new ArrayList();

        //get the portInformation by name
        PortInfo portInfo = getPortInfo(portName);
        //iterate through each port operation and create the call for
        //that operation and add to the call list
        //this method implements the java api method
        //service.getCalls(portname)
        Iterator eachOperation = portInfo.getOperations();
        while (eachOperation.hasNext()) {
            OperationInfo currentOperation =
                    (OperationInfo) eachOperation.next();
            calls.add(createCall(portName, currentOperation.getName()));
        }

        return (Call[]) calls.toArray(new Call[calls.size()]);
    }

    public Call createCall(QName portName, String operationName) throws ServiceException {
        return createCall(portName, new QName(operationName));
    }

    public Call createCall(QName portName, QName operationName)
            throws ServiceException {

        ConfiguredCall call = new ConfiguredCall(internalTypeRegistry, getHandlerRegistry(), configuration);
        call.setPortName(portName);
        call.setOperationName(operationName);

        return call;
    }

    protected PortInfo getPortInfo(QName portName) throws ServiceException {
        if (!ports.contains(portName)) {
            throw portNotFoundException(portName);
        }

        return configuration.getPortInfo(portName);
    }

    protected ServiceExceptionImpl portNotFoundException(QName portName) {
        return new ServiceExceptionImpl("dii.service.does.not.contain.port",
                new Object[]{name, portName});
    }

    public Iterator getPorts() {
        //implements Service.getPorts()
        return ports.iterator();
    }

    public Remote getPort(Class portInterface) throws ServiceException {
        //implements Service.getPort(port interface)
        QName portName = getPortNameForInterface(portInterface);
        return getPort(portName, portInterface);
    }

    protected QName getPortNameForInterface(Class portInterface) {
        TypeMapping mapping =
                typeRegistry.getTypeMapping(soapEncodingConstants.getSOAPEncodingNamespace());

        Iterator eachPortInfo = configuration.getPortNames();
        while (eachPortInfo.hasNext()) {
            QName currentPortName = (QName) eachPortInfo.next();
            PortInfo currentPort = configuration.getPortInfo(currentPortName);

            // find out if the currentPortInfo matches the interface
            // Does it have the same number of operations as the interface has methods?
            Method[] methods = portInterface.getMethods();
            if (currentPort.getOperationCount() != methods.length) {
                continue;
            }

            // For eachMethod in the interface
            boolean allMethodsMatched = true;
            for (int i = 0; i < methods.length; i++) {
                // Find out if the currentPortInfo has an operation that matches the method
                // For eachOperation in the currentPortInfo
                Iterator eachOperation = currentPort.getOperations();
                boolean operationMatchesMethod = false;
                while (eachOperation.hasNext()) {
                    OperationInfo currentOperation =
                            (OperationInfo) eachOperation.next();
                    // Does the currentOperation name match the method name?
                    if (!currentOperation
                            .getName()
                            .getLocalPart()
                            .equals(methods[i].getName())) {
                        continue;
                    }
                    // Does it have the same number of parameters?
                    //not fail proof currentOperation has request and response

                    Class[] parameters = methods[i].getParameterTypes();
                    int paramLength = parameters.length;

                    if (currentOperation.getParameterCount()
                            != paramLength) {
                        continue;
                    }
                    // For eachMethodParameter
                    boolean parametersMatched = true;

                    //can't count on registered serializers even for simple Types
                    //in docliteral and rpcliteral- or for arrays or beans
                    //skip this for now- change when get wsdl-
                    /* for (int j = 0; j < parameters.length; j++) {
                         parametersMatched = false;

                         // For eachOperationParameter
                         QName[] operationParameters =
                             currentOperation.getParameterXmlTypes();
                         for (int k = 0; k < operationParameters.length; k++) {
                             // If there is a typeMapping between the currentMethodParameter class and the currentOperationParameter xmlType
                             Class methodParameter =
                                 Holders.stripHolderClass(parameters[j]);

                             if (mapping
                                 .getSerializer(
                                     methodParameter,
                                     operationParameters[k])
                                 != null) {
                                 parametersMatched = true;
                                 break;
                             }
                         }
                         if (!parametersMatched) {
                             break;
                         }
                     }
                      */
                    // If all the parameters matched then consider the operation to match
                    if (parametersMatched) {
                        operationMatchesMethod = true;
                    }
                }
                if (!operationMatchesMethod) {
                    allMethodsMatched = false;
                    break;
                }
            }
            //If all the methods had a matching operation then the
            //currentPortInfo is a match. Return it's name.
            if (allMethodsMatched) {
                return currentPortName;
            }
        }
        return null;
    }

    public Remote getPort(QName portName, Class portInterface)
            throws ServiceException {
        //implements Service.getPort(QName, portInterface)
        //build dynamic proxy and returns
        return dynamicProxyBuilder.buildDynamicProxyFor(getPortInfo(portName),
                portInterface);
    }

}

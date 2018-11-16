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

package com.sun.xml.rpc.client;

import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ServiceFactory;

import com.sun.xml.rpc.client.dii.ConfiguredService;
import com.sun.xml.rpc.processor.modeler.ModelerException;

/**
 * <p> A concrete factory for Service objects. </p>
 *
 * @author JAX-RPC Development Team
 */
public class ServiceFactoryImpl extends ServiceFactory {

    public ServiceFactoryImpl() {
    }
   
    public Service createService(java.net.URL wsdlDocumentLocation, QName name)
        throws ServiceException {
        if (wsdlDocumentLocation == null) {
            throw new IllegalArgumentException("wsdlDocumentLocation must not be null");
        }

        //for a dii and dynamic proxy client using wsdl, the wsdl will
        //be examined and a configured service created
        try {
            ConfiguredService service =
                new ConfiguredService(name, wsdlDocumentLocation);
            if (service.getServiceException() != null)
                throw service.getServiceException();
            return service;
        } catch (ModelerException ex) {
            throw new ServiceException(ex);
        }
    }

    public Service createService(QName name) throws ServiceException {
    	//only qname is known, a BasicService is returned
        return new BasicService(name);
    }

    public Service createService(Class serviceInterface, QName name)
        throws ServiceException {
        //create a service using the service qname and an interface
        if (!Service.class.isAssignableFrom(serviceInterface)) {
            throw new ServiceExceptionImpl(
                "service.interface.required",
                serviceInterface.getName());
        }
        //here the service implementation class is given a name and the
        //service is created using the implementation class
        String serviceImplementationName = serviceInterface.getName() + "_Impl";
        Service service = createService(serviceImplementationName);
        if (service.getServiceName().equals(name)) {
            return service;
        } else {
            throw new ServiceExceptionImpl(
                "service.implementation.not.found",
                serviceInterface.getName());
        }
    }

    private Service createService(String serviceImplementationName)
        throws ServiceException {
        if (serviceImplementationName == null) {
            throw new IllegalArgumentException();
        }
        //load the service implementation class using classloader.loadClass()
        try {
            Class serviceImplementationClass =
                Thread.currentThread().getContextClassLoader().loadClass(
                    serviceImplementationName);
            //make sure it is a BasicService
            if (!BasicService
                .class
                .isAssignableFrom(serviceImplementationClass)) {
                throw new ServiceExceptionImpl(
                    "service.implementation.not.found",
                    serviceImplementationName);
            }
            //create a new instance of the service class
            //and return
            try {            	
                Service service =
                    (Service) serviceImplementationClass.newInstance();
                if (service.getServiceName() != null) {
                    return service;
                } else {
                    throw new ServiceExceptionImpl(
                        "service.implementation.not.found",
                        serviceImplementationName);
                }
            } catch (InstantiationException e) {
                throw new ServiceExceptionImpl(
                    "service.implementation.cannot.create",
                    serviceImplementationClass.getName());
            } catch (IllegalAccessException e) {
                throw new ServiceExceptionImpl(
                    "service.implementation.cannot.create",
                    serviceImplementationClass.getName());
            }
        } catch (ClassNotFoundException e) {
            throw new ServiceExceptionImpl(
                "service.implementation.not.found",
                serviceImplementationName);
        }
    }

    public Service loadService(java.lang.Class serviceInterface)
        throws ServiceException {
        if (serviceInterface == null) {
            throw new IllegalArgumentException();
        }

        //load a service given the service interface
        if (!Service.class.isAssignableFrom(serviceInterface)) {
            throw new ServiceExceptionImpl(
                "service.interface.required",
                serviceInterface.getName());
        }
        // assign the service implementation class name
        String serviceImplementationName = serviceInterface.getName() + "_Impl";
        //create the service
        Service service = createService(serviceImplementationName);

        return (service);
    }

    public Service loadService(
        java.net.URL wsdlDocumentLocation,
        Class serviceInterface,
        Properties properties)
        throws ServiceException {
        	
        //check for null arguments
        if (wsdlDocumentLocation == null) {
            throw new IllegalArgumentException("wsdlDocumentLocation must not be null");
        }

        if (serviceInterface == null) {
            throw new IllegalArgumentException();
        }

        //check to make sure this is a service
        if (!Service.class.isAssignableFrom(serviceInterface)) {
            throw new ServiceExceptionImpl(
                "service.interface.required",
                serviceInterface.getName());
        }
        //make the service implementation class name
        String serviceImplementationName = serviceInterface.getName() + "_Impl";
        //create the service
        Service service = createService(serviceImplementationName);

        return (service);
    }

    public Service loadService(
        java.net.URL wsdlDocumentLocation,
        QName ServiceName,
        Properties properties)
        throws ServiceException {
        	
        //check for null arguments
        if (wsdlDocumentLocation == null) {
            throw new IllegalArgumentException("wsdlDocumentLocation must not be null");
        }
        
        //get the service implementation class name
        String serviceImplementationName = null;
        if (properties != null) {
            serviceImplementationName =
                properties.getProperty(StubPropertyConstants.SERVICEIMPL_NAME);
        }
        if (serviceImplementationName == null) {
            throw new IllegalArgumentException(
                "Properties should contain the property:"
                    + StubPropertyConstants.SERVICEIMPL_NAME);
        }
        //create the service
        Service service = createService(serviceImplementationName);
        if (service.getServiceName().equals(ServiceName)) {
            return service;
        } else {
            throw new ServiceExceptionImpl(
                "service.implementation.not.found",
                serviceImplementationName);
        }
    }
}

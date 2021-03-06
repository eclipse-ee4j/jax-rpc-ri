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

package com.sun.xml.rpc.processor.modeler.j2ee;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.J2EEModelInfo;
import com.sun.xml.rpc.processor.config.J2EEModelInfo.MetadataOperationInfo;
import com.sun.xml.rpc.processor.model.AbstractType;
import com.sun.xml.rpc.processor.model.Fault;
import com.sun.xml.rpc.processor.model.HeaderFault;
import com.sun.xml.rpc.processor.model.ModelProperties;
import com.sun.xml.rpc.processor.model.Operation;
import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.model.java.JavaArrayType;
import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
import com.sun.xml.rpc.processor.model.java.JavaStructureType;
import com.sun.xml.rpc.processor.model.java.JavaType;
import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.modeler.rmi.RmiType;
import com.sun.xml.rpc.processor.modeler.rmi.RmiUtils;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase.WSDLExceptionInfo;
import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
import com.sun.xml.rpc.wsdl.document.Message;
import com.sun.xml.rpc.wsdl.document.OperationStyle;
import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/**
 *
 * @author JAX-RPC RI Development Team
 */
public class J2EEModelerHelper {

    public J2EEModelerHelper(J2EEModelerIf base, J2EEModelInfo modelInfo) {
        this.base = base;
        _j2eeModelInfo = modelInfo;
        _env = (ProcessorEnvironment) modelInfo.getParent().getEnvironment();
    }

    /**
     * @param serviceQName
     * @param wsdlService
     * @return The name of the service in the wsdl
     */
    protected String getServiceInterfaceName(
        QName serviceQName,
        com.sun.xml.rpc.wsdl.document.Service wsdlService) {
        String serviceInterface = "";
        serviceInterface = _j2eeModelInfo.javaNameOfService(serviceQName);
        return serviceInterface;
    }

    protected String getJavaNameOfPort(QName portQName) {
        return _j2eeModelInfo.javaNameOfPort(portQName);
    }

    //  bug fix: 4923650
    protected void setJavaOperationNameProperty(Message inputMessage) {
        MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
        if (opMetaData != null) {
            /* set java operation name */
            base.getInfo().operation.setProperty(
                PROPERTY_OPERATION_JAVA_NAME,
                opMetaData.javaOpName);
        }
    }


    /**
     * This is a complete hack. We should really be reading the mapping file and generate the
     * java methods.  Since we are retro-fitting the mapping information, we have to force
     * jaxrpc to create the explicit context, i.e. handling soap headerfault.
     */
    protected boolean useExplicitServiceContextForDocLit(Message inputMessage) {
        MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
        if (opMetaData != null) {    
            return opMetaData.explicitcontext;
        }    
        return base.useSuperExplicitServiceContextForDocLit(inputMessage);
    }

    /**
     * This is a complete hack. We should really be reading the mapping file and generate the
     * java methods.  Since we are retro-fitting the mapping information, we have to force
     * jaxrpc to create the explicit context, i.e. handling soap headerfault.
     */
    protected boolean useExplicitServiceContextForRpcLit(Message inputMessage) {
        MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
        if (opMetaData != null) {    
            return opMetaData.explicitcontext;
        }
        return base.useSuperExplicitServiceContextForRpcLit(inputMessage);
    }
    
    protected MetadataOperationInfo findOperationInfo(Message inputMessage) {        

        Message outputMessage =
            base.getInfo().portTypeOperation.getStyle()
                == OperationStyle.REQUEST_RESPONSE
                ? base.getInfo().portTypeOperation.getOutput().resolveMessage(
                    base.getInfo().document)
                : null;
        QName bindingName =
            (QName) base.getInfo().modelPort.getProperty(
                ModelProperties.PROPERTY_WSDL_BINDING_NAME);
        J2EEModelInfo.MetadataOperationInfo opMetaData =
            _j2eeModelInfo.findOperationInfo(
                bindingName,
                base.getInfo().portTypeOperation.getName(),
                inputMessage,
                outputMessage,
                base);
        return opMetaData;
    }     

    /**
     * This is a complete hack. We should really be reading the mapping file and generate the
     * java methods.  Since we are retro-fitting the mapping information, we have to force
     * jaxrpc to create the explicit context, i.e. handling soap headerfault.
     */
    protected boolean useExplicitServiceContextForRpcEncoded(Message inputMessage) {
        MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
        if (opMetaData != null) {    
            return opMetaData.explicitcontext;
        }
        return base.useSuperExplicitServiceContextForRpcEncoded(inputMessage);
    }

    protected boolean isUnwrappable(Message inputMessage) {  
        MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
        if (opMetaData != null) {    
            return opMetaData.isWrapped;
        }
        return base.isSuperUnwrappable();
    }

    protected void setCurrentPort(Port port) {
        this.port = port;
    }

    protected String getJavaNameOfSEI(Port port) {
        QName portTypeName =
            (QName) port.getProperty(
                ModelProperties.PROPERTY_WSDL_PORT_TYPE_NAME);
        QName bindingName =
            (QName) port.getProperty(
                ModelProperties.PROPERTY_WSDL_BINDING_NAME);
        String interfaceName =
            _j2eeModelInfo.javaNameOfSEI(
                bindingName,
                portTypeName,
                port.getName());
        return interfaceName;
    }

    public LiteralType getElementTypeToLiteralType(QName elementType) {
        return base.getSuperElementTypeToLiteralType(elementType);
    }
    
    /*-------------------------------------------------------------------*/
    // The following methods were added as part of bug fix: 4923650
    protected AbstractType verifyResultType(
        AbstractType type,
        Operation operation) {
            
        QName bindingName =
            (QName)base.getInfo().modelPort.getProperty(
                ModelProperties.PROPERTY_WSDL_BINDING_NAME);
 
        J2EEModelInfo.MetadataOperationInfo opMetaData = null;
        opMetaData = _j2eeModelInfo.findOperationInfo(bindingName,
            base.getInfo().portTypeOperation.getName(), base.getSuperInputMessage(),
            base.getSuperOutputMessage(), base);

        if (opMetaData == null)
            return type;
            
        J2EEModelInfo.MetadataParamInfo paramInfo 
            = (J2EEModelInfo.MetadataParamInfo)opMetaData.retPart;
        if (paramInfo == null)
            return type;
        try {
            Class paramClass = RmiUtils.getClassForName(paramInfo.javaType,
                _env.getClassLoader());           
            return replaceJavaType(type, RmiType.getRmiType(paramClass));    
        } catch (ClassNotFoundException e) {
            // TODO fill this in?
            debug("ClassNotFoundException: " +e.getMessage());
        }
        return type;
    }

    protected AbstractType verifyParameterType(
        AbstractType type,
        String partName,
        Operation operation) {

        QName bindingName =
            (QName) base.getInfo().modelPort.getProperty(
                ModelProperties.PROPERTY_WSDL_BINDING_NAME);

        J2EEModelInfo.MetadataOperationInfo opMetaData = null;
        opMetaData =
            _j2eeModelInfo.findOperationInfo(
                bindingName,
                base.getInfo().portTypeOperation.getName(),
                base.getSuperInputMessage(),
                base.getSuperOutputMessage(),
                base);

        if (opMetaData == null)
            return type;

        J2EEModelInfo.MetadataParamInfo paramInfo =
            (J2EEModelInfo.MetadataParamInfo) opMetaData.inputParts.get(
                partName);
        if (paramInfo == null) {
            paramInfo =
                (J2EEModelInfo.MetadataParamInfo) opMetaData.inoutParts.get(
                    partName);
        }

        if (paramInfo == null) {
            paramInfo =
                (J2EEModelInfo.MetadataParamInfo) opMetaData.outputParts.get(
                    partName);
        }
        if (paramInfo == null)
            return type;
        try {
            Class paramClass =
                RmiUtils.getClassForName(
                    paramInfo.javaType,
                    _env.getClassLoader());

            return replaceJavaType(type, RmiType.getRmiType(paramClass));
        } catch (ClassNotFoundException e) {
            // TODO fill this in?
            debug("ClassNotFoundException: " + e.getMessage());
        }
        return type;
    }

    private AbstractType replaceJavaType(AbstractType type, RmiType rmiType) {
        // TODO right now we only do simple types
        if (type instanceof SOAPSimpleType
            || type instanceof LiteralSimpleType) {
            JavaSimpleType javaSimpleType =
                base.getJavaTypes().getJavaSimpleType(rmiType.typeString(false));
            if (javaSimpleType != null) {
                AbstractType newType;
                if (type instanceof SOAPSimpleType) {
                    newType =
                        new SOAPSimpleType(
                            type.getName(),
                            javaSimpleType,
                            Boolean
                                .valueOf(
                                    ((SOAPSimpleType) type).isReferenceable())
                                .booleanValue());

                } else {
                    newType =
                        new LiteralSimpleType(
                            type.getName(),
                            javaSimpleType,
                            ((LiteralSimpleType) type).isNillable());
                }
                newType.setVersion(type.getVersion());
                return newType;
            }
        } else if (
            type instanceof LiteralSequenceType
                && rmiType.getTypeCode() == RmiType.TC_ARRAY
                && ((LiteralSequenceType) type).getElementMembersCount() == 1) {
            // bug fix: 4927549              
            LiteralElementMember elementMember =
                (LiteralElementMember) ((LiteralSequenceType) type)
                    .getElementMembers()
                    .next();
            String typeString = rmiType.typeString(false);

            if (type.getJavaType() instanceof JavaStructureType) {
                AbstractType modType =
                    (AbstractType) modifiedTypes.get(type.getName());
                if (modType != null)
                    return modType;
                String elemName =
                    elementMember.getJavaStructureMember().getName();
                JavaType elemType =
                    elementMember.getJavaStructureMember().getType();
                // bug fix: 4931493
                if (!elemType
                    .getName()
                    .equals(JavaSimpleTypeCreator.BYTE_ARRAY_CLASSNAME)) {
                    LiteralArrayWrapperType arrayType =
                        new LiteralArrayWrapperType(type.getName());
                    arrayType.setJavaType(type.getJavaType());
                    ((JavaStructureType) type.getJavaType()).setOwner(
                        arrayType);
                    arrayType.setJavaArrayType((JavaArrayType) elemType);
                    arrayType.add(elementMember);
                    modifiedTypes.put(arrayType.getName(), arrayType);
                    return arrayType;
                }
            }
        }

        return type;
    }
    // end of bug fix: 4923650
    /*-----------------------------------------------------------------*/

    /*
     * This is a hook so that parameters ordering can be reset by the 109 mapping
     * information.  This is backwards.  Ideally, we should just do this right the
     * first time we process.  But since we are retro-fitting, this is the least
     * intrusive way I could think of to 1) be spec compliant 2) not introduing
     * regression.
     */
    protected void postProcessSOAPOperation(Operation operation) {
        //XXX TODO.  Maybe we could just do a verification here for now.
        //compare the param info between jaxrpc mapping and the 109 mapping
    }

    protected WSDLExceptionInfo getExceptionInfo(Fault fault) {

        QName faultMsgQName = null;
        String partName = null;
        if (fault instanceof HeaderFault) {
            faultMsgQName = ((HeaderFault) fault).getMessage();
            partName = ((HeaderFault) fault).getPart();
        } else {
            faultMsgQName =
                new QName(
                    fault.getBlock().getName().getNamespaceURI(),
                    fault.getName());
        }
        debug(
            "Looking for fault qname = "
                + faultMsgQName
                + "; partName = "
                + partName);
        return _j2eeModelInfo.getExceptionInfo(faultMsgQName, partName);
    }

    /**
     * BP 2707 assume "literal" if "use" attribute is not specified
     * XXX FIXME.  How do we know whether useWSIBasicProfile should be used or not?
     * XXX Also, soapheader "use" attribute not handled here
     */
    protected void setSOAPUse() {
        SOAPBody requestBody = base.getSuperSOAPRequestBody();
        SOAPBody responseBody = null;

        if (requestBody != null
            && !(requestBody.isLiteral() || requestBody.isEncoded()))
            requestBody.setUse(SOAPUse.LITERAL);
        else if (requestBody != null && requestBody.isEncoded())
            requestBody.setUse(SOAPUse.ENCODED);

        if (base.getInfo().portTypeOperation.getStyle()
            == OperationStyle.REQUEST_RESPONSE) {
            responseBody = base.getSuperSOAPResponseBody();
            if (responseBody != null
                && !(responseBody.isLiteral() || responseBody.isEncoded()))
                responseBody.setUse(SOAPUse.LITERAL);
            else if (responseBody != null && responseBody.isEncoded())
                responseBody.setUse(SOAPUse.ENCODED);
        }
    }

    protected String getJavaNameForOperation(Operation operation) {
        String name =
            (String) operation.getProperty(PROPERTY_OPERATION_JAVA_NAME);
        if (name == null) {
            name = base.getSuperJavaNameForOperation(operation);
        }
        return name;
    }

    public static QName getQNameOf(GloballyKnown entity) {
        return new QName(
            entity.getDefining().getTargetNamespaceURI(),
            entity.getName());
    }

    private void debug(String msg) {
        if (_env.verbose()) {
            System.out.println("[J2EEModelInfo] ==> " + msg);
        }
    }

    private static final String PROPERTY_OPERATION_JAVA_NAME =
        "com.sun.enterprise.webservice.mapping.operationJavaName";
    private static final String WSDL_PARAMETER_ORDER =
        "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";

    private J2EEModelInfo _j2eeModelInfo;
    private ProcessorEnvironment _env;
    private Port port;
    private Map modifiedTypes = new HashMap();
    private J2EEModelerIf base;
}

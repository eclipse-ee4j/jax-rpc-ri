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

import java.util.Properties;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.J2EEModelInfo;
import com.sun.xml.rpc.processor.config.WSDLModelInfo;
import com.sun.xml.rpc.processor.model.AbstractType;
import com.sun.xml.rpc.processor.model.Fault;
import com.sun.xml.rpc.processor.model.Operation;
import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler112;
import com.sun.xml.rpc.wsdl.document.Message;
import com.sun.xml.rpc.wsdl.document.WSDLDocument;
import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class J2EEModeler112 extends WSDLModeler112 implements J2EEModelerIf {


    public J2EEModeler112(J2EEModelInfo modelInfo, Properties options) {
        super(modelInfo, options);
        helper = new J2EEModelerHelper(this, modelInfo);
    }

    /**
     * @param document
     * @param _modelInfo
     * @param _options
     * @param _conflictingClassNames
     * @param _javaTypes
     * @return The appropriate schema analyzer for the document
     */
    @Override
    protected SchemaAnalyzerBase getSchemaAnalyzerInstance(   
        WSDLDocument document,
        WSDLModelInfo _modelInfo,
        Properties _options,
        Set _conflictingClassNames,
        JavaSimpleTypeCreator _javaTypes) {

        return new J2EESchemaAnalyzer112(
            document,
            (J2EEModelInfo) _modelInfo,
            _options,
            _conflictingClassNames,
            _javaTypes);
    }

    @Override
    protected String getServiceInterfaceName(
        QName serviceQName,
        com.sun.xml.rpc.wsdl.document.Service wsdlService) {
        return helper.getServiceInterfaceName(serviceQName, wsdlService);
    }

    @Override
    protected String getJavaNameOfPort(QName portQName) {
        return helper.getJavaNameOfPort(portQName);
    }

    @Override
    protected void setJavaOperationNameProperty(Message inputMessage) {
        helper.setJavaOperationNameProperty(inputMessage);
    }


    /**
     * This is a complete hack. We should really be reading the mapping file and generate the
     * java methods.  Since we are retro-fitting the mapping information, we have to force
     * jaxrpc to create the explicit context, i.e. handling soap headerfault.
     */
    @Override
    protected boolean useExplicitServiceContextForDocLit(Message inputMessage) {
        return helper.useExplicitServiceContextForDocLit(inputMessage);
    }

    /**
     * This is a complete hack. We should really be reading the mapping file and generate the
     * java methods.  Since we are retro-fitting the mapping information, we have to force
     * jaxrpc to create the explicit context, i.e. handling soap headerfault.
     */
    @Override
    protected boolean useExplicitServiceContextForRpcLit(Message inputMessage) {
        return helper.useExplicitServiceContextForRpcLit(inputMessage);
    }
    

    /**
     * This is a complete hack. We should really be reading the mapping file and generate the
     * java methods.  Since we are retro-fitting the mapping information, we have to force
     * jaxrpc to create the explicit context, i.e. handling soap headerfault.
     */
    @Override
    protected boolean useExplicitServiceContextForRpcEncoded(
        Message inputMessage) {
            
        return helper.useExplicitServiceContextForRpcEncoded(inputMessage);
    }

    @Override
    protected boolean isUnwrappable(Message inputMessage) {
        boolean unwrap = helper.isUnwrappable(inputMessage);
        if (unwrap) {
            this.info.operation.setProperty("J2EE_UNWRAP", "true");
        }
        return unwrap;
    }

    @Override
    protected void setCurrentPort(Port port) {
        helper.setCurrentPort(port);
    }

    @Override
    protected String getJavaNameOfSEI(Port port) {
        return helper.getJavaNameOfSEI(port);
    }

    @Override
    public LiteralType getElementTypeToLiteralType(QName elementType) {        
        return helper.getElementTypeToLiteralType(elementType);
    }
    
    @Override
    protected AbstractType verifyResultType(
        AbstractType type,
        Operation operation) {
            
        return helper.verifyResultType(type, operation);    
    }

    @Override
    protected AbstractType verifyParameterType(
        AbstractType type,
        String partName,
        Operation operation) {
            
        return helper.verifyParameterType(type, partName, operation);
    }

    @Override
    protected void postProcessSOAPOperation(Operation operation) {
        helper.postProcessSOAPOperation(operation);
    }

    @Override
    protected WSDLExceptionInfo getExceptionInfo(Fault fault) {
        return helper.getExceptionInfo(fault);
    }

    @Override
    protected void setSOAPUse() {
        helper.setSOAPUse();
    }

    @Override
    protected String getJavaNameForOperation(Operation operation) {
        return helper.getJavaNameForOperation(operation);
    }

    public boolean useSuperExplicitServiceContextForDocLit(
        Message inputMessage) {
            
        return super.useExplicitServiceContextForDocLit(inputMessage);
    }

    public boolean useSuperExplicitServiceContextForRpcLit(
        Message inputMessage) {
            
        return super.useExplicitServiceContextForRpcLit(inputMessage);
    }

    public boolean useSuperExplicitServiceContextForRpcEncoded(
        Message inputMessage) {
            
        return super.useExplicitServiceContextForRpcEncoded(inputMessage);
    }

    public boolean isSuperUnwrappable() {
        return super.isUnwrappable();
    }

    public LiteralType getSuperElementTypeToLiteralType(QName elementType) {
        return super.getElementTypeToLiteralType(elementType);
    }

    public String getSuperJavaNameForOperation(Operation operation) {
        return super.getJavaNameForOperation(operation);
    }

    public ProcessSOAPOperationInfo getInfo() {
        return info;
    }

    public Message getSuperOutputMessage() {
        return super.getOutputMessage();
    }
    
    public Message getSuperInputMessage() {
        return super.getInputMessage();
    }
    
    public SOAPBody getSuperSOAPRequestBody() {
        return super.getSOAPRequestBody();
    }
    
    public SOAPBody getSuperSOAPResponseBody() {
        return super.getSOAPResponseBody();
    }

    public JavaSimpleTypeCreator getJavaTypes() {
        return _javaTypes;
    }
    
    @Override
    public boolean isConflictingServiceClassName(String name) {
        return false;
    }

    @Override
    public boolean isConflictingPortClassName(String name) {
        return false;
    }

    @Override
    public boolean isConflictingExceptionClassName(String name) {
        return false;
    }
    
    private static final String PROPERTY_OPERATION_JAVA_NAME =
        "com.sun.enterprise.webservice.mapping.operationJavaName";
    private static final String WSDL_PARAMETER_ORDER =
        "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";
    private J2EEModelerHelper helper;
}

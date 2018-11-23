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

package com.sun.xml.rpc.processor.modeler.wsdl;

import java.util.Properties;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.WSDLModelInfo;
import com.sun.xml.rpc.processor.model.AbstractType;
import com.sun.xml.rpc.processor.model.Fault;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.wsdl.document.MessagePart;
import com.sun.xml.rpc.wsdl.document.WSDLDocument;
import com.sun.xml.rpc.wsdl.framework.Extensible;
import com.sun.xml.rpc.wsdl.framework.Extension;

/**
 * WSDLModeler for JAXRPC version 1.1
 * @author JAX-RPC Development Team
 */
public class WSDLModeler11 extends WSDLModelerBase {

    /**
     * @param modelInfo
     * @param options
     */
    public WSDLModeler11(WSDLModelInfo modelInfo, Properties options) {
        super(modelInfo, options);
    }

    /**
     * bug fix: 4884736, this method can be overridden from subclasses of WSDLModelerBase
     * Returns soapbinding:fault name. If null then gives warning for wsi R2721 and uses 
     * wsdl:fault name.
     * 
     * @param faultPartName - to be used by versions &lt; 1.1
     * @param soapFaultName
     * @param bindFaultName
     * @return soapFaultName if not null otherwise bindFaultName
     */
    @Override
    protected String getFaultName(
        String faultPartName,
        String soapFaultName,
        String bindFaultName,
        String faultMessageName) {
          
        return (soapFaultName == null) ? bindFaultName : soapFaultName;
    }

    @Override
    protected String getLiteralJavaMemberName(Fault fault) {
        String javaMemberName =
            fault.getBlock().getName().getLocalPart();
        return javaMemberName;
    }

    /* 
     * @see com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase#getSchemaAnalyzerInstance(com.sun.xml.rpc.wsdl.document.WSDLDocument, com.sun.xml.rpc.processor.config.WSDLModelInfo, java.util.Properties, java.util.Set, com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator)
     */
    protected SchemaAnalyzerBase getSchemaAnalyzerInstance(
        WSDLDocument document,
        WSDLModelInfo _modelInfo,
        Properties _options,
        Set _conflictingClassNames,
        JavaSimpleTypeCreator _javaTypes) {
        return new SchemaAnalyzer11(
            document,
            _modelInfo,
            _options,
            _conflictingClassNames,
            _javaTypes);

    }
    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase#getHeaderFaultSequenceType(com.sun.xml.rpc.processor.model.AbstractType, com.sun.xml.rpc.wsdl.document.MessagePart, javax.xml.namespace.QName)
     */
    @Override
    protected AbstractType getHeaderFaultSequenceType(
        AbstractType faultType,
        MessagePart faultPart,
        QName elemName) {
        return faultType;
    }

    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase#isSingleInOutPart(java.util.Set, com.sun.xml.rpc.wsdl.document.MessagePart)
     */
    @Override
    protected boolean isSingleInOutPart(
        Set inputParameterNames,
        MessagePart outputPart) {
        return false;
    }
        
    /* 
     * Only JAXRPC SI 1.1.2 and onwards support wsdl mime extension and swaref.
     * @see com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase#getAnyExtensionOfType(com.sun.xml.rpc.wsdl.framework.Extensible, java.lang.Class)
     */
    @Override
    protected Extension getAnyExtensionOfType(
            Extensible extensible,
            Class type) {
        return getExtensionOfType(extensible, type);
    }
    
}

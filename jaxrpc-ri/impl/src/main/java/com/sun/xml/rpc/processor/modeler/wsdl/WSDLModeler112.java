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

import com.sun.xml.rpc.processor.config.WSDLModelInfo;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.wsdl.document.WSDLDocument;

/**
 * WSDLModeler for JAXRPC version 1.1.2
 * @author Vivek Pandey
 */
public class WSDLModeler112 extends WSDLModelerBase {

    /**
     * @param modelInfo
     * @param options
     */
    public WSDLModeler112(WSDLModelInfo modelInfo, Properties options) {
        super(modelInfo, options);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase#getSchemaAnalyzerInstance(com.sun.xml.rpc.wsdl.document.WSDLDocument, com.sun.xml.rpc.processor.config.WSDLModelInfo, java.util.Properties, java.util.Set, com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator)
     */
    protected SchemaAnalyzerBase getSchemaAnalyzerInstance(
        WSDLDocument document,
        WSDLModelInfo _modelInfo,
        Properties _options,
        Set _conflictingClassNames,
        JavaSimpleTypeCreator _javaTypes) {
        return new SchemaAnalyzer112(
                document,
                _modelInfo,
                _options,
                _conflictingClassNames,
                _javaTypes);
    }

}

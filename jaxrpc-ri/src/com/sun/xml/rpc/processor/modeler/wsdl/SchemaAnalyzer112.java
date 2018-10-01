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

import com.sun.xml.rpc.processor.config.ModelInfo;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.wsdl.framework.AbstractDocument;

/**
 * @author Vivek Pandey
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SchemaAnalyzer112 extends SchemaAnalyzer111 {
    /**
     * @param document
     * @param modelInfo
     * @param options
     * @param conflictingClassNames
     * @param javaTypes
     */
    public SchemaAnalyzer112(
            AbstractDocument document,
            ModelInfo modelInfo,
            Properties options,
            Set conflictingClassNames,
            JavaSimpleTypeCreator javaTypes) {
        super(document, modelInfo, options, conflictingClassNames, javaTypes);
    }

    /* (bug fix: 4999385
     * @see com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase#doWeHandleAttributeTypeEnumeration(com.sun.xml.rpc.processor.model.literal.LiteralType)
     */
    protected boolean doWeHandleAttributeTypeEnumeration(LiteralType attributeType) {
        return isAttributeEnumeration(attributeType);
    }

}

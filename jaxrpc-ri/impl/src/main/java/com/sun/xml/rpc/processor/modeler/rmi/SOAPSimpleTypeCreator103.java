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

package com.sun.xml.rpc.processor.modeler.rmi;

import java.util.Map;

import com.sun.xml.rpc.soap.SOAPVersion;

/**
 * @author JAX-RPC Development Team
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SOAPSimpleTypeCreator103 extends SOAPSimpleTypeCreatorBase {

    /**
     * 
     */
    public SOAPSimpleTypeCreator103() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param useStrictMode
     */
    public SOAPSimpleTypeCreator103(boolean useStrictMode) {
        super(false);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param useStrictMode
     * @param version
     */
    public SOAPSimpleTypeCreator103(boolean useStrictMode, SOAPVersion version) {
        super(false, SOAPVersion.SOAP_11);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreatorBase#initializeTypeMap(java.util.Map)
     */
    public void initializeTypeMap(Map typeMap) {
        typeMap.put(BOXED_BOOLEAN_CLASSNAME, SOAP_BOXED_BOOLEAN_SOAPTYPE);
        typeMap.put(BOOLEAN_CLASSNAME, XSD_BOOLEAN_SOAPTYPE);
        typeMap.put(BOXED_BYTE_CLASSNAME, SOAP_BOXED_BYTE_SOAPTYPE);
        // don't uncomment this because Byte[]s can have nulls and soapenc:base64
        // doesn't handle that
//        typeMap.put(BOXED_BYTE_ARRAY_CLASSNAME, SOAP_BOXED_BYTE_ARRAY_SOAPTYPE);
        typeMap.put(BYTE_CLASSNAME, XSD_BYTE_SOAPTYPE);
        typeMap.put(BYTE_ARRAY_CLASSNAME, XSD_BYTE_ARRAY_SOAPTYPE);
        typeMap.put(BOXED_DOUBLE_CLASSNAME, SOAP_BOXED_DOUBLE_SOAPTYPE);
        typeMap.put(DOUBLE_CLASSNAME, XSD_DOUBLE_SOAPTYPE);
        typeMap.put(BOXED_FLOAT_CLASSNAME, SOAP_BOXED_FLOAT_SOAPTYPE);
        typeMap.put(FLOAT_CLASSNAME, XSD_FLOAT_SOAPTYPE);
        typeMap.put(BOXED_INTEGER_CLASSNAME, SOAP_BOXED_INTEGER_SOAPTYPE);
        typeMap.put(INT_CLASSNAME, XSD_INT_SOAPTYPE);
        typeMap.put(BOXED_LONG_CLASSNAME, SOAP_BOXED_LONG_SOAPTYPE);
        typeMap.put(LONG_CLASSNAME, XSD_LONG_SOAPTYPE);
        typeMap.put(BOXED_SHORT_CLASSNAME, SOAP_BOXED_SHORT_SOAPTYPE);
        typeMap.put(SHORT_CLASSNAME, XSD_SHORT_SOAPTYPE);
        typeMap.put(STRING_CLASSNAME, XSD_STRING_SOAPTYPE);
        typeMap.put(BIGDECIMAL_CLASSNAME, XSD_DECIMAL_SOAPTYPE);
        typeMap.put(BIGINTEGER_CLASSNAME, XSD_INTEGER_SOAPTYPE);
        typeMap.put(DATE_CLASSNAME, XSD_DATE_TIME_SOAPTYPE);
        typeMap.put(CALENDAR_CLASSNAME, XSD_DATE_TIME_CALENDAR_SOAPTYPE);
        typeMap.put(QNAME_CLASSNAME, XSD_QNAME_SOAPTYPE);
        typeMap.put(VOID_CLASSNAME, XSD_VOID_SOAPTYPE);
        typeMap.put(OBJECT_CLASSNAME, XSD_ANYTYPE_SOAPTYPE);

        // Collection Types
        typeMap.put(COLLECTION_CLASSNAME, COLLECTION_SOAPTYPE);
        typeMap.put(LIST_CLASSNAME, LIST_SOAPTYPE);
        typeMap.put(SET_CLASSNAME, SET_SOAPTYPE);
        typeMap.put(VECTOR_CLASSNAME, VECTOR_SOAPTYPE);
        typeMap.put(STACK_CLASSNAME, STACK_SOAPTYPE);
        typeMap.put(LINKED_LIST_CLASSNAME, LINKED_LIST_SOAPTYPE);
        typeMap.put(ARRAY_LIST_CLASSNAME, ARRAY_LIST_SOAPTYPE);
        typeMap.put(HASH_SET_CLASSNAME, HASH_SET_SOAPTYPE);
        typeMap.put(TREE_SET_CLASSNAME, TREE_SET_SOAPTYPE);

        // Collection Types
        typeMap.put(MAP_CLASSNAME, MAP_SOAPTYPE);
        typeMap.put(HASH_MAP_CLASSNAME, HASH_MAP_SOAPTYPE);
        typeMap.put(TREE_MAP_CLASSNAME, TREE_MAP_SOAPTYPE);
        typeMap.put(HASHTABLE_CLASSNAME, HASHTABLE_SOAPTYPE);
        typeMap.put(PROPERTIES_CLASSNAME, PROPERTIES_SOAPTYPE);
//        typeMap.put(WEAK_HASH_MAP_CLASSNAME, WEAK_HASH_MAP_SOAPTYPE);

        // Attachment types
        typeMap.put(IMAGE_CLASSNAME, IMAGE_SOAPTYPE);
        typeMap.put(MIME_MULTIPART_CLASSNAME, MIME_MULTIPART_SOAPTYPE);
        typeMap.put(SOURCE_CLASSNAME, SOURCE_SOAPTYPE);
        typeMap.put(DATA_HANDLER_CLASSNAME, DATA_HANDLER_SOAPTYPE);
        typeMap.put(JAX_RPC_MAP_ENTRY_CLASSNAME, JAX_RPC_MAP_ENTRY_SOAPTYPE);

    }

}

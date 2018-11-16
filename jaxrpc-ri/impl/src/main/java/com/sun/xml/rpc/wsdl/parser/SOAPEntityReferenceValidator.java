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

package com.sun.xml.rpc.wsdl.parser;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
import com.sun.xml.rpc.wsdl.framework.Kind;

/**
 * An interface implemented by a class that is capable of validating
 * a QName/Kind pair referring to an external entity.
 *
 * @author JAX-RPC Development Team
 */
public class SOAPEntityReferenceValidator implements EntityReferenceValidator {
    public SOAPEntityReferenceValidator() {
    }

    public boolean isValid(Kind kind, QName name) {

        // just let all "xml:" QNames through
        if (name.getNamespaceURI().equals(Constants.NS_XML))
            return true;

        if (kind == SchemaKinds.XSD_TYPE) {
            return _validTypes.contains(name);
        } else if (kind == SchemaKinds.XSD_ELEMENT) {
            return _validElements.contains(name);
        } else if (kind == SchemaKinds.XSD_ATTRIBUTE) {
            return _validAttributes.contains(name);
        } else {
            // no luck
            return false;
        }
    }

    private static final Set _validTypes;
    private static final Set _validElements;
    private static final Set _validAttributes;

    static {
        // add all XML Schema and SOAP types
        _validTypes = new HashSet();
        _validTypes.add(SOAPConstants.QNAME_TYPE_ARRAY);
        _validTypes.add(SchemaConstants.QNAME_TYPE_STRING);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NORMALIZED_STRING);
        _validTypes.add(SchemaConstants.QNAME_TYPE_TOKEN);
        _validTypes.add(SchemaConstants.QNAME_TYPE_BYTE);
        _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE);
        _validTypes.add(SchemaConstants.QNAME_TYPE_BASE64_BINARY);
        _validTypes.add(SchemaConstants.QNAME_TYPE_HEX_BINARY);
        _validTypes.add(SchemaConstants.QNAME_TYPE_INTEGER);
        _validTypes.add(SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER);
        _validTypes.add(SchemaConstants.QNAME_TYPE_INT);
        _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_INT);
        _validTypes.add(SchemaConstants.QNAME_TYPE_LONG);
        _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_LONG);
        _validTypes.add(SchemaConstants.QNAME_TYPE_SHORT);
        _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT);
        _validTypes.add(SchemaConstants.QNAME_TYPE_DECIMAL);
        _validTypes.add(SchemaConstants.QNAME_TYPE_FLOAT);
        _validTypes.add(SchemaConstants.QNAME_TYPE_DOUBLE);
        _validTypes.add(SchemaConstants.QNAME_TYPE_BOOLEAN);
        _validTypes.add(SchemaConstants.QNAME_TYPE_TIME);
        _validTypes.add(SchemaConstants.QNAME_TYPE_DATE_TIME);
        _validTypes.add(SchemaConstants.QNAME_TYPE_DURATION);
        _validTypes.add(SchemaConstants.QNAME_TYPE_DATE);
        _validTypes.add(SchemaConstants.QNAME_TYPE_G_MONTH);
        _validTypes.add(SchemaConstants.QNAME_TYPE_G_YEAR);
        _validTypes.add(SchemaConstants.QNAME_TYPE_G_YEAR_MONTH);
        _validTypes.add(SchemaConstants.QNAME_TYPE_G_DAY);
        _validTypes.add(SchemaConstants.QNAME_TYPE_G_MONTH_DAY);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NAME);
        _validTypes.add(SchemaConstants.QNAME_TYPE_QNAME);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NCNAME);
        _validTypes.add(SchemaConstants.QNAME_TYPE_ANY_URI);
        _validTypes.add(SchemaConstants.QNAME_TYPE_ID);
        _validTypes.add(SchemaConstants.QNAME_TYPE_IDREF);
        _validTypes.add(SchemaConstants.QNAME_TYPE_IDREFS);
        _validTypes.add(SchemaConstants.QNAME_TYPE_ENTITY);
        _validTypes.add(SchemaConstants.QNAME_TYPE_ENTITIES);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NOTATION);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NMTOKEN);
        _validTypes.add(SchemaConstants.QNAME_TYPE_NMTOKENS);
        _validTypes.add(SchemaConstants.QNAME_TYPE_URTYPE);
        _validTypes.add(SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE);
        _validTypes.add(SOAPConstants.QNAME_TYPE_STRING);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NORMALIZED_STRING);
        _validTypes.add(SOAPConstants.QNAME_TYPE_TOKEN);
        _validTypes.add(SOAPConstants.QNAME_TYPE_BYTE);
        _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE);
        _validTypes.add(SOAPConstants.QNAME_TYPE_BASE64_BINARY);
        _validTypes.add(SOAPConstants.QNAME_TYPE_HEX_BINARY);
        _validTypes.add(SOAPConstants.QNAME_TYPE_INTEGER);
        _validTypes.add(SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER);
        _validTypes.add(SOAPConstants.QNAME_TYPE_INT);
        _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_INT);
        _validTypes.add(SOAPConstants.QNAME_TYPE_LONG);
        _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_LONG);
        _validTypes.add(SOAPConstants.QNAME_TYPE_SHORT);
        _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT);
        _validTypes.add(SOAPConstants.QNAME_TYPE_DECIMAL);
        _validTypes.add(SOAPConstants.QNAME_TYPE_FLOAT);
        _validTypes.add(SOAPConstants.QNAME_TYPE_DOUBLE);
        _validTypes.add(SOAPConstants.QNAME_TYPE_BOOLEAN);
        _validTypes.add(SOAPConstants.QNAME_TYPE_TIME);
        _validTypes.add(SOAPConstants.QNAME_TYPE_DATE_TIME);
        _validTypes.add(SOAPConstants.QNAME_TYPE_DURATION);
        _validTypes.add(SOAPConstants.QNAME_TYPE_DATE);
        _validTypes.add(SOAPConstants.QNAME_TYPE_G_MONTH);
        _validTypes.add(SOAPConstants.QNAME_TYPE_G_YEAR);
        _validTypes.add(SOAPConstants.QNAME_TYPE_G_YEAR_MONTH);
        _validTypes.add(SOAPConstants.QNAME_TYPE_G_DAY);
        _validTypes.add(SOAPConstants.QNAME_TYPE_G_MONTH_DAY);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NAME);
        _validTypes.add(SOAPConstants.QNAME_TYPE_QNAME);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NCNAME);
        _validTypes.add(SOAPConstants.QNAME_TYPE_ANY_URI);
        _validTypes.add(SOAPConstants.QNAME_TYPE_ID);
        _validTypes.add(SOAPConstants.QNAME_TYPE_IDREF);
        _validTypes.add(SOAPConstants.QNAME_TYPE_IDREFS);
        _validTypes.add(SOAPConstants.QNAME_TYPE_ENTITY);
        _validTypes.add(SOAPConstants.QNAME_TYPE_ENTITIES);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NOTATION);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NMTOKEN);
        _validTypes.add(SOAPConstants.QNAME_TYPE_NMTOKENS);
        _validTypes.add(SOAPConstants.QNAME_TYPE_BASE64);
        // New types 12/3/02
        _validTypes.add(SchemaConstants.QNAME_TYPE_LANGUAGE);

        // add all SOAP encoding elements
        _validElements = new HashSet();
        _validElements.add(SOAPConstants.QNAME_ELEMENT_STRING);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NORMALIZED_STRING);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_TOKEN);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_BYTE);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_BYTE);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_BASE64_BINARY);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_HEX_BINARY);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_INTEGER);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_POSITIVE_INTEGER);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NEGATIVE_INTEGER);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NON_NEGATIVE_INTEGER);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NON_POSITIVE_INTEGER);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_INT);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_INT);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_LONG);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_LONG);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_SHORT);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_SHORT);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_DECIMAL);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_FLOAT);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_DOUBLE);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_BOOLEAN);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_TIME);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_DATE_TIME);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_DURATION);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_DATE);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_G_MONTH);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_G_YEAR);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_G_YEAR_MONTH);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_G_DAY);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_G_MONTH_DAY);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NAME);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_QNAME);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NCNAME);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_ANY_URI);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_ID);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_IDREF);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_IDREFS);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_ENTITY);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_ENTITIES);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NOTATION);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NMTOKEN);
        _validElements.add(SOAPConstants.QNAME_ELEMENT_NMTOKENS);

        _validAttributes = new HashSet();
        _validAttributes.add(SOAPConstants.QNAME_ATTR_ARRAY_TYPE);
        _validAttributes.add(SOAPConstants.QNAME_ATTR_OFFSET);
        _validAttributes.add(SOAPConstants.QNAME_ATTR_POSITION);
    }
}

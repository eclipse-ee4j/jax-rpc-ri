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

package com.sun.xml.rpc.encoding;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;

/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class SerializerBase
    implements CombinedSerializer, SerializerConstants {

    protected QName type;
    protected boolean encodeType;
    protected boolean isNullable;
    protected String encodingStyle;

    protected SerializerBase(
        QName xmlType,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle) {
        if (xmlType == null) {
            throw new IllegalArgumentException("xmlType parameter is not allowed to be null");
        }
        this.type = xmlType;
        this.encodeType = encodeType;
        this.isNullable = isNullable;
        this.encodingStyle = encodingStyle;
    }

    public QName getXmlType() {
        return type;
    }

    public boolean getEncodeType() {
        return encodeType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public String getEncodingStyle() {
        return encodingStyle;
    }

    public CombinedSerializer getInnermostSerializer() {
        return this;
    }

    public Object deserialize(
        DataHandler dataHandler,
        SOAPDeserializationContext context) {
        throw new UnsupportedOperationException();
    }

    protected QName getName(XMLReader reader) throws Exception {
        return reader.getName();
    }

    public static QName getType(XMLReader reader) throws Exception {
        QName type = null;

        Attributes attrs = reader.getAttributes();
        String typeVal = attrs.getValue(XSDConstants.URI_XSI, "type");

        if (typeVal != null) {
            type = XMLReaderUtil.decodeQName(reader, typeVal);
        }

        return type;
    }

    public static boolean getNullStatus(XMLReader reader) throws Exception {
        boolean isNull = false;

        Attributes attrs = reader.getAttributes();
        String nullVal = attrs.getValue(XSDConstants.URI_XSI, "nil");
        isNull = (nullVal != null && decodeBoolean(nullVal));

        return isNull;
    }

    public static boolean decodeBoolean(String str) throws Exception {
        return (str.equals("true") || str.equals("1"));
    }

    protected String getID(XMLReader reader) throws Exception {
        Attributes attrs = reader.getAttributes();
        return attrs.getValue("", "id");
    }

    protected void verifyName(XMLReader reader, QName expectedName)
        throws Exception {
        QName actualName = getName(reader);

        if (!actualName.equals(expectedName)) {
            throw new DeserializationException(
                "soap.unexpectedElementName",
                new Object[] { expectedName.toString(), actualName.toString()});
        }
    }

    protected void verifyType(XMLReader reader) throws Exception {
        if (typeIsEmpty()) {
            return;
        }

        QName actualType = getType(reader);

        if (actualType != null) {
            if (!actualType.equals(type) && !isAcceptableType(actualType)) {
                throw new DeserializationException(
                    "soap.unexpectedElementType",
                    new Object[] { type.toString(), actualType.toString()});
            }
        }
    }

    protected boolean isAcceptableType(QName actualType) {
        return false;
    }

    protected void skipEmptyContent(XMLReader reader) throws Exception {
        reader.skipElement();
    }

    public String getMechanismType() {
        return com.sun.xml.rpc.encoding.EncodingConstants.JAX_RPC_RI_MECHANISM;
    }

    protected boolean typeIsEmpty() {
        return type.getNamespaceURI().equals("")
            && type.getLocalPart().equals("");
    }
}

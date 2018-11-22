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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.streaming.XMLWriterUtil;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;

/**
 * Serializes and Deserializes objects dynamically based on their Java type or XML type respectively
 *
 * @author JAX-RPC Development Team
 */

public class DynamicSerializer
    extends SerializerBase
    implements SchemaConstants, Initializable {

    InternalTypeMappingRegistry registry = null;

    private com.sun.xml.rpc.soap.SOAPEncodingConstants soapEncodingConstants =
        null;

    private void init(SOAPVersion ver) {
        soapEncodingConstants =
            SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public DynamicSerializer(
        QName xmlType,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle) {

        this(
            xmlType,
            encodeType,
            isNullable,
            encodingStyle,
            SOAPVersion.SOAP_11);
    }

    public DynamicSerializer(
        QName xmlType,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        SOAPVersion ver) {

        super(xmlType, encodeType, isNullable, encodingStyle);
        init(ver); // Initialize SOAP constants
    }

    public void initialize(InternalTypeMappingRegistry registry)
        throws Exception {

        this.registry = registry;
    }

    public void serialize(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context) {

        if (obj == null) {
            serializeNull(name, writer);
            return;
        }

        JAXRPCSerializer serializer = getSerializerForObject(obj);

        if (serializer != null) {
            serializer.serialize(obj, name, callback, writer, context);
        }
    }

    protected JAXRPCSerializer getSerializerForObject(Object obj) {
        JAXRPCSerializer serializer = null;
        try {
            serializer =
                (JAXRPCSerializer) registry.getSerializer(
                    soapEncodingConstants.getURIEncoding(),
                    obj.getClass());
            if (serializer instanceof DynamicSerializer) {
                throw new SerializationException(
                    "typemapping.serializer.is.dynamic",
                    new Object[] { obj.getClass()});
            }
        } catch (SerializationException e) {
            throw e;
        } catch (Exception e) {
            throw new SerializationException(
                "nestedSerializationError",
                new LocalizableExceptionAdapter(e));
        }

        return serializer;
    }

    protected void serializeNull(QName name, XMLWriter writer) {

        try {
            writer.startElement((name != null) ? name : QNAME_ANY);

            String attrVal = XMLWriterUtil.encodeQName(writer, super.type);
            writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);

            writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
            writer.endElement();
        } catch (JAXRPCExceptionBase e) {
            throw new SerializationException("nestedSerializationError", e);
        }
    }

    public Object deserialize(
        QName name,
        XMLReader reader,
        SOAPDeserializationContext context) {

        try {
            JAXRPCDeserializer deserializer =
                getDeserializerForElement(reader, context);

            if (deserializer == null) {
                return null;
            } else {
                return deserializer.deserialize(name, reader, context);
            }
        } catch (DeserializationException e) {
            throw e;
        } catch (Exception e) {
            throw new DeserializationException(
                "nestedDeserializationError",
                new LocalizableExceptionAdapter(e));
        }
    }

    protected JAXRPCDeserializer getDeserializerForElement(
        XMLReader reader,
        SOAPDeserializationContext context)
        throws Exception {

        if (getNullStatus(reader) == true) {
            skipEmptyContent(reader);
            return null;
        }

        QName objectXMLType = getType(reader);
        return (JAXRPCDeserializer) registry.getDeserializer(
            soapEncodingConstants.getURIEncoding(),
            objectXMLType);
    }
}

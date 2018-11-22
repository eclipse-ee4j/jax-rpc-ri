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

import java.util.Iterator;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import com.sun.xml.rpc.encoding.simpletype.AttachmentEncoder;
import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.streaming.XMLWriterUtil;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 * This class is used to serialize attachments in rpc/encoded mode
 * 
 * @author JAX-RPC Development Team
 */
public class AttachmentSerializer extends SimpleTypeSerializer {
    protected AttachmentEncoder attachmentEncoder;
    protected boolean serializerAsAttachment;

    private com.sun.xml.rpc.soap.SOAPEncodingConstants soapEncodingConstants =
        null;

    private void init(SOAPVersion ver) {
        soapEncodingConstants =
            SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public AttachmentSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        boolean serializerAsAttachment,
        SimpleTypeEncoder encoder) {

        this(
            type,
            encodeType,
            isNullable,
            encodingStyle,
            serializerAsAttachment,
            encoder,
            SOAPVersion.SOAP_11);
    }

    public AttachmentSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        boolean serializerAsAttachment,
        SimpleTypeEncoder encoder,
        SOAPVersion ver) {

        super(type, encodeType, isNullable, encodingStyle, encoder);
        init(ver); // Initialize SOAP constants
        this.serializerAsAttachment = serializerAsAttachment;
        if (encoder instanceof AttachmentEncoder) {
            this.attachmentEncoder = (AttachmentEncoder) encoder;
        } else if (serializerAsAttachment) {
            throw new SerializationException(
                "soap.no.attachment.encoder.and.serializeAsAttachment",
                type.toString());
        }
    }

    public AttachmentSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        boolean serializerAsAttachment,
        AttachmentEncoder encoder) {

        this(
            type,
            encodeType,
            isNullable,
            encodingStyle,
            serializerAsAttachment,
            encoder,
            SOAPVersion.SOAP_11);
    }

    public AttachmentSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        boolean serializerAsAttachment,
        AttachmentEncoder encoder,
        SOAPVersion ver) {

        super(type, encodeType, isNullable, encodingStyle, null);
        init(ver); // Initialize SOAP constants
        this.serializerAsAttachment = serializerAsAttachment;
        this.attachmentEncoder = encoder;
    }

    public void serialize(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context) {

        if (serializerAsAttachment) {
            serializeAsAttachment(obj, name, callback, writer, context);
        } else {
            if (encoder != null) {
                super.serialize(obj, name, callback, writer, context);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    private void serializeAsAttachment(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context) {

        try {
            writer.startElement((name != null) ? name : type);
            if (obj == null) {
                if (!isNullable) {
                    throw new SerializationException("xsd.unexpectedNull");
                }

                writer.writeAttributeUnquoted(QNAME_XSI_NIL, "1");
            } else {
                if (encodeType) {
                    String attrVal = XMLWriterUtil.encodeQName(writer, type);
                    writer.writeAttributeUnquoted(QNAME_XSI_TYPE, attrVal);
                }
                String id = context.nextID();
                writer.writeAttribute(
                    soapEncodingConstants.getQNameAttrHREF(),
                    "cid:" + id);
                SOAPMessage message = context.getMessage();
                AttachmentPart attachment =
                    message.createAttachmentPart(
                        attachmentEncoder.objectToDataHandler(obj));
                attachment.setContentId(id);
                message.addAttachmentPart(attachment);
            }

            writer.endElement();
        } catch (SerializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new SerializationException(e);
        } catch (Exception e) {
            throw new SerializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    public Object deserialize(
        QName name,
        XMLReader reader,
        SOAPDeserializationContext context) {

        boolean pushedEncodingStyle = false;
        try {

            String href = getHRef(reader);
            if (href != null) {
                skipEmptyContent(reader);

                SOAPMessage message = context.getMessage();
                MimeHeaders mimeHeaders = new MimeHeaders();
                mimeHeaders.addHeader("Content-Id", href.substring(4));
                Iterator attachments = message.getAttachments(mimeHeaders);
                if (!attachments.hasNext()) {
                    throw new DeserializationException(
                        "soap.missing.attachment.for.id",
                        href);
                }
                AttachmentPart attachment = (AttachmentPart) attachments.next();
                if (attachments.hasNext()) {
                    throw new DeserializationException(
                        "soap.multiple.attachments.for.id",
                        href);
                }

                return deserialize(attachment.getDataHandler(), context);
            }
        } catch (DeserializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        }
        return super.deserialize(name, reader, context);
    }

    public Object deserialize(
        DataHandler dataHandler,
        SOAPDeserializationContext context)
        throws DeserializationException, UnsupportedOperationException {

        if (attachmentEncoder == null)
            throw new UnsupportedOperationException();
        try {
            return attachmentEncoder.dataHandlerToObject(dataHandler);
        } catch (DeserializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    protected String getHRef(XMLReader reader) throws Exception {
        String href = null;

        Attributes attrs = reader.getAttributes();
        href = attrs.getValue("", "href");

        if (href != null) {
            if (!href.startsWith("cid:")) {
                throw new DeserializationException(
                    "soap.nonLocalReference",
                    href);
            }
        }

        return href;
    }
}

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

import com.sun.xml.rpc.encoding.simpletype.SimpleTypeConstants;
import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.streaming.XMLWriterUtil;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

import com.sun.xml.rpc.streaming.FastInfosetReader;
import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SimpleTypeSerializer
    extends SerializerBase
    implements SimpleTypeConstants {

    protected SimpleTypeEncoder encoder;

    public SimpleTypeSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        SimpleTypeEncoder encoder) {
            
        super(type, encodeType, isNullable, encodingStyle);
        this.encoder = encoder;
    }

    public void serialize(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context) {
            
        boolean pushedEncodingStyle = false;
        try {
            writer.startElement((name != null) ? name : type);
            if (callback != null) {
                callback.onStartTag(obj, name, writer, context);
            }

            if (encodingStyle != null)
                pushedEncodingStyle =
                    context.pushEncodingStyle(encodingStyle, writer);

            if (encodeType) {
                String attrVal = XMLWriterUtil.encodeQName(writer, type);
                writer.writeAttributeUnquoted(QNAME_XSI_TYPE, attrVal);
            }

            if (obj == null) {
                if (!isNullable) {
                    throw new SerializationException("xsd.unexpectedNull");
                }

                writer.writeAttributeUnquoted(QNAME_XSI_NIL, "1");
            } else {
                encoder.writeAdditionalNamespaceDeclarations(obj, writer);
                encoder.writeValue(obj, writer);
            }

            writer.endElement();
        } catch (SerializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new SerializationException(e);
        } catch (Exception e) {
            throw new SerializationException(
                new LocalizableExceptionAdapter(e));
        } finally {
            if (pushedEncodingStyle) {
                context.popEncodingStyle();
            }
        }
    }

    public Object deserialize(
        QName name,
        XMLReader reader,
        SOAPDeserializationContext context) {

        boolean pushedEncodingStyle = false;
        try {
            pushedEncodingStyle = context.processEncodingStyle(reader);
            if (encodingStyle != null)
                context.verifyEncodingStyle(encodingStyle);

            if (name != null) {
                QName actualName = reader.getName();
                if (!actualName.equals(name)) {
                    throw new DeserializationException(
                        "xsd.unexpectedElementName",
                        new Object[] { name.toString(), actualName.toString()});
                }
            }

            verifyType(reader);

            Attributes attrs = reader.getAttributes();

            String nullVal = attrs.getValue(URI_XSI, "nil");
            boolean isNull =
                (nullVal != null
                    && (nullVal.equals("true") || nullVal.equals("1")));

            reader.next();
            Object obj = null;

            if (isNull) {
                if (!isNullable) {
                    throw new DeserializationException("xsd.unexpectedNull");
                }
            } else {
                String val = null;

                switch (reader.getState()) {
                    case XMLReader.CHARS :
                        /*
                            * An FI reader *may* get bytes using an encoding algorithm. If so, return
                            * the bytes directly without base64 decoding.
                            */
                        if (reader instanceof FastInfosetReader &&
                            encoder instanceof XSDBase64BinaryEncoder) 
                        {
                            final FastInfosetReader fiReader = (FastInfosetReader) reader;
                            obj = fiReader.getTextAlgorithmBytes();
                            if (obj != null && fiReader.getTextAlgorithmIndex() ==
                                    org.jvnet.fastinfoset.EncodingAlgorithmIndexes.BASE64) 
                            {
                                obj = fiReader.getTextAlgorithmBytesClone();
                                reader.next();
                                break;      // ends switch
                            } 
                        }
                         
                        val = reader.getValue();
                        obj = encoder.stringToObject(val, reader);
                        reader.next();
                        break;
                    case XMLReader.END :
                        val = "";
                        obj = encoder.stringToObject(val, reader);
                        break;
                }
            }

            XMLReaderUtil.verifyReaderState(reader, XMLReader.END);

            return obj;
        } catch (DeserializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        } finally {
            if (pushedEncodingStyle) {
                context.popEncodingStyle();
            }
        }
    }
}

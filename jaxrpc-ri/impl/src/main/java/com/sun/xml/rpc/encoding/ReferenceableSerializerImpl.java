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

import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ReferenceableSerializerImpl
    extends SerializerBase
    implements Initializable, ReferenceableSerializer, SerializerCallback {

    private CombinedSerializer serializer;
    private boolean serializeAsRef;

    private com.sun.xml.rpc.soap.SOAPEncodingConstants soapEncodingConstants =
        null;

    private void init(SOAPVersion ver) {
        this.soapEncodingConstants =
            SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public ReferenceableSerializerImpl(
        boolean serializeAsRef,
        CombinedSerializer serializer) {
        this(serializeAsRef, serializer, SOAPVersion.SOAP_11);
    }

    public ReferenceableSerializerImpl(
        boolean serializeAsRef,
        CombinedSerializer serializer,
        SOAPVersion ver) {
        super(
            serializer.getXmlType(),
            serializer.getEncodeType(),
            serializer.isNullable(),
            serializer.getEncodingStyle());
        init(ver); // Initialize the SOAP constants
        this.serializer = serializer;
        this.serializeAsRef = serializeAsRef;
    }

    public void initialize(InternalTypeMappingRegistry registry)
        throws Exception {
        if (serializer instanceof Initializable)
             ((Initializable) serializer).initialize(registry);
    }

    public CombinedSerializer getInnermostSerializer() {
        return serializer.getInnermostSerializer();
    }

    public void serialize(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context) {
        boolean pushedEncodingStyle = false;
        try {
            if (!serializeAsRef || obj == null) {
                serializer.serialize(obj, name, null, writer, context);
            } else {
                if (!context.isRegistered(obj)
                    && (context.getSOAPVersion() == SOAPVersion.SOAP_12)) {
                    context.registerObject(obj, this);
                    serializer.serialize(obj, name, this, writer, context);
                } else {
                    SOAPSerializationState state =
                        context.registerObject(obj, this);
                    writer.startElement((name != null) ? name : type);
                    if (typeIsEmpty()) {
                        throw new SerializationException("soap.unspecifiedType");
                    }
                    if (encodingStyle != null)
                        pushedEncodingStyle =
                            context.pushEncodingStyle(encodingStyle, writer);
                    if (context.getSOAPVersion() == SOAPVersion.SOAP_11)
                        writer.writeAttribute(
                            soapEncodingConstants.getQNameAttrHREF(),
                            "#" + state.getID());
                    else if (context.getSOAPVersion() == SOAPVersion.SOAP_12)
                        writer.writeAttribute(
                            soapEncodingConstants.getQNameAttrHREF(),
                            state.getID());

                    writer.endElement();
                }
            }
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
        try {
            String href = null;
            if (context.getSOAPVersion() == SOAPVersion.SOAP_11)
                href = getHRef(reader);
            else if (context.getSOAPVersion() == SOAPVersion.SOAP_12)
                href = getIDRef(reader);

            if (href != null) {
                // is it an Attachment
                if (href.startsWith("cid:")) {
                    return serializer.deserialize(name, reader, context);
                } else {
                    skipEmptyContent(reader);
                    SOAPDeserializationState state = context.getStateFor(href);
                    state.setDeserializer(this);

                    if (state.isComplete()) {
                        return state.getInstance();
                    } else {
                        return state;
                    }
                }
            }

            String id = getID(reader);
            boolean isNull = getNullStatus(reader);
            if (!isNull) {
                SOAPDeserializationState state = null;
                Object instance = serializer.deserialize(name, reader, context);

                if (id != null) {
                    state = context.getStateFor(id);
                }

                /* TODO: This eventually needs to be removed to handle inheritence 
                 * of exceptions and value types when no xsi:type information is 
                 * included on the element.  We do this because we fall back to the 
                 * base type serializer in this case and not all of the elements are
                 * consumed, they should be consumed by the 
                 * base_type_inerface_SOAPSerializer.
                 */
                //                XMLReaderUtil.verifyReaderState(reader, XMLReader.END);

                if (instance instanceof SOAPDeserializationState) {
                    state = (SOAPDeserializationState) instance;
                    state.setDeserializer(this);
                } else if (state != null) {
                    state.setInstance(instance);
                    state.setDeserializer(this);
                }

                if (state != null) {
                    state.doneReading();
                    return state;
                }

                return instance;
            } else { // NULL
                serializer.deserialize(name, reader, context);

                if (id != null) {
                    SOAPDeserializationState state = context.getStateFor(id);
                    state.setDeserializer(this);
                    state.setInstance(null);
                    state.doneReading();
                }

                return null;
            }
        } catch (DeserializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    public Object deserialize(
        DataHandler dataHandler,
        SOAPDeserializationContext context) {
        return serializer.deserialize(dataHandler, context);
    }

    public void serializeInstance(
        Object obj,
        QName name,
        boolean isMultiRef,
        XMLWriter writer,
        SOAPSerializationContext context) {
        SerializerCallback callback = isMultiRef ? this : null;
        serializer.serialize(obj, name, callback, writer, context);
    }

    public void onStartTag(
        Object obj,
        QName name,
        XMLWriter writer,
        SOAPSerializationContext context) {

        if (!serializeAsRef) {
            return;
        }
        try {
            SOAPSerializationState state = context.registerObject(obj, this);
            writer.writeAttribute(
                soapEncodingConstants.getQNameAttrID(),
                state.getID());
        } catch (SerializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new SerializationException(e);
        } catch (Exception e) {
            throw new SerializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    protected String getHRef(XMLReader reader) throws Exception {
        String href = null;

        Attributes attrs = reader.getAttributes();
        href = attrs.getValue("", "href");

        if (href != null) {
            // remove leading #
            if (href.charAt(0) == '#') {
                href = href.substring(1);
            } else if (!href.startsWith("cid:")) {
                throw new DeserializationException(
                    "soap.nonLocalReference",
                    href);
            }
        }

        return href;
    }

    private String getIDRef(XMLReader reader) throws Exception {
        String href = null;

        Attributes attrs = reader.getAttributes();
        href =
            attrs.getValue(
                "",
                soapEncodingConstants.getQNameAttrHREF().getLocalPart());
        return href;
    }
}

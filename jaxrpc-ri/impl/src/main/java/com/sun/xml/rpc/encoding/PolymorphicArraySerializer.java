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

import java.lang.reflect.Array;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.streaming.XMLWriterUtil;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class PolymorphicArraySerializer
    extends SerializerBase
    implements Initializable {

    protected QName elemName;
    protected InternalTypeMappingRegistry registry;
    protected ArraySerializerHelper helper;

    private com.sun.xml.rpc.soap.SOAPEncodingConstants soapEncodingConstants =
        null;

    private void init(SOAPVersion ver) {
        if (ver.toString().equals(SOAPVersion.SOAP_12.toString())) {
            helper = new SOAP12ArraySerializerHelper();
        } else
            helper = new SOAP11ArraySerializerHelper();

        soapEncodingConstants =
            SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public PolymorphicArraySerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName elemName) {

        this(
            type,
            encodeType,
            isNullable,
            encodingStyle,
            elemName,
            SOAPVersion.SOAP_11);
    }

    public PolymorphicArraySerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName elemName,
        SOAPVersion ver) {

        super(type, encodeType, isNullable, encodingStyle);
        init(ver); // Initialize SOAP version
        this.elemName = elemName;
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
        SOAPSerializationContext context)
        throws SerializationException {

        boolean pushedEncodingStyle = false;
        try {
            if (obj == null) {
                if (!isNullable) {
                    throw new SerializationException("soap.unexpectedNull");
                }

                serializeNull(name, writer, context);
            } else {
                if (!obj.getClass().isArray()) {
                    throw new SerializationException(
                        "type.is.not.array",
                        new Object[] { obj.getClass().getName()});
                }
                String attrVal;

                writer.startElement((name != null) ? name : type);
                if (callback != null) {
                    callback.onStartTag(obj, name, writer, context);
                }

                pushedEncodingStyle =
                    context.pushEncodingStyle(encodingStyle, writer);

                if (encodeType) {
 
                    // instead of writing the actual array type (as above), always
                    // use soapenc:Array as the value of xsi:type, for compatibility
                    // with some SOAP implementations out there (e.g. Apache SOAP)
                    attrVal =
                        XMLWriterUtil.encodeQName(
                            writer,
                            soapEncodingConstants.getQNameEncodingArray());
                    writer.writeAttributeUnquoted(
                        XSDConstants.QNAME_XSI_TYPE,
                        attrVal);
                }

                int[] dims =
                    ArraySerializerBase.getArrayDimensions(
                        obj,
                        getArrayRank(obj));
                String encodedDims = helper.encodeArrayDimensions(dims);
                QName xmlType =
                    registry.getXmlType(encodingStyle, java.lang.Object.class);
                if (xmlType == null) {
                    throw new SerializationException(
                        "typemapping.serializerNotRegistered",
                        new Object[] { obj.getClass().getName()});
                }
                helper.serializeArray(xmlType, encodedDims, writer);
                serializeArrayInstance(obj, dims, writer, context);

                writer.endElement();
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

    protected void serializeArrayInstance(
        Object obj,
        int[] dims,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        serializeArrayElements((Object[]) obj, 0, dims, writer, context);
    }

    protected void serializeArrayElements(
        Object[] arr,
        int level,
        int[] dims,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        if (arr == null || arr.length != dims[level]) {
            throw new SerializationException("soap.irregularMultiDimensionalArray");
        }

        boolean serializeLeaves = false;
        JAXRPCSerializer elemSer = null;

        if (level == dims.length - 1) {
            serializeLeaves = true;
            elemSer =
                (JAXRPCSerializer) registry.getSerializer(
                    encodingStyle,
                    arr.getClass().getComponentType());
        }

        for (int i = 0; i < dims[level]; ++i) {
            Object elem = arr[i];
            if (serializeLeaves) {
                elemSer.serialize(elem, elemName, null, writer, context);
            } else {
                serializeArrayElements(
                    (Object[]) elem,
                    level + 1,
                    dims,
                    writer,
                    context);
            }
        }
    }

    protected void serializeNull(
        QName name,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {
            
        String attrVal;
        boolean pushedEncodingStyle = false;
        writer.startElement((name != null) ? name : type);

        pushedEncodingStyle = context.pushEncodingStyle(encodingStyle, writer);

        if (encodeType) {
            attrVal = XMLWriterUtil.encodeQName(writer, type);
            writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
        }

        writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");

        writer.endElement();
        if (pushedEncodingStyle) {
            context.popEncodingStyle();
        }
    }

    public Object deserialize(
        QName name,
        XMLReader reader,
        SOAPDeserializationContext context) {

        boolean pushedEncodingStyle = false;
        try {
            pushedEncodingStyle = context.processEncodingStyle(reader);
            context.verifyEncodingStyle(encodingStyle);

            if (name != null) {
                verifyName(reader, name);
            }

            boolean isNull = getNullStatus(reader);
            if (!isNull) {
                // NOTE - just calling
                //      verifyType(reader);
                // here is too strict, because instead of, say, <myns:ArrayOfFoo> a client
                // can send <soap-enc:Array> (as long as the array type matches, but that is
                // tested later in the code)

                QName actualType = getType(reader);
                if (actualType != null) {
                    if (!actualType.equals(type)
                        && !actualType.equals(
                            soapEncodingConstants.getQNameEncodingArray())) {
                        throw new DeserializationException(
                            "soap.unexpectedElementType",
                            new Object[] {
                                type.toString(),
                                actualType.toString()});
                    }
                }

                String arrayType = null;
                Attributes attrs = reader.getAttributes();
                arrayType = helper.getArrayType(attrs);
                if (arrayType == null) {
                    throw new DeserializationException(
                        "soap.malformedArrayType",
                        "<arrayType attribute missing>");
                }
                int[] dims =
                    ArraySerializerBase.getArrayDimensions(arrayType, reader);
                QName elemXmlType =
                    ArraySerializerBase.getArrayElementType(arrayType, reader);
                Class elemJavaType =
                    registry.getJavaType(encodingStyle, elemXmlType);
                if (elemJavaType == null) {
                    throw new SerializationException(
                        "typemapping.deserializerNotRegistered",
                        new Object[] { elemXmlType });
                }
                JAXRPCDeserializer elemDeser =
                    (JAXRPCDeserializer) registry.getDeserializer(
                        encodingStyle,
                        elemXmlType);

                Object rslt =
                    deserializeArrayInstance(
                        reader,
                        context,
                        dims,
                        elemJavaType,
                        elemDeser);
                XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
                return rslt;
            } else {
                if (!isNullable) {
                    throw new DeserializationException("soap.unexpectedNull");
                }

                skipEmptyContent(reader);

                return null;
            }
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

    protected Object deserializeArrayInstance(
        XMLReader reader,
        SOAPDeserializationContext context,
        int[] dims,
        Class elemClass,
        JAXRPCDeserializer elemDeser)
        throws Exception {

        String id = getID(reader);
        SOAPDeserializationState state =
            ((id != null) ? context.getStateFor(id) : null);
        boolean isComplete = true;
        boolean emptyDims = ArraySerializerBase.isEmptyDimensions(dims);
        final int[] dimOffsets = ArraySerializerBase.getDimensionOffsets(dims);

        int[] offset = ArraySerializerBase.getArrayOffset(reader, dims);
        if (offset == null) {
            offset = new int[emptyDims ? 1 : dims.length];
        }

        Object[] value = null;
        int maxPosition = 0;
        int length = 0;

        if (reader.nextElementContent() != XMLReader.END) {
            int[] position =
                ArraySerializerBase.getArrayElementPosition(reader, dims);
            boolean isSparseArray = (position != null);

            if (!isSparseArray) {
                position = offset;
            }

            if (emptyDims) {
                maxPosition = position[0];
                length = Math.max(maxPosition * 2, 1024);
                value = (Object[]) Array.newInstance(elemClass, length);
            } else {
                value = (Object[]) Array.newInstance(elemClass, dims);
            }

            while (true) {
                if (!emptyDims
                    && !ArraySerializerBase.isPositionWithinBounds(
                        position,
                        dims)) {
                    if (isSparseArray) {
                        throw new DeserializationException(
                            "soap.outOfBoundsArrayElementPosition",
                            ArraySerializerBase.encodeArrayDimensions(
                                position));
                    } else {
                        throw new DeserializationException("soap.tooManyArrayElements");
                    }
                }

                if (emptyDims) {
                    if (position[0] >= length) {
                        int newLength = length * 2;
                        while (position[0] >= newLength) {
                            newLength *= 2;
                        }
                        Object[] newValue =
                            (Object[]) Array.newInstance(elemClass, newLength);
                        System.arraycopy(value, 0, newValue, 0, length);
                        value = newValue;
                        length = newLength;
                    }
                }

                Object elem = null;
                elem = elemDeser.deserialize(elemName, reader, context);

                if (elem instanceof SOAPDeserializationState) {
                    SOAPDeserializationState elemState =
                        (SOAPDeserializationState) elem;
                    isComplete = false;

                    if (state == null) {
                        // i'm a single-ref instance
                        state = new SOAPDeserializationState();
                    }

                    // ensure that state (and therefore builder) contains a reference
                    // to the current array since registerListener could call back
                    // on the builder if the element object has already been created
                    state.setInstance(value);

                    if (state.getBuilder() == null) {
                        state.setBuilder(
                            new ObjectArrayInstanceBuilder(dimOffsets));
                    }

                    elemState.registerListener(
                        state,
                        ArraySerializerBase.indexFromPosition(
                            position,
                            dimOffsets));
                } else {
                    ObjectArraySerializer.setElement(value, position, elem);
                }

                if (reader.nextElementContent() == XMLReader.END) {
                    break;
                }

                if (isSparseArray) {
                    position =
                        ArraySerializerBase.getArrayElementPosition(
                            reader,
                            dims);
                    if (position == null) {
                        // all elements of a sparse array must have a position attribute
                        throw new DeserializationException("soap.missingArrayElementPosition");
                    }
                } else {
                    if (emptyDims) {
                        ++position[0];
                    } else {
                        ArraySerializerBase.incrementPosition(position, dims);
                    }
                }

                if (emptyDims) {
                    maxPosition = Math.max(position[0], maxPosition);
                }
            }

            if (emptyDims) {
                if (length != maxPosition + 1) {
                    int newLength = maxPosition + 1;
                    Object[] newValue =
                        (Object[]) Array.newInstance(elemClass, newLength);
                    System.arraycopy(value, 0, newValue, 0, newLength);
                    value = newValue;
                    length = newLength;
                }
            }
        } else {
            if (emptyDims) {
                value = (Object[]) Array.newInstance(elemClass, 0);
            } else {
                value = (Object[]) Array.newInstance(elemClass, dims);
            }
        }

        if (state != null) {
            state.setDeserializer(this);
            state.setInstance(value);
            state.doneReading();
        }

        if (isComplete) {
            return value;
        } else {
            return state;
        }
    }

    // keep
    protected int getArrayRank(Object obj) {
        int rank = 0;
        Class type = obj.getClass();
        while (type.isArray()) {
            ++rank;
            type = type.getComponentType();
        }
        return rank;
    }

    private class ObjectArrayInstanceBuilder implements SOAPInstanceBuilder {

        Object[] instance = null;
        int[] dimOffsets = null;

        ObjectArrayInstanceBuilder(int[] dimOffsets) {
            this.dimOffsets = dimOffsets;
        }

        public int memberGateType(int memberIndex) {
            return (
                SOAPInstanceBuilder.GATES_INITIALIZATION
                    | SOAPInstanceBuilder.REQUIRES_CREATION);
        }

        public void construct() {
            throw new IllegalStateException();
        }

        public void setMember(int index, Object memberValue) {
            int[] position =
                ArraySerializerBase.positionFromIndex(index, dimOffsets);
            ObjectArraySerializer.setElement(instance, position, memberValue);
        }

        public void initialize() {
            return;
        }

        public void setInstance(Object instance) {
            this.instance = (Object[]) instance;
        }

        public Object getInstance() {
            return instance;
        }
    }
}

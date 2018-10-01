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

import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SimpleTypeArraySerializer extends ArraySerializerBase {

    protected SimpleTypeSerializer elemSer;
    protected Class encoderElemClass;

    public SimpleTypeArraySerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName elemName,
        QName elemType,
        Class elemClass,
        int rank,
        int[] dims,
        SimpleTypeSerializer elemSer) {
            
        this(
            type,
            encodeType,
            isNullable,
            encodingStyle,
            elemName,
            elemType,
            elemClass,
            rank,
            dims,
            elemSer,
            SOAPVersion.SOAP_11);
    }

    public SimpleTypeArraySerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName elemName,
        QName elemType,
        Class elemClass,
        int rank,
        int[] dims,
        SimpleTypeSerializer elemSer,
        SOAPVersion version) {
            
        super(
            type,
            encodeType,
            isNullable,
            encodingStyle,
            elemName,
            elemType,
            elemClass,
            rank,
            dims,
            version);
        this.elemSer = elemSer;
    }

    protected void serializeArrayInstance(
        Object obj,
        int[] dims,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        serializeArrayElements(obj, 0, dims, writer, context);
    }

    protected void serializeArrayElements(
        Object obj,
        int level,
        int[] dims,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        if (obj == null || Array.getLength(obj) != dims[level]) {
            throw new SerializationException("soap.irregularMultiDimensionalArray");
        }

        boolean serializeLeaves = (level == dims.length - 1);

        for (int i = 0; i < dims[level]; ++i) {
            if (serializeLeaves) {
                serializeElement(obj, i, writer, context);
            } else {
                serializeArrayElements(
                    Array.get(obj, i),
                    level + 1,
                    dims,
                    writer,
                    context);
            }
        }
    }

    protected void serializeElement(
        Object obj,
        int index,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        Object elem = Array.get(obj, index);
        elemSer.serialize(elem, elemName, null, writer, context);
    }

    protected Object deserializeArrayInstance(
        XMLReader reader,
        SOAPDeserializationContext context,
        int[] dims)
        throws Exception {

        boolean emptyDims = isEmptyDimensions(dims);

        int[] offset = getArrayOffset(reader, dims);
        if (offset == null) {
            offset = new int[emptyDims ? 1 : dims.length];
        }

        Object value = null;
        int maxPosition = 0;
        int length = 0;

        if (reader.nextElementContent() != XMLReader.END) {
            int[] position = getArrayElementPosition(reader, dims);
            boolean isSparseArray = (position != null);

            if (!isSparseArray) {
                position = offset;
            }

            if (emptyDims) {
                maxPosition = position[0];
                length = Math.max(maxPosition * 2, 1024);
                value = Array.newInstance(elemClass, length);
            } else {
                value = Array.newInstance(elemClass, dims);
            }

            while (true) {
                if (!emptyDims && !isPositionWithinBounds(position, dims)) {
                    if (isSparseArray) {
                        throw new DeserializationException(
                            "soap.outOfBoundsArrayElementPosition",
                            encodeArrayDimensions(position));
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
                        Object newValue =
                            Array.newInstance(elemClass, newLength);
                        System.arraycopy(value, 0, newValue, 0, length);
                        value = newValue;
                        length = newLength;
                    }
                }

                deserializeElement(value, position, reader, context);

                if (reader.nextElementContent() == XMLReader.END) {
                    break;
                }

                if (isSparseArray) {
                    position = getArrayElementPosition(reader, dims);
                    if (position == null) {
                        // all elements of a sparse array must have a position attribute
                        throw new DeserializationException("soap.missingArrayElementPosition");
                    }
                } else {
                    if (emptyDims) {
                        ++position[0];
                    } else {
                        incrementPosition(position, dims);
                    }
                }

                if (emptyDims) {
                    maxPosition = Math.max(position[0], maxPosition);
                }
            }

            if (emptyDims) {
                if (length != maxPosition + 1) {
                    int newLength = maxPosition + 1;
                    Object newValue = Array.newInstance(elemClass, newLength);
                    System.arraycopy(value, 0, newValue, 0, newLength);
                    value = newValue;
                    length = newLength;
                }
            }
        } else {
            if (emptyDims) {
                value = Array.newInstance(elemClass, 0);
            } else {
                value = Array.newInstance(elemClass, dims);
            }
        }

        return value;
    }

    protected void deserializeElement(
        Object value,
        int[] position,
        XMLReader reader,
        SOAPDeserializationContext context)
        throws Exception {

        Object arr = value;
        for (int i = 0; i < position.length - 1; ++i) {
            arr = Array.get(arr, position[i]);
        }

        deserializeElement(arr, position[position.length - 1], reader, context);
    }

    protected void deserializeElement(
        Object value,
        int position,
        XMLReader reader,
        SOAPDeserializationContext context)
        throws Exception {
        Array.set(value, position, elemSer.deserialize(null, reader, context));
    }
}

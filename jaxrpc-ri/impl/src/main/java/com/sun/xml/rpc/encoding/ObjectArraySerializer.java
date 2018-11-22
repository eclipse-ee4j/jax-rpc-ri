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
public class ObjectArraySerializer
    extends ArraySerializerBase
    implements Initializable {

    protected JAXRPCSerializer elemSer;
    protected JAXRPCDeserializer elemDeser;

    public ObjectArraySerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName elemName,
        QName elemType,
        Class elemClass,
        int rank,
        int[] dims) {
            
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
            SOAPVersion.SOAP_11);
    }

    public ObjectArraySerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName elemName,
        QName elemType,
        Class elemClass,
        int rank,
        int[] dims,
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
    }

    public void initialize(InternalTypeMappingRegistry registry)
        throws Exception {
            
        elemSer =
            (JAXRPCSerializer) registry.getSerializer(
                encodingStyle,
                elemClass,
                elemType);
        elemDeser =
            (JAXRPCDeserializer) registry.getDeserializer(
                encodingStyle,
                elemClass,
                elemType);
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

        boolean serializeLeaves = (level == dims.length - 1);

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

    protected Object deserializeArrayInstance(
        XMLReader reader,
        SOAPDeserializationContext context,
        int[] dims)
        throws Exception {

        String id = getID(reader);
        SOAPDeserializationState state =
            ((id != null) ? context.getStateFor(id) : null);
        boolean isComplete = true;
        int arbitraryArrayLength = 1024;
        int maxIncomingArrayLength = 4096;
        boolean emptyDims = isEmptyDimensions(dims);
        
        // if array lengths too large, ignore them in case they are bogus
        if (emptyDims == false && dimLargerThan(dims, maxIncomingArrayLength)) {
            emptyDims = true;
            arbitraryArrayLength = maxIncomingArrayLength;
        }
        
        final int[] dimOffsets = getDimensionOffsets(dims);

        int[] offset = getArrayOffset(reader, dims);
        if (offset == null) {
            offset = new int[emptyDims ? 1 : dims.length];
        }

        Object[] value = null;
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
                length = Math.max(maxPosition * 2, arbitraryArrayLength);
                value = (Object[]) Array.newInstance(elemClass, length);
            } else {
                value = (Object[]) Array.newInstance(elemClass, dims);
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
                        Object[] newValue =
                            (Object[]) Array.newInstance(elemClass, newLength);
                        System.arraycopy(value, 0, newValue, 0, length);
                        value = newValue;
                        length = newLength;
                    }
                }

                Object elem = null;
                elem = elemDeser.deserialize(null, reader, context);

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
                        indexFromPosition(position, dimOffsets));
                } else {
                    setElement(value, position, elem);
                }

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
                
                // trim the new array to the actual size
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

    public static void setElement(
        Object[] value,
        int[] position,
        Object elem) {
            
        Object[] arr = value;
        for (int i = 0; i < position.length - 1; ++i) {
            arr = (Object[]) arr[position[i]];
        }

        arr[position[position.length - 1]] = elem;
    }

    private boolean dimLargerThan(int [] dims, int maxLength) {
        for (int i=0; i<dims.length; i++) {
            if (dims[i] > maxLength) {
                return true;
            }
        }
        return false;
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
            int[] position = positionFromIndex(index, dimOffsets);
            setElement(instance, position, memberValue);
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

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

package com.sun.xml.rpc.encoding.soap;

import java.util.Iterator;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
import com.sun.xml.rpc.encoding.JAXRPCSerializer;
import com.sun.xml.rpc.encoding.ObjectSerializerBase;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPDeserializationState;
import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.util.StructMap;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 * A data-driven (de)serializer.
 *
 * @author JAX-RPC Development Team
 */

public class StructMapSerializer
    extends ObjectSerializerBase
    implements Initializable {
        
    protected InternalTypeMappingRegistry registry;

    public StructMapSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle) {
            
        super(type, encodeType, isNullable, encodingStyle);
    }

    public void initialize(InternalTypeMappingRegistry registry)
        throws Exception {
            
        this.registry = registry;
    }

    protected void doSerializeInstance(
        Object instance,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        StructMap struct = (StructMap) instance;
        Iterator eachKey = struct.keys().iterator();
        Iterator eachValue = struct.values().iterator();

        while (eachKey.hasNext()) {
            Object value = eachValue.next();
            QName key = (QName) eachKey.next();

            if (value != null) {
                JAXRPCSerializer serializer =
                    (JAXRPCSerializer) registry.getSerializer(
                        encodingStyle,
                        value.getClass());
                serializer.serialize(value, key, null, writer, context);
            } else {
                serializeNull(key, writer, context);
            }
        }
    }

    protected Object doDeserialize(
        SOAPDeserializationState state,
        XMLReader reader,
        SOAPDeserializationContext context)
        throws Exception {

        StructMap instance = new StructMap();
        Object member;
        StructMapBuilder builder = null;
        boolean isComplete = true;

        int memberIndex = 0;
        //bug fix 484627
        while (reader.nextElementContent() != XMLReader.END) {
            QName key = reader.getName();
            if (!getNullStatus(reader)) {
                JAXRPCDeserializer deserializer =
                    (JAXRPCDeserializer) registry.getDeserializer(
                        encodingStyle,
                        getType(reader));
                member = deserializer.deserialize(key, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new StructMapBuilder(instance);
                    }
                    state =
                        registerWithMemberState(
                            instance,
                            state,
                            member,
                            memberIndex,
                            builder);
                    isComplete = false;
                }

                // We have to reserve space for the eventual value and remember the name of the member no matter what
                instance.put(key, member);
            } else {
                instance.put(key, null);
            }
        }

        return (isComplete ? (Object) instance : (Object) state);
    }

    protected class StructMapBuilder implements SOAPInstanceBuilder {

        StructMap instance;

        StructMapBuilder(StructMap instance) {
            this.instance = instance;
        }

        public int memberGateType(int memberIndex) {
            return (
                SOAPInstanceBuilder.GATES_INITIALIZATION
                    | SOAPInstanceBuilder.REQUIRES_CREATION);
        }

        public void construct() {
            return;
        }

        public void setMember(int index, Object memberValue) {
            try {
                instance.set(index, memberValue);
            } catch (Exception e) {
                throw new DeserializationException(
                    "nestedSerializationError",
                    new LocalizableExceptionAdapter(e));
            }
        }

        public void initialize() {
            return;
        }

        public void setInstance(Object instance) {
            instance = (StructMap) instance;
        }

        public Object getInstance() {
            return instance;
        }
    }
}

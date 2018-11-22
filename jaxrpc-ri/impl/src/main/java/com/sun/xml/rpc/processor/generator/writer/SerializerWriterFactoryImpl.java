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

/*
 * SerializerWriter.java
 *
 * Created on January 11, 2002, 12:58 PM
 */

package com.sun.xml.rpc.processor.generator.writer;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.rpc.processor.generator.GeneratorException;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.processor.model.AbstractType;
import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
import com.sun.xml.rpc.processor.model.literal.LiteralListType;
import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
import com.sun.xml.rpc.processor.model.soap.SOAPListType;
import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
import com.sun.xml.rpc.processor.model.soap.SOAPType;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SerializerWriterFactoryImpl implements SerializerWriterFactory {
    private Map writerMap;
    private Names names;

    public SerializerWriterFactoryImpl(Names names) {
        writerMap = new HashMap();
        this.names = names;
    }

    public SerializerWriter createWriter(
        String basePackage,
        AbstractType type) {
        if (type == null)
            return null;
        SerializerWriter writer = getTypeSerializerWriter(type);
        if (writer == null) {
            if (type instanceof SOAPAnyType) {
                writer = new DynamicSerializerWriter((SOAPType) type, names);
            } else if (type instanceof SOAPSimpleType) {
                if (CollectionSerializerWriter.handlesType(type)) {
                    writer =
                        new CollectionSerializerWriter((SOAPType) type, names);
                } else {
                    writer =
                        new SimpleTypeSerializerWriter((SOAPType) type, names);
                }
            } else if (type instanceof SOAPStructureType) {
                if (((SOAPStructureType) type).getSubtypes() != null) {
                    writer =
                        new InterfaceSerializerWriter(
                            basePackage,
                            (SOAPType) type,
                            names);
                } else {
                    writer =
                        new SOAPObjectSerializerWriter(
                            basePackage,
                            (SOAPType) type,
                            names);
                }
            } else if (type instanceof SOAPArrayType) {
                writer =
                    new ArraySerializerWriter(
                        basePackage,
                        (SOAPType) type,
                        names);
            } else if (type instanceof SOAPEnumerationType) {
                writer =
                    new EnumerationSerializerWriter(
                        basePackage,
                        (SOAPType) type,
                        names);
            } else if (type instanceof LiteralEnumerationType) {
                writer =
                    new LiteralEnumerationSerializerWriter(
                        basePackage,
                        (LiteralType) type,
                        names);
            } else if (type instanceof SOAPCustomType) {
                writer = new CustomSerializerWriter((SOAPType) type, names);
            } else if (type instanceof LiteralFragmentType) {
                writer =
                    new LiteralFragmentSerializerWriter(
                        (LiteralFragmentType) type,
                        names);
            } else if (type instanceof LiteralSimpleType) {
                writer =
                    new LiteralSimpleSerializerWriter(
                        (LiteralSimpleType) type,
                        names);
            } else if (type instanceof LiteralSequenceType) {
                if (((LiteralSequenceType) type).getSubtypes() != null) {
                    writer =
                        new LiteralInterfaceSerializerWriter(
                            basePackage,
                            (LiteralType) type,
                            names);
                } else {
                    writer =
                        new LiteralSequenceSerializerWriter(
                            basePackage,
                            (LiteralType) type,
                            names);
                }
            } else if (type instanceof LiteralAllType) {
                // we can reuse the sequence serializer writer for "all" types too!
                if (((LiteralAllType) type).getSubtypes() != null) {
                    writer =
                        new LiteralInterfaceSerializerWriter(
                            basePackage,
                            (LiteralType) type,
                            names);
                } else {
                    writer =
                        new LiteralSequenceSerializerWriter(
                            basePackage,
                            (LiteralType) type,
                            names);
                }
            } else if (type instanceof LiteralArrayType) {
                // we can reuse the sequence serializer writer for "array" types too!
                writer =
                    new LiteralSequenceSerializerWriter(
                        basePackage,
                        (LiteralArrayType) type,
                        names);
            } else if (type instanceof LiteralArrayWrapperType) {
                // we can reuse the sequence serializer writer for "array" types too!
                writer =
                    new LiteralSequenceSerializerWriter(
                        basePackage,
                        (LiteralArrayWrapperType) type,
                        names);
            } else if (type instanceof LiteralListType) {
                writer =
                    new LiteralSimpleSerializerWriter(
                        (LiteralListType) type,
                        names);
            } else if (type instanceof LiteralIDType) {
                writer =
                    new LiteralSimpleSerializerWriter(
                        (LiteralIDType) type,
                        names);
            } else if (type instanceof SOAPListType) {
                writer =
                    new SimpleTypeSerializerWriter((SOAPListType) type, names);
            }
            if (writer == null) {
                throw new GeneratorException(
                    "generator.unsupported.type.encountered",
                    new Object[] {
                        type.getName().getLocalPart(),
                        type.getName().getNamespaceURI()});
            }
            setTypeSerializerWriter(type, writer);
        }
        return writer;
    }

    private SerializerWriter getTypeSerializerWriter(AbstractType type) {
        String key = genKey(type);
        SerializerWriter writer = (SerializerWriter) writerMap.get(key);
        return writer;
    }

    private void setTypeSerializerWriter(
        AbstractType type,
        SerializerWriter writer) {
        String key = genKey(type);
        writerMap.put(key, writer);
    }

    protected static String genKey(AbstractType type) {
        String schemaType = type.getName().toString();
        String javaType = type.getJavaType().getRealName();
        String typeType;
        if (type instanceof LiteralListType)
            typeType = type.toString();
        else
            typeType = type.getClass().getName();
        return schemaType + ";" + javaType + ";" + typeType;
    }
}

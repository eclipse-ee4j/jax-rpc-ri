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
import javax.xml.rpc.encoding.Deserializer;
import javax.xml.rpc.encoding.DeserializerFactory;
import javax.xml.rpc.encoding.Serializer;
import javax.xml.rpc.encoding.SerializerFactory;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

/**
 *
 * @author JAX-RPC Development Team
 */
public class TypeMappingUtil {

    public static TypeMapping getTypeMapping(
        TypeMappingRegistry registry,
        String encodingStyle)
        throws Exception {

        TypeMapping mapping = registry.getTypeMapping(encodingStyle);

        if (mapping == null) {
            throw new TypeMappingException(
                "typemapping.noMappingForEncoding",
                encodingStyle);
        }

        return mapping;
    }

    public static Serializer getSerializer(
        TypeMapping mapping,
        Class javaType,
        QName xmlType)
        throws Exception {

        SerializerFactory sf = mapping.getSerializer(javaType, xmlType);

        if (sf == null) {
            throw new TypeMappingException(
                "typemapping.serializerNotRegistered",
                new Object[] { javaType, xmlType });
        }

        return sf.getSerializerAs(EncodingConstants.JAX_RPC_RI_MECHANISM);
    }

    public static Deserializer getDeserializer(
        TypeMapping mapping,
        Class javaType,
        QName xmlType)
        throws Exception {

        DeserializerFactory df = mapping.getDeserializer(javaType, xmlType);

        if (df == null) {
            throw new TypeMappingException(
                "typemapping.deserializerNotRegistered",
                new Object[] { javaType, xmlType });
        }

        return df.getDeserializerAs(EncodingConstants.JAX_RPC_RI_MECHANISM);
    }

}

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

package com.sun.xml.rpc.processor.generator.writer;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.processor.config.TypeMappingInfo;
import com.sun.xml.rpc.processor.generator.GeneratorConstants;
import com.sun.xml.rpc.processor.generator.GeneratorException;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.processor.model.AbstractType;
import com.sun.xml.rpc.processor.model.java.JavaCustomType;
import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
import com.sun.xml.rpc.processor.model.soap.SOAPType;
import com.sun.xml.rpc.processor.util.IndentingWriter;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class CustomSerializerWriter
    extends SerializerWriterBase
    implements GeneratorConstants {
    private String serializerName;
    private String serializerMemberName;
    private String deserializerName;
    private String deserializerMemberName;
    private SOAPType dataType;

    public CustomSerializerWriter(SOAPType type, Names names) {
        super(type, names);
        dataType = type;
        serializerName = names.getTypeQName(type.getName()) + "_Serializer";
        serializerMemberName = names.getClassMemberName(serializerName);
        deserializerName = names.getTypeQName(type.getName()) + "_Deserializer";
        deserializerMemberName = names.getClassMemberName(deserializerName);
    }

    public void createSerializer(
        IndentingWriter p,
        StringBuffer typeName,
        String varName,
        boolean encodeTypes,
        boolean multiRefEncoding,
        String typeMapping)
        throws IOException {
        throw new GeneratorException(
            "generator.nestedGeneratorError",
            new LocalizableExceptionAdapter(
                new OperationNotSupportedException()));
    }

    public void registerSerializer(
        IndentingWriter p,
        boolean encodeTypes,
        boolean multiRefEncoding,
        String typeMapping)
        throws IOException {
        TypeMappingInfo mappingInfo =
            ((JavaCustomType) type.getJavaType()).getTypeMappingInfo();
        if ((soapVer.equals(SOAPVersion.SOAP_11)
            && !mappingInfo.getEncodingStyle().equals(
                SOAPConstants.NS_SOAP_ENCODING))
            || (soapVer.equals(SOAPVersion.SOAP_12)
                && !mappingInfo.getEncodingStyle().equals(
                    SOAP12Constants.NS_SOAP_ENCODING))) {
            throw new GeneratorException(
                "generator.unsupported.encoding.encountered",
                mappingInfo.getEncodingStyle().toString());
        }
        String serFac = mappingInfo.getSerializerFactoryName();
        String deserFac = mappingInfo.getDeserializerFactoryName();
        StringBuffer typeName = new StringBuffer("type");
        declareType(p, typeName, type.getName(), false, false);
        p.pln(
            typeMapping
                + ".register("
                + type.getJavaType().getName()
                + ".class, "
                + typeName.toString()
                + ", "
                + "new "
                + serFac
                + "(), "
                + "new "
                + deserFac
                + "());");
    }

    public void declareSerializer(
        IndentingWriter p,
        boolean isStatic,
        boolean isFinal)
        throws IOException {
        String modifier = getPrivateModifier(isStatic, isFinal);
        p.pln("private JAXRPCSerializer " + serializerMemberName() + ";");
        p.pln("private JAXRPCDeserializer " + deserializerMemberName() + ";");
    }

    public void initializeSerializer(
        IndentingWriter p,
        String typeName,
        String registry)
        throws IOException {
        p.pln(
            serializerMemberName()
                + " = (JAXRPCSerializer)registry.getSerializer("
                + getEncodingStyleString()
                + ", "
                + type.getJavaType().getName()
                + ".class, "
                + typeName
                + ");");
        p.pln(
            deserializerMemberName()
                + " = (JAXRPCDeserializer)registry.getDeserializer("
                + getEncodingStyleString()
                + ", "
                + type.getJavaType().getName()
                + ".class, "
                + typeName
                + ");");
    }

    public String serializerName() {
        return serializerName;
    }

    public String serializerMemberName() {
        return getPrefix(dataType) + UNDERSCORE + serializerMemberName;
    }

    public String deserializerName() {
        return deserializerName;
    }

    public String deserializerMemberName() {
        return getPrefix(dataType) + UNDERSCORE + deserializerMemberName;
    }

    protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
        return "private " + super.getModifier(isStatic, isFinal);
    }

    public AbstractType getElementType() {
        SOAPType elemType = ((SOAPArrayType) type).getElementType();
        while (elemType instanceof SOAPArrayType) {
            elemType = ((SOAPArrayType) elemType).getElementType();
        }
        return elemType;
    }
}

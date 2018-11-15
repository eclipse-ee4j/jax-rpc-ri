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

import com.sun.xml.rpc.processor.generator.GeneratorConstants;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.util.IndentingWriter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class LiteralSequenceSerializerWriter
    extends LiteralSerializerWriterBase
    implements GeneratorConstants {
    protected String serializerName;
    protected String serializerMemberName;
    protected LiteralType dataType;

    public LiteralSequenceSerializerWriter(
        String basePackage,
        LiteralType type,
        Names names) {
        super(type, names);
        serializerName =
            names.typeObjectSerializerClassName(
                basePackage,
                (LiteralType) type);
        serializerMemberName = names.getClassMemberName(serializerName);
        dataType = type;
    }

    public void createSerializer(
        IndentingWriter p,
        StringBuffer typeName,
        String serName,
        boolean encodeTypes,
        boolean multiRefEncoding,
        String typeMapping)
        throws IOException {
        LiteralType type = (LiteralType) this.type;
        boolean isExtendable =
            ((LiteralStructuredType) type).getParentType() != null;
        String encodeType =
            (encodeTypes
                && isExtendable ? ENCODE_TYPE_STR : DONT_ENCODE_TYPE_STR);
        declareType(p, typeName, type.getName(), false, false);
        p.plnI(
            BASE_SERIALIZER_NAME
                + " "
                + serName
                + " = new "
                + serializerName()
                + "("
                + typeName
                + ", \"\", "
                + encodeType
                + ");");
        p.pO();
    }

    public void declareSerializer(
        IndentingWriter p,
        boolean isStatic,
        boolean isFinal)
        throws IOException {
        String modifier = getPrivateModifier(isStatic, isFinal);
        p.pln(
            modifier
                + BASE_SERIALIZER_NAME
                + " "
                + serializerMemberName()
                + ";");
    }

    public String serializerName() {
        return serializerName;
    }

    public String serializerMemberName() {
        return getPrefix(dataType) + UNDERSCORE + serializerMemberName;
    }

    public String deserializerName() {
        return serializerName;
    }

    public String deserializerMemberName() {
        return getPrefix(dataType) + UNDERSCORE + serializerMemberName;
    }

    protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
        return "private " + super.getModifier(isStatic, isFinal);
    }
}

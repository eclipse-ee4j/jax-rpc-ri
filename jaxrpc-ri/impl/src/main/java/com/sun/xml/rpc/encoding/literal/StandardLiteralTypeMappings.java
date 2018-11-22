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

package com.sun.xml.rpc.encoding.literal;

import java.util.Calendar;

import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.Deserializer;
import javax.xml.rpc.encoding.TypeMapping;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.InternalEncodingConstants;
import com.sun.xml.rpc.encoding.SerializerConstants;
import com.sun.xml.rpc.encoding.SingletonDeserializerFactory;
import com.sun.xml.rpc.encoding.SingletonSerializerFactory;
import com.sun.xml.rpc.encoding.TypeMappingImpl;
import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDBooleanEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDByteEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDDateEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeDateEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDDecimalEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDDoubleEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDFloatEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDHexBinaryEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDIntEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDIntegerEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDLongEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDNegativeIntegerEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDNonNegativeIntegerEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDNonPositiveIntegerEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDPositiveIntegerEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDQNameEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDShortEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDTimeEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedByteEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedIntEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedLongEncoder;
import com.sun.xml.rpc.encoding.simpletype.XSDUnsignedShortEncoder;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;

/**
 * An implementation of the standard TypeMapping interface for rpc literals
 *
 * @author JAX-RPC Development Team
 */
public class StandardLiteralTypeMappings
    extends TypeMappingImpl
    implements SerializerConstants, InternalEncodingConstants {

    public StandardLiteralTypeMappings() throws Exception {
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_BOOLEAN,
                    "",
                    XSDBooleanEncoder.getInstance());
            registerSerializer(
                boolean.class,
                SchemaConstants.QNAME_TYPE_BOOLEAN,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_BOOLEAN,
                    "",
                    XSDBooleanEncoder.getInstance());
            registerSerializer(
                Boolean.class,
                SchemaConstants.QNAME_TYPE_BOOLEAN,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_BYTE,
                    "",
                    XSDByteEncoder.getInstance());
            registerSerializer(
                byte.class,
                SchemaConstants.QNAME_TYPE_BYTE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_BYTE,
                    "",
                    XSDByteEncoder.getInstance());
            registerSerializer(
                Byte.class,
                SchemaConstants.QNAME_TYPE_BYTE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_BASE64_BINARY,
                    "",
                    XSDBase64BinaryEncoder.getInstance());
            registerSerializer(
                byte[].class,
                SchemaConstants.QNAME_TYPE_BASE64_BINARY,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_BASE64_BINARY,
                    "",
                    XSDBase64BinaryEncoder.getInstance());
            registerSerializer(
                Byte[].class,
                SchemaConstants.QNAME_TYPE_BASE64_BINARY,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_HEX_BINARY,
                    "",
                    XSDHexBinaryEncoder.getInstance());
            registerSerializer(
                byte[].class,
                SchemaConstants.QNAME_TYPE_HEX_BINARY,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_HEX_BINARY,
                    "",
                    XSDHexBinaryEncoder.getInstance());
            registerSerializer(
                Byte[].class,
                SchemaConstants.QNAME_TYPE_HEX_BINARY,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DOUBLE,
                    "",
                    XSDDoubleEncoder.getInstance());
            registerSerializer(
                double.class,
                SchemaConstants.QNAME_TYPE_DOUBLE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DOUBLE,
                    "",
                    XSDDoubleEncoder.getInstance());
            registerSerializer(
                Double.class,
                SchemaConstants.QNAME_TYPE_DOUBLE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DECIMAL,
                    "",
                    XSDDecimalEncoder.getInstance());
            registerSerializer(
                java.math.BigDecimal.class,
                SchemaConstants.QNAME_TYPE_DECIMAL,
                serializer);
        }
        {
            QName type = SchemaConstants.QNAME_TYPE_FLOAT;
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_FLOAT,
                    "",
                    XSDFloatEncoder.getInstance());
            registerSerializer(
                float.class,
                SchemaConstants.QNAME_TYPE_FLOAT,
                serializer);
        }
        {
            QName type = SchemaConstants.QNAME_TYPE_FLOAT;
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_FLOAT,
                    "",
                    XSDFloatEncoder.getInstance());
            registerSerializer(
                Float.class,
                SchemaConstants.QNAME_TYPE_FLOAT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_INT,
                    "",
                    XSDIntEncoder.getInstance());
            registerSerializer(
                int.class,
                SchemaConstants.QNAME_TYPE_INT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_INT,
                    "",
                    XSDIntEncoder.getInstance());
            registerSerializer(
                Integer.class,
                SchemaConstants.QNAME_TYPE_INT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_INTEGER,
                    "",
                    XSDIntegerEncoder.getInstance());
            registerSerializer(
                java.math.BigInteger.class,
                SchemaConstants.QNAME_TYPE_INTEGER,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_LONG,
                    "",
                    XSDLongEncoder.getInstance());
            registerSerializer(
                long.class,
                SchemaConstants.QNAME_TYPE_LONG,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_LONG,
                    "",
                    XSDLongEncoder.getInstance());
            registerSerializer(
                Long.class,
                SchemaConstants.QNAME_TYPE_LONG,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_SHORT,
                    "",
                    XSDShortEncoder.getInstance());
            registerSerializer(
                short.class,
                SchemaConstants.QNAME_TYPE_SHORT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_SHORT,
                    "",
                    XSDShortEncoder.getInstance());
            registerSerializer(
                Short.class,
                SchemaConstants.QNAME_TYPE_SHORT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_STRING,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_STRING,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DATE_TIME,
                    "",
                    XSDDateTimeCalendarEncoder.getInstance());
            registerSerializer(
                java.util.Calendar.class,
                SchemaConstants.QNAME_TYPE_DATE_TIME,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DATE_TIME,
                    "",
                    XSDDateTimeDateEncoder.getInstance());
            registerSerializer(
                java.util.Date.class,
                SchemaConstants.QNAME_TYPE_DATE_TIME,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DATE,
                    "",
                    XSDDateEncoder.getInstance());
            registerSerializer(
                java.util.Calendar.class,
                SchemaConstants.QNAME_TYPE_DATE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_QNAME,
                    "",
                    XSDQNameEncoder.getInstance());
            registerSerializer(
                javax.xml.namespace.QName.class,
                SchemaConstants.QNAME_TYPE_QNAME,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_LANGUAGE,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_LANGUAGE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NORMALIZED_STRING,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_NORMALIZED_STRING,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_TOKEN,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_TOKEN,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NMTOKEN,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_NMTOKEN,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NAME,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_NAME,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NCNAME,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_NCNAME,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_ID,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_ID,
                serializer);
        }
        //xsd:positiveInteger
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER,
                    "",
                    XSDPositiveIntegerEncoder.getInstance());
            registerSerializer(
                java.math.BigInteger.class,
                SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER,
                serializer);
        }

        //xsd:nonPositiveInteger
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER,
                    "",
                    XSDNonPositiveIntegerEncoder.getInstance());
            registerSerializer(
                java.math.BigInteger.class,
                SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER,
                serializer);
        }

        //xsd:negativeInteger
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER,
                    "",
                    XSDNegativeIntegerEncoder.getInstance());
            registerSerializer(
                java.math.BigInteger.class,
                SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER,
                serializer);
        }
        //xsd:nonNegativeInteger
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER,
                    "",
                    XSDNonNegativeIntegerEncoder.getInstance());
            registerSerializer(
                java.math.BigInteger.class,
                SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER,
                serializer);
        }
        //xsd:unsignedLong
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_LONG,
                    "",
                    XSDUnsignedLongEncoder.getInstance());
            registerSerializer(
                java.math.BigInteger.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_LONG,
                serializer);
        }
        //unsignedInt
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_INT,
                    "",
                    XSDUnsignedIntEncoder.getInstance());
            registerSerializer(
                long.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_INT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_INT,
                    "",
                    XSDUnsignedIntEncoder.getInstance());
            registerSerializer(
                Long.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_INT,
                serializer);
        }
        //unsignedShort
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT,
                    "",
                    XSDUnsignedShortEncoder.getInstance());
            registerSerializer(
                int.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT,
                    "",
                    XSDUnsignedShortEncoder.getInstance());
            registerSerializer(
                Integer.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT,
                serializer);
        }
        //unsignedByte
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE,
                    "",
                    XSDUnsignedByteEncoder.getInstance());
            registerSerializer(
                short.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE,
                serializer);
        }
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE,
                    "",
                    XSDUnsignedByteEncoder.getInstance());
            registerSerializer(
                Short.class,
                SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE,
                serializer);
        }
        //xsd:Duration
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_DURATION,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_DURATION,
                serializer);
        }
        //xsd:time
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_TIME,
                    "",
                    XSDTimeEncoder.getInstance());
            registerSerializer(
                Calendar.class,
                SchemaConstants.QNAME_TYPE_TIME,
                serializer);
        }
        //xsd:G_YEAR_MONTH
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_G_YEAR_MONTH,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_G_YEAR_MONTH,
                serializer);
        }
        //xsd:G_YEAR
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_G_YEAR,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_G_YEAR_MONTH,
                serializer);
        }
        //xsd:G_MONTH_DAY
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_G_MONTH_DAY,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_G_MONTH_DAY,
                serializer);
        }
        //xsd:G_DAY
        {
            QName type = SchemaConstants.QNAME_TYPE_G_DAY;
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_G_DAY,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_G_DAY,
                serializer);
        }
        //xsd:G_Month
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_G_MONTH,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_G_MONTH,
                serializer);
        }
        //xsd:AnyURI

        // 03/31/2003, vivekp, this is a problem if the client is generated
        // using jdk1.3 and is run with jdk1.4. With jdk1.4 the
        // registered serializer will be java.net.URI
        // (XSDAnyURIEncoder) which will fail to serialize jdk 1.3
        // mapping of String.  To solve this problem, Serializers for
        // xsd:anyURI will be generated at compile time. No run time
        // handling will be based on jdk version. So runtime will have
        // no serializers registerd for anyURI.

        /*
            if(!VersionUtil.isJavaVersionGreaterThan1_3()) {
                CombinedSerializer serializer = new LiteralSimpleTypeSerializer(
        								    SchemaConstants.QNAME_TYPE_ANY_URI,
        								    "", XSDStringEncoder.getInstance());
                registerSerializer(String.class,  
                                 SchemaConstants.QNAME_TYPE_ANY_URI, serializer);
            } else {
            CombinedSerializer serializer = new LiteralSimpleTypeSerializer(
        								    SchemaConstants.QNAME_TYPE_ANY_URI,
        								    "", XSDAnyURIEncoder.getInstance());
            registerSerializer(java.net.URI.class,  
        		       SchemaConstants.QNAME_TYPE_ANY_URI, serializer);
        }
        
        */

        //xsd:IDREF
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_IDREF,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_IDREF,
                serializer);
        }
        //xsd:IDREFS
        //bug fix:4863162
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_IDREFS,
                    "",
                    XSDListTypeEncoder.getInstance(
                        XSDStringEncoder.getInstance(),
                        String.class));
            registerSerializer(
                String[].class,
                SchemaConstants.QNAME_TYPE_IDREFS,
                serializer);
        }
        //xsd:NMTOKENS
        //bug fix:4863162
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_NMTOKENS,
                    "",
                    XSDListTypeEncoder.getInstance(
                        XSDStringEncoder.getInstance(),
                        String.class));
            registerSerializer(
                String[].class,
                SchemaConstants.QNAME_TYPE_NMTOKENS,
                serializer);
        }

        // bugfix: 4925400
        {
            CombinedSerializer serializer =
                new LiteralSimpleTypeSerializer(
                    SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE,
                    "",
                    XSDStringEncoder.getInstance());
            registerSerializer(
                String.class,
                SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE,
                serializer);
        }
    }

    private void registerSerializer(
        TypeMapping mapping,
        Class javaType,
        QName xmlType,
        CombinedSerializer ser)
        throws Exception {

        register(
            javaType,
            xmlType,
            new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer) ser));
    }

    private void registerSerializer(
        Class javaType,
        QName xmlType,
        CombinedSerializer ser)
        throws Exception {

        register(
            javaType,
            xmlType,
            new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer) ser));
    }
}

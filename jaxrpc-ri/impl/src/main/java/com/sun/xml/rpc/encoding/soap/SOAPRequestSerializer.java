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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.EncodingException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
import com.sun.xml.rpc.encoding.JAXRPCSerializer;
import com.sun.xml.rpc.encoding.ObjectSerializerBase;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPDeserializationState;
import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.encoding.SerializationException;
import com.sun.xml.rpc.encoding.SerializerBase;
import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 * A data-driven (de)serializer for a request structure.
 *
 * @author JAX-RPC Development Team
 */

public class SOAPRequestSerializer
    extends ObjectSerializerBase
    implements Initializable {
        
    protected QName[] parameterNames;
    protected QName[] parameterXmlTypes;
    protected Class[] parameterJavaTypes;

    protected JAXRPCSerializer[] serializers;
    protected JAXRPCDeserializer[] deserializers;

    protected InternalTypeMappingRegistry typeRegistry = null;

    private com.sun.xml.rpc.soap.SOAPEncodingConstants soapEncodingConstants =
        null;

    private void init(SOAPVersion ver) {
        this.soapEncodingConstants =
            SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public SOAPRequestSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName[] parameterNames,
        QName[] parameterTypes,
        Class[] parameterClasses) {
            
        this(
            type,
            encodeType,
            isNullable,
            encodingStyle,
            parameterNames,
            parameterTypes,
            parameterClasses,
            SOAPVersion.SOAP_11);
    }

    public SOAPRequestSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        QName[] parameterNames,
        QName[] parameterTypes,
        Class[] parameterClasses,
        SOAPVersion ver) {
            
        super(type, encodeType, isNullable, encodingStyle);
        init(ver);

        this.parameterNames = parameterNames;
        this.parameterXmlTypes = parameterTypes;
        this.parameterJavaTypes = parameterClasses;
    }

    public SOAPRequestSerializer(
        QName type,
        QName[] parameterNames,
        QName[] parameterTypes,
        Class[] parameterClasses) {
            
        this(
            type,
            parameterNames,
            parameterTypes,
            parameterClasses,
            SOAPVersion.SOAP_11);

    }

    public SOAPRequestSerializer(
        QName type,
        QName[] parameterNames,
        QName[] parameterTypes,
        Class[] parameterClasses,
        SOAPVersion ver) {
            
        this(
            type,
            DONT_ENCODE_TYPE,
            NULLABLE,
            getURIEncoding(ver),
            parameterNames,
            parameterTypes,
            parameterClasses);

    }

    private static String getURIEncoding(SOAPVersion ver) {
        if (ver == SOAPVersion.SOAP_11)
            return com.sun.xml.rpc.encoding.soap.SOAPConstants.URI_ENCODING;
        else if (ver == SOAPVersion.SOAP_11)
            return com.sun.xml.rpc.encoding.soap.SOAPConstants.URI_ENCODING;
        return null;
    }

    public void initialize(InternalTypeMappingRegistry registry)
        throws Exception {
            
        if (typeRegistry != null) {
            return;
        }

        serializers = new JAXRPCSerializer[parameterXmlTypes.length];
        deserializers = new JAXRPCDeserializer[parameterXmlTypes.length];

        for (int i = 0; i < parameterXmlTypes.length; ++i) {
            if (parameterXmlTypes[i] != null
                && parameterJavaTypes[i] != null) {
                serializers[i] =
                    (JAXRPCSerializer) registry.getSerializer(
                        encodingStyle,
                        parameterJavaTypes[i],
                        parameterXmlTypes[i]);
                deserializers[i] =
                    (JAXRPCDeserializer) registry.getDeserializer(
                        encodingStyle,
                        parameterJavaTypes[i],
                        parameterXmlTypes[i]);
            } else {
                serializers[i] = null;
                deserializers[i] = null;
            }
        }

        typeRegistry = registry;
    }

    protected void doSerializeInstance(
        Object instance,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {
            
        if (typeRegistry == null) {
            throw new EncodingException("initializable.not.initialized");
        }

        Object[] parameters = (Object[]) instance;

        checkParameterListLength(parameters);
        for (int i = 0; i < parameters.length; ++i) {
            Object parameter = parameters[i];
            getParameterSerializer(i, parameter).serialize(
                parameter,
                getParameterName(i),
                null,
                writer,
                context);
        }
    }

    protected Object doDeserialize(
        SOAPDeserializationState existingState,
        XMLReader reader,
        SOAPDeserializationContext context)
        throws Exception {
            
        if (typeRegistry == null) {
            throw new EncodingException("initializable.not.initialized");
        }

        Object[] instance = new Object[parameterXmlTypes.length];
        Object parameter;
        ParameterArrayBuilder builder = null;
        boolean isComplete = true;
        SOAPDeserializationState state = existingState;

        for (int i = 0; i < parameterXmlTypes.length; ++i) {
            reader.nextElementContent();
            QName parameterName = getParameterName(i);

            if (reader.getName().equals(parameterName)) {
                parameter =
                    getParameterDeserializer(i, reader).deserialize(
                        parameterName,
                        reader,
                        context);
                if (parameter instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new ParameterArrayBuilder(instance);
                    }
                    state =
                        registerWithMemberState(
                            instance,
                            state,
                            parameter,
                            i,
                            builder);
                    isComplete = false;
                } else {
                    instance[i] = parameter;
                }
            }
        }

        reader.nextElementContent();
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (Object) instance : (Object) state);
    }

    protected JAXRPCSerializer getParameterSerializer(
        int index,
        Object parameter)
        throws Exception {
            
        JAXRPCSerializer serializer = getSerializer(index);
        if (serializer == null) {
            Class parameterClass = null;
            if (parameter != null) {
                parameterClass = parameter.getClass();
            }
            serializer =
                (JAXRPCSerializer) typeRegistry.getSerializer(
                    encodingStyle,
                    parameterClass,
                    getParameterXmlType(index));
        }
        return serializer;
    }

    protected JAXRPCDeserializer getParameterDeserializer(
        int index,
        XMLReader reader)
        throws Exception {
            
        JAXRPCDeserializer deserializer = getDeserializer(index);
        if (deserializer == null) {
            QName parameterXmlType =
                parameterXmlTypes[index] != null
                    ? parameterXmlTypes[index]
                    : SerializerBase.getType(reader);
            deserializer =
                (JAXRPCDeserializer) typeRegistry.getDeserializer(
                    encodingStyle,
                    getParameterJavaType(index),
                    parameterXmlType);
        }
        return deserializer;
    }

    protected static class ParameterArrayBuilder
        implements SOAPInstanceBuilder {

        Object[] instance = null;

        ParameterArrayBuilder(Object[] instance) {
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
                instance[index] = memberValue;
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
            instance = (Object[]) instance;
        }

        public Object getInstance() {
            return instance;
        }
    }

    private Class getParameterJavaType(int index) {
        if (index < parameterJavaTypes.length) {
            return parameterJavaTypes[index];
        }
        return null;
    }
    
    private QName getParameterXmlType(int index) {
        if (index < parameterXmlTypes.length) {
            return parameterXmlTypes[index];
        }
        return null;
    }
    
    private QName getParameterName(int index) {
        if (index < parameterNames.length) {
            return parameterNames[index];
        }
        return null;
    }
    
    private JAXRPCDeserializer getDeserializer(int index) {
        if (index < deserializers.length) {
            return deserializers[index];
        }
        return null;
    }

    private JAXRPCSerializer getSerializer(int index) {
        if (index < serializers.length) {
            return serializers[index];
        }
        return null;
    }
    
    private void checkParameterListLength(Object[] parameters) {
        if (serializers.length > 0
            && parameters.length != serializers.length) {
            String expectedParameters = "\n";
            String actualParameters = "\n";

            for (int i = 0; i < parameterNames.length; i++) {
                QName name = parameterNames[i];
                QName xmlType = parameterXmlTypes[i];
                expectedParameters += name + ":" + xmlType;

                if (i + 1 != parameterNames.length) {
                    expectedParameters += "\n";
                }
            }
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String javaType =
                    parameter == null ? "null" : parameter.getClass().getName();
                actualParameters += javaType;

                if (i + 1 != parameters.length) {
                    actualParameters += "\n";
                }
            }

            throw new SerializationException(
                "request.parameter.count.incorrect",
                new Object[] {
                    new Integer(serializers.length),
                    new Integer(parameters.length),
                    expectedParameters,
                    actualParameters });
        }
    }
}

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

package com.sun.xml.rpc.util;

import java.lang.reflect.Constructor;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.encoding.TypeMapping;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.EncodingException;
import com.sun.xml.rpc.encoding.ObjectSerializerBase;
import com.sun.xml.rpc.encoding.ReferenceableSerializerImpl;
import com.sun.xml.rpc.encoding.SerializerConstants;
import com.sun.xml.rpc.encoding.SingletonDeserializerFactory;
import com.sun.xml.rpc.encoding.SingletonSerializerFactory;
import com.sun.xml.rpc.encoding.ValueTypeSerializer;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
* @author JAX-RPC Development Team
*/

public class TypeMappings implements SerializerConstants {
	public static void createMapping(
		Class serializerType,
		QName xmlType,
		Class javaType,
		TypeMapping mappings) {
		if (!ObjectSerializerBase.class.isAssignableFrom(serializerType)) {
			throw new IllegalArgumentException("serializerType must be a derivitive of com.sun.xml.rpc.encoding.ObjectSerializerBase");
		}

		try {
			Constructor serializerConstructor =
				serializerType.getConstructor(
					new Class[] {
						QName.class,
						boolean.class,
						boolean.class,
						String.class });

			CombinedSerializer serializer =
				(CombinedSerializer) serializerConstructor.newInstance(
					new Object[] {
						xmlType,
						new Boolean(ENCODE_TYPE),
						new Boolean(NULLABLE),
						SOAPConstants.URI_ENCODING });

			serializer =
				new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer);

			SingletonSerializerFactory serializerFactory =
				new SingletonSerializerFactory(serializer);
			SingletonDeserializerFactory deserializerFactory =
				new SingletonDeserializerFactory(serializer);

			mappings.register(
				javaType,
				xmlType,
				serializerFactory,
				deserializerFactory);
		} catch (Exception e) {
			throw new EncodingException(new LocalizableExceptionAdapter(e));
		}
	}

	public static void createMapping(
		Class serializerType,
		QName xmlType,
		Class javaType,
		Service service) {
		TypeMapping mappings =
			service.getTypeMappingRegistry().getTypeMapping(
				SOAPConstants.URI_ENCODING);
		createMapping(serializerType, xmlType, javaType, mappings);
	}

	public static void createValueTypeMapping(
		QName xmlType,
		Class javaType,
		Service service) {
		CombinedSerializer serializer =
			new ValueTypeSerializer(
				xmlType,
				ENCODE_TYPE,
				NULLABLE,
				SOAPConstants.URI_ENCODING,
				javaType);
		serializer =
			new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer);

		SingletonSerializerFactory serializerFactory =
			new SingletonSerializerFactory(serializer);
		SingletonDeserializerFactory deserializerFactory =
			new SingletonDeserializerFactory(serializer);

		TypeMapping mappings =
			service.getTypeMappingRegistry().getTypeMapping(
				SOAPConstants.URI_ENCODING);
		mappings.register(
			javaType,
			xmlType,
			serializerFactory,
			deserializerFactory);
	}
}

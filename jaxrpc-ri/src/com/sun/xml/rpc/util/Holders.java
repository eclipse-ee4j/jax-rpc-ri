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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.holders.Holder;

import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
* @author JAX-RPC Development Team
*/

public class Holders {
	private static final Map boxedTypes = new HashMap();

	static {
		boxedTypes.put(boolean.class, Boolean.class);
		boxedTypes.put(byte.class, Byte.class);
		boxedTypes.put(char.class, Character.class);
		boxedTypes.put(short.class, Short.class);
		boxedTypes.put(int.class, Integer.class);
		boxedTypes.put(long.class, Long.class);
		boxedTypes.put(float.class, Float.class);
		boxedTypes.put(double.class, Double.class);
	}

	public static Object getValue(Holder holder) {
		Class holderClass = holder.getClass();
		try {
			Field valueField = holderClass.getField("value");
			return valueField.get(holder);
		} catch (Exception e) {
			throw fieldExtractionException(e);
		}
	}

	public static void setValue(Holder holder, Object value) {
		Class holderClass = holder.getClass();
		try {
			Field valueField = holderClass.getField("value");
			valueField.set(holder, value);
		} catch (Exception e) {
			throw fieldExtractionException(e);
		}
	}

	public static Class stripHolderClass(Class aClass) {
		if (aClass == null || !Holder.class.isAssignableFrom(aClass)) {
			return aClass;
		}

		try {
			Field valueField = aClass.getField("value");
			Class valueClass = valueField.getType();

			return boxClassIfPrimitive(valueClass);
		} catch (Exception e) {
			throw fieldExtractionException(e);
		}
	}

	private static Class boxClassIfPrimitive(Class aClass) {
		Class boxedType = (Class) boxedTypes.get(aClass);

		if (boxedType != null) {
			return boxedType;
		} else {
			return aClass;
		}
	}

	private static HolderException fieldExtractionException(Exception e) {
		return new HolderException(
			"holder.valuefield.not.found",
			new LocalizableExceptionAdapter(e));
	}
}

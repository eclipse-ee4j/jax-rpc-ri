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

package com.sun.xml.rpc.processor.generator;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author JAX-RPC Development Team
 */
public final class SimpleToBoxedUtil {

    public static String getBoxedExpressionOfType(String s, String c) {
        if (isPrimitive(c)) {
            StringBuffer sb = new StringBuffer();
            sb.append("new ");
            sb.append(getBoxedClassName(c));
            sb.append('(');
            sb.append(s);
            sb.append(')');
            return sb.toString();
        } else
            return s;
    }

    public static String getUnboxedExpressionOfType(String s, String c) {
        if (isPrimitive(c)) {
            StringBuffer sb = new StringBuffer();
            sb.append('(');
            sb.append(s);
            sb.append(").");
            sb.append(c);
            sb.append("Value()");
            return sb.toString();
        } else
            return s;
    }

    public static String convertExpressionFromTypeToType(
        String s,
        String from,
        String to)
        throws Exception {
        if (from.equals(to))
            return s;
        else {
            if (!isPrimitive(to) && isPrimitive(from))
                return getBoxedExpressionOfType(s, from);
            else if (isPrimitive(to) && isPrimitive(from))
                return getUnboxedExpressionOfType(s, to);
            else
                return s;
        }
    }

    public static String getBoxedClassName(String className) {
        if (isPrimitive(className)) {
            StringBuffer sb = new StringBuffer();
            if (className.equals(int.class.getName()))
                sb.append("java.lang.Integer");
            else if (className.equals(char.class.getName()))
                sb.append("java.lang.Character");
            else {
                sb.append(Character.toUpperCase(className.charAt(0)));
                sb.append(className.substring(1));
            }
            return sb.toString();
        } else
            return className;
    }

    public static boolean isPrimitive(String className) {
        return primitiveSet.contains(className);
    }

    static Set primitiveSet = null;

    static {
        primitiveSet = new HashSet();
        primitiveSet.add("boolean");
        primitiveSet.add("byte");
        primitiveSet.add("double");
        primitiveSet.add("float");
        primitiveSet.add("int");
        primitiveSet.add("long");
        primitiveSet.add("short");
    }
}

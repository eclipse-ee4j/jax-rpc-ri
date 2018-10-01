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

package com.sun.xml.rpc.processor.modeler.rmi;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JAX-RPC Development Team
 */
public class RmiUtils implements RmiConstants {

    public static String getTypeSig(String typeName) {
        String sig = "";
        int idx;
        if ((idx = typeName.lastIndexOf(BRACKETS)) > 0) {
            return SIG_ARRAY + getTypeSig(typeName.substring(0, idx));
        }
        if (typeName.equals(BOOLEAN_CLASSNAME))
            return SIG_BOOLEAN;
        if (typeName.equals(BYTE_CLASSNAME))
            return SIG_BYTE;
        if (typeName.equals(CHAR_CLASSNAME))
            return SIG_CHAR;
        if (typeName.equals(SHORT_CLASSNAME))
            return SIG_SHORT;
        if (typeName.equals(INT_CLASSNAME))
            return SIG_INT;
        if (typeName.equals(LONG_CLASSNAME))
            return SIG_LONG;
        if (typeName.equals(FLOAT_CLASSNAME))
            return SIG_FLOAT;
        if (typeName.equals(DOUBLE_CLASSNAME))
            return SIG_DOUBLE;
        if (typeName.equals(VOID_CLASSNAME))
            return SIG_VOID;
        return SIG_CLASS + typeName.replace('.', SIGC_PACKAGE) + SIG_ENDCLASS;
    }

    public static String getRealName(String name, ClassLoader loader)
        throws ClassNotFoundException {
            
        String tmpName = name;
        if ((name.lastIndexOf(BRACKETS)) > 0)
            tmpName = getTypeSig(name).replace(SIGC_PACKAGE, '.');
        tmpName = getLoadableClassName(tmpName, loader);
        return tmpName;
    }

    public static Class getClassForName(String name, ClassLoader classLoader)
        throws ClassNotFoundException {
            
        Class tmpClass = (Class) typeClassMap.get(name);
        if (tmpClass != null)
            return tmpClass;
        String tmpName = name;
        if ((name.lastIndexOf(BRACKETS)) > 0)
            tmpName = getTypeSig(name).replace(SIGC_PACKAGE, '.');
        tmpName = getLoadableClassName(tmpName, classLoader);
        tmpClass = Class.forName(tmpName, true, classLoader);
        return tmpClass;
    }

    public static String getLoadableClassName(
        String className,
        ClassLoader classLoader)
        throws ClassNotFoundException {
            
        try {
            Class seiClass = Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            int idx = className.lastIndexOf(DOTC);
            if (idx > -1) {
                String tmp = className.substring(0, idx) + SIG_INNERCLASS;
                tmp += className.substring(idx + 1);
                return getLoadableClassName(tmp, classLoader);
            }
            throw e;
        }
        return className;
    }

    private static final Map typeClassMap = new HashMap();

    static {
        typeClassMap.put(BOOLEAN_CLASSNAME, boolean.class);
        typeClassMap.put(BYTE_CLASSNAME, byte.class);
        typeClassMap.put(CHAR_CLASSNAME, char.class);
        typeClassMap.put(DOUBLE_CLASSNAME, double.class);
        typeClassMap.put(FLOAT_CLASSNAME, float.class);
        typeClassMap.put(INT_CLASSNAME, int.class);
        typeClassMap.put(LONG_CLASSNAME, long.class);
        typeClassMap.put(SHORT_CLASSNAME, short.class);
        typeClassMap.put(VOID_CLASSNAME, void.class);
    }
}

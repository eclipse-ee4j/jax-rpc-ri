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

import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.model.java.JavaType;

/**
 * @author JAX-RPC Development Team
 *
 * JAXRPC 1.0.1 Names class
 */
public class Names101 extends Names {
    public String holderClassName(Port port, JavaType type) {
        if (type.getHolderName() != null)
            return type.getHolderName();
        return holderClassName(port, type.getName());
    }

    protected String holderClassName(Port port, String typeName) {
        String holderTypeName = (String) holderClassNames.get(typeName);
        if (holderTypeName == null) {
            // not a built-in holder class
            String className = port.getJavaInterface().getName();
            String packageName = getPackageName(className);
            if (packageName.length() > 0) {
                packageName += ".holders.";
            } else {
                packageName = "holders.";
            }
            //                if (!(typeName.startsWith("java.") || typeName.startsWith("javax."))) {
            if (!isInJavaOrJavaxPackage(typeName)) {
                typeName = stripQualifier(typeName);
            }
            int idx = typeName.indexOf(BRACKETS);
            while (idx > 0) {
                typeName =
                    typeName.substring(0, idx)
                        + ARRAY
                        + typeName.substring(idx + 2);
                idx = typeName.indexOf(BRACKETS);
            }
            holderTypeName = packageName + typeName + HOLDER_SUFFIX;
        }
        return holderTypeName;
    }

}

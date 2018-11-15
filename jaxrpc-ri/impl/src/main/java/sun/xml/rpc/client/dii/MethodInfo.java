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

package com.sun.xml.rpc.client.dii;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author JAX-RPC Development Team
 */

public class MethodInfo {
    private Method method = null;

    //hold actual method information extracted from
    //the inspected java interface
    //this information is used to do a sanity check
    //between the operation information obtained from
    //examining the wsdl and the java interface
    //information
    public MethodInfo() {
    }

    public MethodInfo(Method method) {
        this.method = method;
    }

    public int getModifiers() {
        if (method != null) {
            return method.getModifiers();
        }

        return Modifier.PRIVATE;
    }

    public String getName() {
        if (method != null) {
            return method.getName();
        }

        return "";
    }

    public Class[] getParameterTypes() {
        if (method != null) {
            return method.getParameterTypes();
        }

        return getParameterTypes(0);
    }

    public Class[] getParameterTypes(int parameterCount) {
        if (method != null) {
            return method.getParameterTypes();
        }

        return new Class[parameterCount];
    }

    public int getParameterCount() {
        if (method != null) {
            return method.getParameterTypes().length;
        }

        return -1;
    }

    public Class getReturnType() {
        if (method != null) {
            return method.getReturnType();
        }

        return null;
    }

    public Method getMethodObject() {
        return method;
    }

    public boolean matches(String methodName, OperationInfo operation) {
        if (!operation.getName().getLocalPart().equals(methodName)) {
            return false;
        }
        if (method != null) {
            if (getParameterCount() != operation.getParameterCount()) {
                return false;
            }
            // TODO: support overloading based on parameter types
        }
        return true;
    }
}

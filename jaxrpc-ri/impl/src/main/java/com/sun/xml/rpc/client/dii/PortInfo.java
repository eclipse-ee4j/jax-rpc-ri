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

/**
 * @author JAX-RPC Development Team
 */
package com.sun.xml.rpc.client.dii;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

public class PortInfo {
    Map operationMap;
    String targetEndpoint;
    String defaultNamespace;
    QName name;
    QName portTypeName;

    //stores port information from the examined
    //wsdl - stores a map of operations found in the
    //wsdl
    public PortInfo(QName name) {
        init();
        this.name = name;
    }

    protected void init() {
        operationMap = new HashMap();
        targetEndpoint = "";
        defaultNamespace = "";
    }

    public QName getName() {
        return name;
    }

    public OperationInfo createOperationForName(String operationName) {
        OperationInfo operation =
            (OperationInfo) operationMap.get(operationName);
        if (operation == null) {
            operation = new OperationInfo(operationName);
            operation.setNamespace(defaultNamespace);
            operationMap.put(operationName, operation);
        }
        return operation;
    }

    public void setPortTypeName(QName typeName) {
        portTypeName = typeName;
    }

    public QName getPortTypeName() {
        return portTypeName;
    }

    public void setDefaultNamespace(String namespace) {
        defaultNamespace = namespace;
    }

    public boolean isOperationKnown(String operationName) {
        return operationMap.get(operationName) != null;
    }

    public String getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(String target) {
        targetEndpoint = target;
    }

    public Iterator getOperations() {
        return operationMap.values().iterator();
    }

    public int getOperationCount() {
        return operationMap.values().size();
    }
}

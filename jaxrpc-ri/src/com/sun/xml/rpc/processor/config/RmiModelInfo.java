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

package com.sun.xml.rpc.processor.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.sun.xml.rpc.processor.modeler.Modeler;
import com.sun.xml.rpc.processor.modeler.rmi.RmiModeler;

/**
 *
 * @author JAX-RPC Development Team
 */
public class RmiModelInfo extends ModelInfo {

    public RmiModelInfo() {
        interfaces = new ArrayList();
    }

    public String getTargetNamespaceURI() {
        return targetNamespaceURI;
    }

    public void setTargetNamespaceURI(String s) {
        targetNamespaceURI = s;
    }

    public String getTypeNamespaceURI() {
        return typeNamespaceURI;
    }

    public void setTypeNamespaceURI(String s) {
        typeNamespaceURI = s;
    }

    public String getJavaPackageName() {
        return javaPackageName;
    }

    public void setJavaPackageName(String s) {
        javaPackageName = s;
    }

    public void add(RmiInterfaceInfo i) {
        interfaces.add(i);
        i.setParent(this); 
    }

    public Iterator getInterfaces() {
        return interfaces.iterator();
    }

    protected Modeler getModeler(Properties options) {
        return new RmiModeler(this, options);
    }

    private String targetNamespaceURI;
    private String typeNamespaceURI;
    private List interfaces;
    private String javaPackageName;
    private HandlerChainInfo clientHandlerChainInfo;
    private HandlerChainInfo serverHandlerChainInfo;
}

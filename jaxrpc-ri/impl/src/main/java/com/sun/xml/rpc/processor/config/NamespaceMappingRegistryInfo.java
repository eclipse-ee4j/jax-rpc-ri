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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public class NamespaceMappingRegistryInfo
    implements com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo {

    public NamespaceMappingRegistryInfo() {
        namespaceMap = new HashMap();
        javaPackageNameMap = new HashMap();
    }

    public void addMapping(com.sun.xml.rpc.spi.tools.NamespaceMappingInfo i) {
        addMapping((NamespaceMappingInfo)i);
    }
    
    public void addMapping(NamespaceMappingInfo i) {
        namespaceMap.put(i.getNamespaceURI(), i);
        javaPackageNameMap.put(i.getJavaPackageName(), i);
    }

    public NamespaceMappingInfo getNamespaceMappingInfo(QName xmlType) {
        NamespaceMappingInfo i =
            (NamespaceMappingInfo) namespaceMap.get(xmlType.getNamespaceURI());
        return i;
    }

    public NamespaceMappingInfo getNamespaceMappingInfo(
        String javaPackageName) {
            
        NamespaceMappingInfo i =
            (NamespaceMappingInfo) javaPackageNameMap.get(javaPackageName);
        return i;
    }

    public Iterator getNamespaceMappings() {
        return namespaceMap.values().iterator();
    }
    
    private Map namespaceMap;
    private Map javaPackageNameMap;
}

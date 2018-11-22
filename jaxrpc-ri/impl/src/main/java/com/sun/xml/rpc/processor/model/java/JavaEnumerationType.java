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

package com.sun.xml.rpc.processor.model.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author JAX-RPC Development Team
 */
public class JavaEnumerationType extends JavaType {
    
    public JavaEnumerationType() {}
    
    public JavaEnumerationType(String name, JavaType baseType,
        boolean present) {
            
        super(name, present, "null");
        this.baseType = baseType;
    }
    
    public JavaType getBaseType() {
        return baseType;
    }
    
    public void setBaseType(JavaType t) {
        baseType = t;
    }
    
    public void add(JavaEnumerationEntry e) {
        entries.add(e);
    }
    
    public Iterator getEntries() {
        return entries.iterator();
    }
    
    public int getEntriesCount() {
        return entries.size();
    }
    
    /* serialization */
    public List getEntriesList() {
        return entries;
    }
    
    /* serialization */
    public void setEntriesList(List l) {
        entries = l;
    }
    
    private List entries = new ArrayList();
    private JavaType baseType;
}

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

/**
 *
 * @author JAX-RPC Development Team
 */
public class JavaEnumerationEntry {
    
    public JavaEnumerationEntry() {}
    
    public JavaEnumerationEntry(String name, Object value,
        String literalValue) {
            
        this.name = name;
        this.value = value;
        this.literalValue = literalValue;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object obj) {
        value = obj;
    }
    
    public String getLiteralValue() {
        return literalValue;
    }
    
    public void setLiteralValue(String s) {
        literalValue = s;
    }
    
    private String name;
    private Object value;
    private String literalValue;
}

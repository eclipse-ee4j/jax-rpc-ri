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

import com.sun.xml.rpc.processor.model.Parameter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class JavaParameter {
    
    public JavaParameter() {}
    
    public JavaParameter(String name, JavaType type, Parameter parameter) {
        this(name, type, parameter, false);
    }
    
    public JavaParameter(String name, JavaType type, Parameter parameter,
        boolean holder) {
            
        this.name = name;
        this.type = type;
        this.parameter = parameter;
        this.holder = holder;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public JavaType getType() {
        return type;
    }
    
    public void setType(JavaType t) {
        type = t;
    }
    
    public Parameter getParameter() {
        return parameter;
    }
    
    public void setParameter(Parameter p) {
        parameter = p;
    }
    
    public boolean isHolder() {
        return holder;
    }
    
    public void setHolder(boolean b) {
        holder = b;
    }
    
    public String getHolderName() {
        return holderName;
    }
    
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    
    private String name;
    private JavaType type;
    private Parameter parameter;
    private boolean holder;
    private String holderName;
}

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

import com.sun.xml.rpc.processor.model.ModelException;

/**
 *
 * @author JAX-RPC Development Team
 */
public class JavaMethod {
    
    public JavaMethod() {}
    
    public JavaMethod(String name) {
        this.name = name;
        this.returnType = null;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public JavaType getReturnType() {
        return returnType;
    }
    
    public void setReturnType(JavaType returnType) {
        this.returnType = returnType;
    }
    
    public boolean hasParameter(String paramName) {
        for (int i=0; i<parameters.size();i++) {
            if (paramName.equals(
                ((JavaParameter)parameters.get(i)).getName())) {
                    
                return true;
            }
        }
        return false;
    }
    
    public void addParameter(JavaParameter param) {
        
        // verify that this member does not already exist
        if (hasParameter(param.getName())) {
            throw new ModelException("model.uniqueness");
        }
        parameters.add(param);
    }
    
    public Iterator getParameters() {
        return parameters.iterator();
    }
    
    public int getParameterCount() {
        return parameters.size();
    }
    
    /* serialization */
    public List getParametersList() {
        return parameters;
    }
    
    /* serialization */
    public void setParametersList(List l) {
        parameters = l;
    }
    
    public boolean hasException(String exception) {
        return exceptions.contains(exception);
    }
    
    public void addException(String exception) {
        
        // verify that this exception does not already exist
        if (hasException(exception)) {
            throw new ModelException("model.uniqueness");
        }
        exceptions.add(exception);
    }
    
    public Iterator getExceptions() {
        return exceptions.iterator();
    }
    
    /* serialization */
    public List getExceptionsList() {
        return exceptions;
    }
    
    /* serialization */
    public void setExceptionsList(List l) {
        exceptions = l;
    }
    
    public String getDeclaringClass() {
        return declaringClass;
    }
    public void setDeclaringClass(String declaringClass) {
        this.declaringClass = declaringClass;
    }
    
    private String name;
    private List parameters = new ArrayList();
    private List exceptions = new ArrayList();
    private JavaType returnType;
    private String declaringClass;
}

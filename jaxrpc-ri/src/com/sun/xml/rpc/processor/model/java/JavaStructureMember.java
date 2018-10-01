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
public class JavaStructureMember {
    
    public JavaStructureMember() {}
    
    public JavaStructureMember(String name, JavaType type, Object owner) {
        this(name, type, owner, false);
    }
    public JavaStructureMember(String name, JavaType type,
        Object owner, boolean isPublic) {
            
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.isPublic = isPublic;
        constructorPos = -1;
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
    
    public boolean isPublic() {
        return isPublic;
    }
    
    public void setPublic(boolean b) {
        isPublic = b;
    }
    
    public boolean isInherited() {
        return isInherited;
    }
    
    public void setInherited(boolean b) {
        isInherited = b;
    }
    
    public String getReadMethod() {
        return readMethod;
    }
    
    public void setReadMethod(String readMethod) {
        this.readMethod = readMethod;
    }
    
    public String getWriteMethod() {
        return writeMethod;
    }
    
    public void setWriteMethod(String writeMethod) {
        this.writeMethod = writeMethod;
    }
    
    public String getDeclaringClass() {
        return declaringClass;
    }
    public void setDeclaringClass(String declaringClass) {
        this.declaringClass = declaringClass;
    }
    
    public Object getOwner() {
        return owner;
    }
    
    public void setOwner(Object owner) {
        this.owner = owner;
    }
    
    public int getConstructorPos() {
        return constructorPos;
    }
    
    public void setConstructorPos(int idx) {
        constructorPos = idx;
    }
    
    private String name;
    private JavaType type;
    private boolean isPublic = false;
    private boolean isInherited = false;
    private String readMethod;
    private String writeMethod;
    private String declaringClass;
    private Object owner;
    private int constructorPos;
}

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

package com.sun.xml.rpc.processor.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaType;
import com.sun.xml.rpc.util.NullIterator;

/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class AbstractType {
    
    protected AbstractType() {}
    
    protected AbstractType(QName name) {
        this(name, null, null);
    }
    
    protected AbstractType(QName name, String version) {
        this(name, null, version);
    }
    
    protected AbstractType(QName name, JavaType javaType) {
        this(name, javaType, null);
    }
    
    protected AbstractType(QName name, JavaType javaType, String version) {
        this.name = name;
        this.javaType = javaType;
        this.version = version;
    }
    
    public QName getName() {
        return name;
    }
    
    public void setName(QName name) {
        this.name = name;
    }
    
    public JavaType getJavaType() {
        return javaType;
    }
    
    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public boolean isNillable() {
        return false;
    }
    
    public boolean isSOAPType() {
        return false;
    }
    
    public boolean isLiteralType() {
        return false;
    }
    
    public Object getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }
    
    public void setProperty(String key, Object value) {
        if (value == null) {
            removeProperty(key);
            return;
        }
        
        if (properties == null) {
            properties = new HashMap();
        }
        properties.put(key, value);
    }
    
    public void removeProperty(String key) {
        if (properties != null) {
            properties.remove(key);
        }
    }
    
    public Iterator getProperties() {
        if (properties == null) {
            return NullIterator.getInstance();
        } else {
            return properties.keySet().iterator();
        }
    }
    
    /* serialization */
    public Map getPropertiesMap() {
        return properties;
    }
    
    /* serialization */
    public void setPropertiesMap(Map m) {
        properties = m;
    }
    
    private QName name;
    private JavaType javaType;
    private String version = null;
    private Map properties;
}

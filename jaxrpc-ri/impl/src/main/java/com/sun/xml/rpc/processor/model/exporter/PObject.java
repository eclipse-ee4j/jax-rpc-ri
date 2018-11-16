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

package com.sun.xml.rpc.processor.model.exporter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.xml.rpc.util.NullIterator;

/**
 * @author JAX-RPC Development Team
 */
public class PObject {
    
    public PObject() {}
    
    public String getType() {
        return type;
    }
    
    public void setType(String s) {
        type = s;
    }
    
    public Object getProperty(String name) {
        if (properties == null) {
            return null;
        } else {
            return properties.get(name);
        }
    }
    
    public void setProperty(String name, Object value) {
        if (properties == null) {
            properties = new HashMap();
        }
        properties.put(name, value);
    }
    
    public Iterator getProperties() {
        if (properties == null) {
            return NullIterator.getInstance();
        } else {
            return properties.values().iterator();
        }
    }
    
    public Iterator getPropertyNames() {
        if (properties == null) {
            return NullIterator.getInstance();
        } else {
            return properties.keySet().iterator();
        }
    }
    
    private Map properties;
    private String type;
}


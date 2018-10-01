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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaInterface;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Service extends ModelObject
    implements com.sun.xml.rpc.spi.model.Service {

    public Service() {}
    
    public Service(QName name, JavaInterface javaInterface) {
        this.name = name;
        this.javaInterface = javaInterface;
    }

    public QName getName() {
        return name;
    }
    
    public void setName(QName n) {
        name = n;
    }

    public void addPort(Port port) {
        if (portsByName.containsKey(port.getName())) {
            throw new ModelException("model.uniqueness");
        }
        ports.add(port);
        portsByName.put(port.getName(), port);
    }

    public Iterator getPorts() {
        return ports.iterator();
    }

    public Port getPortByName(QName n) {
        if (portsByName.size() != ports.size()) {
            initializePortsByName();
        }
        return (Port) portsByName.get(n);
    }

    /* serialization */
    public List getPortsList() {
        return ports;
    }
    
    /* serialization */
    public void setPortsList(List m) {
        ports = m;
//        initializePortsByName();
    }
    
    private void initializePortsByName() {
        portsByName = new HashMap();
        if (ports != null) {
            for (Iterator iter = ports.iterator(); iter.hasNext();) {
                Port port = (Port) iter.next();
                if (port.getName() != null &&
                    portsByName.containsKey(port.getName())) {
                        
                    throw new ModelException("model.uniqueness");
                }
                portsByName.put(port.getName(), port);
            }
        }
    }
    
    public com.sun.xml.rpc.spi.model.JavaInterface getJavaIntf() {
        return getJavaInterface();
    }
    
    public JavaInterface getJavaInterface() {
        return javaInterface;
    }
    
    public void setJavaInterface(JavaInterface i) {
        javaInterface = i;
    }

    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    private QName name;
    private List ports = new ArrayList();
    private Map portsByName = new HashMap();
    private JavaInterface javaInterface;
}

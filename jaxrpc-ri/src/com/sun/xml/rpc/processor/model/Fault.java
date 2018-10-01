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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.generator.GeneratorUtil;
import com.sun.xml.rpc.processor.model.java.JavaException;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Fault extends ModelObject {
    
    public Fault() {}
    
    public Fault(String name) {
        this.name = name;
        parentFault = null;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public Block getBlock() {
        return block;
    }
    
    public void setBlock(Block b) {
        block = b;
    }
    
    public JavaException getJavaException() {
        return javaException;
    }
    
    public void setJavaException(JavaException e) {
        javaException = e;
    }
    
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    public Fault getParentFault() {
        return parentFault;
    }
    
    public void setParentFault(Fault parentFault) {
        if (this.parentFault != null &&
            parentFault != null &&
            !this.parentFault.equals(parentFault)) {
                
            throw new ModelException("model.parent.fault.already.set",
                new Object[] {
                    getName().toString(),
                    this.parentFault.getName().toString(),
                    parentFault.getName().toString()
            });
        }
        this.parentFault = parentFault;
    }
    
    public void addSubfault(Fault fault) {
        subfaults.add(fault);
        fault.setParentFault(this);
    }
    
    public Iterator getSubfaults() {
        if (subfaults.size() == 0) {
            return null;
        }
        return subfaults.iterator();
    }
    
    public Iterator getSortedSubfaults() {
        Set sortedFaults = new TreeSet(new GeneratorUtil.FaultComparator());
        sortedFaults.addAll(subfaults);
        return sortedFaults.iterator();
    }    
    
    /* serialization */
    public Set getSubfaultsSet() {
        return subfaults;
    }
    
    /* serialization */
    public void setSubfaultsSet(Set s) {
        subfaults = s;
    }
    
    public Iterator getAllFaults() {
        Set allFaults = getAllFaultsSet();
        if (allFaults.size() == 0) {
            return null;
        }
        return allFaults.iterator();
    }
    
    public Set getAllFaultsSet() {
        Set transSet = new HashSet();
        Iterator iter = subfaults.iterator();
        while (iter.hasNext()) {
            transSet.addAll(((Fault)iter.next()).getAllFaultsSet());
        }
        transSet.addAll(subfaults);
        return transSet;
    }
    
    public QName getElementName() {
        return elementName;
    }
    
    public void setElementName(QName elementName) {
        this.elementName = elementName;
    }

    public String getJavaMemberName() {
        return javaMemberName;
    }
    
    public void setJavaMemberName(String javaMemberName) {
        this.javaMemberName = javaMemberName;
    }

    
    private String name;
    private Block block;
    private JavaException javaException;
    private Fault parentFault;
    private Set subfaults = new HashSet();
    private QName elementName = null;
    private String javaMemberName = null;
}

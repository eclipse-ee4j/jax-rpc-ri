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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.xml.rpc.processor.model.ModelException;

/**
 *
 * @author JAX-RPC Development Team
 */
public class JavaStructureType extends JavaType {
    
    public JavaStructureType() {}
    
    public JavaStructureType(String name, boolean present, Object owner) {
        super(name, present, "null");
        this.owner = owner;
    }
    
    public void add(JavaStructureMember m) {
        if (membersByName.containsKey(m.getName())) {
            throw new ModelException("model.uniqueness.javastructuretype",
                new Object[] {m.getName(), getRealName()});
        }
        members.add(m);
        membersByName.put(m.getName(), m);
    }
    
    
    public JavaStructureMember getMemberByName(String name) {
        if (membersByName.size() != members.size()) {
            initializeMembersByName();
        }
        return (JavaStructureMember) membersByName.get(name);
    }
    
    public Iterator getMembers() {
        return members.iterator();
    }
    
    public int getMembersCount() {
        return members.size();
    }
    
    /* serialization */
    public List getMembersList() {
        return members;
    }
    
    /* serialization */
    public void setMembersList(List l) {
        members = l;
    }
    
    private void initializeMembersByName() {
        membersByName = new HashMap();
        if (members != null) {
            for (Iterator iter = members.iterator(); iter.hasNext();) {
                JavaStructureMember m = (JavaStructureMember) iter.next();
                if (m.getName() != null &&
                    membersByName.containsKey(m.getName())) {
                        
                    throw new ModelException("model.uniqueness");
                }
                membersByName.put(m.getName(), m);
            }
        }
    }
    
    public boolean isAbstract() {
        return isAbstract;
    }
    
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
    
    public JavaStructureType getSuperclass() {
        return superclass;
    }
    
    public void setSuperclass(JavaStructureType superclassType) {
        superclass = superclassType;
    }
    
    public void addSubclass(JavaStructureType subclassType) {
        subclasses.add(subclassType);
        subclassType.setSuperclass(this);
    }
    
    public Iterator getSubclasses() {
        if (subclasses == null || subclasses.size() == 0) {
            return null;
        }
        return subclasses.iterator();
    }
    
    public Set getSubclassesSet() {
        return subclasses;
    }
    
    /* serialization */
    public void setSubclassesSet(Set s) {
        subclasses = s;
        for (Iterator iter = s.iterator(); iter.hasNext();) {
            ((JavaStructureType) iter.next()).setSuperclass(this);
        }
    }
    
    public Iterator getAllSubclasses() {
        Set subs = getAllSubclassesSet();
        if (subs.size() == 0) {
            return null;
        }
        return subs.iterator();
    }
    
    public Set getAllSubclassesSet() {
        Set transitiveSet = new HashSet();
        Iterator subs = subclasses.iterator();
        while (subs.hasNext()) {
            transitiveSet.addAll(
                ((JavaStructureType)subs.next()).getAllSubclassesSet());
        }
        transitiveSet.addAll(subclasses);
        return transitiveSet;
    }
    
    public Object getOwner() {

        // usually a SOAPStructureType
        return owner;
    }
    
    public void setOwner(Object owner) {

        // usually a SOAPStructureType
        this.owner = owner;
    }
    
    private List members = new ArrayList();
    private Map membersByName = new HashMap();

    // known subclasses of this type
    private Set subclasses = new HashSet();
    private JavaStructureType superclass;

    // usually a SOAPStructureType
    private Object owner;
    private boolean isAbstract = false;
}

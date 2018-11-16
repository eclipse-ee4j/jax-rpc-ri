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

package com.sun.xml.rpc.processor.model.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaStructureType;
import com.sun.xml.rpc.processor.model.ModelException;
import com.sun.xml.rpc.soap.SOAPVersion;

/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class SOAPStructureType extends SOAPAttributeOwningType {
    
    protected SOAPStructureType() {}
    
    protected SOAPStructureType(QName name) {
        this(name, SOAPVersion.SOAP_11);
    }
    
    protected SOAPStructureType(QName name, SOAPVersion version) {
        super(name, null);
    }
    
    protected SOAPStructureType(QName name, JavaStructureType javaType) {
        super(name, javaType);
    }
    
    public void add(SOAPStructureMember m) {
        if (_membersByName.containsKey(m.getName())) {
            throw new ModelException("model.uniqueness");
        }
        _members.add(m);
        _membersByName.put(m.getName(), m);
    }
    
    public SOAPStructureMember getMemberByName(QName name) {
        if (_membersByName.size() != _members.size()) {
            initializeMembersByName();
        }
        return (SOAPStructureMember) _membersByName.get(name);
    }
    
    public Iterator getMembers() {
        return _members.iterator();
    }
    
    public int getMembersCount() {
        return _members.size();
    }
    
    /* serialization */
    public List getMembersList() {
        return _members;
    }
    
    /* serialization */
    public void setMembersList(List l) {
        _members = l;
    }
    
    private void initializeMembersByName() {
        _membersByName = new HashMap();
        if (_members != null) {
            for (Iterator iter = _members.iterator(); iter.hasNext();) {
                SOAPStructureMember m = (SOAPStructureMember) iter.next();
                if (m.getName() != null &&
                    _membersByName.containsKey(m.getName())) {
                        
                    throw new ModelException("model.uniqueness");
                }
                _membersByName.put(m.getName(), m);
            }
        }
    }
    
    public void addSubtype(SOAPStructureType type) {
        if (_subtypes == null) {
            _subtypes = new HashSet();
        }
        _subtypes.add(type);
        type.setParentType(this);
    }
    
    public Iterator getSubtypes() {
        if (_subtypes != null)
            return _subtypes.iterator();
        return null;
    }
    
    /* serialization */
    public Set getSubtypesSet() {
        return _subtypes;
    }
    
    /* serialization */
    public void setSubtypesSet(Set s) {
        _subtypes = s;
    }
    
    public void setParentType(SOAPStructureType parent) {
        if (_parentType != null &&
            parent != null &&
            !_parentType.equals(parent)) {
                
            throw new ModelException("model.parent.type.already.set",
                new Object[] {getName().toString(),
                    _parentType.getName().toString(),
                    parent.getName().toString()});
        }
        this._parentType = parent;
    }
    
    public SOAPStructureType getParentType() {
        return _parentType;
    }
    
    private List _members = new ArrayList();
    private Map _membersByName = new HashMap();
    private Set _subtypes = null;
    private SOAPStructureType _parentType = null;
}

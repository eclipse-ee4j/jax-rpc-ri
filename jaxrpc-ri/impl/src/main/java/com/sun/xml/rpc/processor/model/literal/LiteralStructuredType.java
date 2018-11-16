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

package com.sun.xml.rpc.processor.model.literal;

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

/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class LiteralStructuredType extends LiteralAttributeOwningType {
    
    protected LiteralStructuredType() {}
    
    protected LiteralStructuredType(QName name) {
        this(name, null);
    }
    
    protected LiteralStructuredType(QName name, JavaStructureType javaType) {
        super(name, javaType);
    }
    
    public void add(LiteralElementMember m) {
        if (_elementMembersByName.containsKey(m.getName())) {
            throw new ModelException("model.uniqueness");
        }
        _elementMembers.add(m);
        if (m.getName() != null) {
            _elementMembersByName.put(m.getName().getLocalPart(), m);
        }
    }
    
    public LiteralElementMember getElementMemberByName(String name) {
        if (_elementMembersByName.size() != _elementMembers.size()) {
            initializeElementMembersByName();
        }
        return (LiteralElementMember) _elementMembersByName.get(name);
    }
    
    public Iterator getElementMembers() {
        return _elementMembers.iterator();
    }
    
    public int getElementMembersCount() {
        return _elementMembers.size();
    }
    
    /* serialization */
    public List getElementMembersList() {
        return _elementMembers;
    }
    
    /* serialization */
    public void setElementMembersList(List l) {
        _elementMembers = l;
    }
    
    private void initializeElementMembersByName() {
        _elementMembersByName = new HashMap();
        if (_elementMembers != null) {
            for (Iterator iter = _elementMembers.iterator(); iter.hasNext();) {
                LiteralElementMember m = (LiteralElementMember) iter.next();
                if (m.getName() != null &&
                    _elementMembersByName.containsKey(m.getName())) {
                        
                    throw new ModelException("model.uniqueness");
                }
                if (m.getName() != null) {
                    _elementMembersByName.put(m.getName().getLocalPart(), m);
                }
            }
        }
    }
    
    public LiteralContentMember getContentMember() {
        return _contentMember;
    }
    
    public void setContentMember(LiteralContentMember t) {
        _contentMember = t;
    }
    
    public void addSubtype(LiteralStructuredType type) {
        if (_subtypes == null) {
            _subtypes = new HashSet();
        }
        _subtypes.add(type);
        type.setParentType(this);
    }
    
    public Iterator getSubtypes() {
        if (_subtypes != null) {
            return _subtypes.iterator();
        }
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
    
    public void setParentType(LiteralStructuredType parent) {
        if (_parentType != null &&
            parent != null &&
            !_parentType.equals(parent)) {
                
            throw new ModelException("model.parent.type.already.set",
                new Object[] { getName().toString(),
                    _parentType.getName().toString(),
                    parent.getName().toString()});
        }
        this._parentType = parent;
    }
    
    public LiteralStructuredType getParentType() {
        return _parentType;
    }
    
    public void setRpcWrapper(boolean rpcWrapper) {
        this._rpcWrapper = rpcWrapper;
    }
    
    public boolean isRpcWrapper() {
        return _rpcWrapper;
    }
   
    private List _elementMembers = new ArrayList();
    private Map _elementMembersByName = new HashMap();
    private LiteralContentMember _contentMember;
    private Set _subtypes = null;
    private LiteralStructuredType _parentType = null;
    private boolean _rpcWrapper = false;
    private boolean requestResponseStruct;
}

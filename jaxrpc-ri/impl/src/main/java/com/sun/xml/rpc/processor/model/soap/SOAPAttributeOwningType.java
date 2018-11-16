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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.ModelException;
import com.sun.xml.rpc.processor.model.java.JavaStructureType;

/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class SOAPAttributeOwningType extends SOAPType {
    
    protected SOAPAttributeOwningType() {}
    
    protected SOAPAttributeOwningType(QName name) {
        this(name, null);
    }
    
    protected SOAPAttributeOwningType(QName name, JavaStructureType javaType) {
        super(name, javaType);
    }
    
    public void add(SOAPAttributeMember m) {
        if (_attributeMembersByName.containsKey(m.getName())) {
            throw new ModelException("model.uniqueness");
        }
        _attributeMembers.add(m);
        _attributeMembersByName.put(m.getName(), m);
    }
    
    public SOAPAttributeMember getAttributeMemberByName(String name) {
        if (_attributeMembersByName.size() != _attributeMembers.size()) {
            initializeAttributeMembersByName();
        }
        return (SOAPAttributeMember) _attributeMembersByName.get(name);
    }
    
    public Iterator getAttributeMembers() {
        return _attributeMembers.iterator();
    }
    
    public int getAttributeMembersCount() {
        return _attributeMembers.size();
    }
    
    /* serialization */
    public List getAttributeMembersList() {
        return _attributeMembers;
    }
    
    /* serialization */
    public void setAttributeMembersList(List l) {
        _attributeMembers = l;
    }
    
    private void initializeAttributeMembersByName() {
        _attributeMembersByName = new HashMap();
        if (_attributeMembers != null) {
            for (Iterator iter = _attributeMembers.iterator();
                iter.hasNext();) {
                    
                SOAPAttributeMember m = (SOAPAttributeMember) iter.next();
                if (m.getName() != null &&
                    _attributeMembersByName.containsKey(m.getName())) {
                        
                    throw new ModelException("model.uniqueness");
                }
                _attributeMembersByName.put(m.getName(), m);
            }
        }
    }
    
    private List _attributeMembers = new ArrayList();
    private Map _attributeMembersByName = new HashMap();
}

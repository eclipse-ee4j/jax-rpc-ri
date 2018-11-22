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

import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaArrayType;
import com.sun.xml.rpc.processor.model.java.JavaStructureType;
import com.sun.xml.rpc.processor.model.ModelException;

/**
 *
 * @author JAX-RPC Development Team
 */
public class LiteralArrayWrapperType extends LiteralSequenceType {
    
    public LiteralArrayWrapperType() {}
    
    public LiteralArrayWrapperType(QName name) {
        super(name, null);
    }
    
    public LiteralArrayWrapperType(QName name, JavaStructureType javaType) {
        super(name, javaType);
    }
    
    public void add(LiteralElementMember m) {
        super.add(m);
        if (elementMember != null) {
            throw new ModelException("model.arraywrapper.member.already.set");
        }
        elementMember = m;
    }
    
    public void setElementMembersList(List l) {
        if (l.size() >1) {
            throw new ModelException("model.arraywrapper.only.one.member");
        }
        super.setElementMembersList(l);
        elementMember = (LiteralElementMember)l.get(0);
    }
    
    public void addSubtype(LiteralStructuredType type) {
        throw new ModelException("model.arraywrapper.no.subtypes");
    }
    
    public void setSubtypesSet(Set s) {
        if (s != null && s.size() > 0) {
            throw new ModelException("model.arraywrapper.no.subtypes");
        }
        super.setSubtypesSet(s);
    }
    
    public void setParentType(LiteralStructuredType parent) {
        if (parent != null) {
            throw new ModelException("model.arraywrapper.no.parent");
        }
        super.setParentType(parent);
    }
    
    public void setContentMember(LiteralContentMember t) {
        if (t != null) {
            throw new ModelException("model.arraywrapper.no.content.member");
        }
        super.setContentMember(t);
    }
    
    public LiteralElementMember getElementMember() {
        return elementMember;
    }
    
    public JavaArrayType getJavaArrayType() {
        return javaArrayType;
    }
    
    public void setJavaArrayType(JavaArrayType javaArrayType) {
        this.javaArrayType = javaArrayType;
    }
    
    public void accept(LiteralTypeVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private LiteralElementMember elementMember = null;
    private JavaArrayType javaArrayType = null;
}

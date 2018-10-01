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

package com.sun.xml.rpc.processor.schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ComplexTypeDefinitionComponent extends TypeDefinitionComponent {
    
    public static final int CONTENT_EMPTY = 1;
    public static final int CONTENT_SIMPLE = 2;
    public static final int CONTENT_MIXED = 3;
    public static final int CONTENT_ELEMENT_ONLY = 4;
    
    public ComplexTypeDefinitionComponent() {
        _attributeUses = new ArrayList();
    }
    
    public boolean isComplex() {
        return true;
    }
    
    public TypeDefinitionComponent getBaseTypeDefinition() {
        return _baseTypeDefinition;
    }
    
    public void setBaseTypeDefinition(TypeDefinitionComponent c) {
        _baseTypeDefinition = c;
    }
    
    public Symbol getDerivationMethod() {
        return _derivationMethod;
    }
    
    public void setDerivationMethod(Symbol s) {
        _derivationMethod = s;
    }
    
    public void setProhibitedSubstitutions(Set s) {
        _prohibitedSubstitutions = s;
    }
    
    public void setFinal(Set s) {
        _final = s;
    }
    
    public boolean isAbstract() {
        return _abstract;
    }
    
    public void setAbstract(boolean b) {
        _abstract = b;
    }
    
    public Iterator attributeUses() {
        return _attributeUses.iterator();
    }
    
    public boolean hasNoAttributeUses() {
        return _attributeUses.size() == 0;
    }
    
    public void addAttributeUse(AttributeUseComponent c) {
        _attributeUses.add(c);
    }
    
    public void addAttributeGroup(AttributeGroupDefinitionComponent c) {
        for (Iterator iter = c.attributeUses(); iter.hasNext();) {
            AttributeUseComponent a = (AttributeUseComponent) iter.next();
            addAttributeUse(a);
        }
    }
    
    public int getContentTag() {
        return _contentTag;
    }
    
    public void setContentTag(int i) {
        _contentTag = i;
    }
    
    public SimpleTypeDefinitionComponent getSimpleTypeContent() {
        return _simpleTypeContent;
    }
    
    public void setSimpleTypeContent(SimpleTypeDefinitionComponent c) {
        _simpleTypeContent = c;
    }
    
    public ParticleComponent getParticleContent() {
        return _particleContent;
    }
    
    public void setParticleContent(ParticleComponent c) {
        _particleContent = c;
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private TypeDefinitionComponent _baseTypeDefinition;
    private Symbol _derivationMethod;
    private Set _final;
    private boolean _abstract;
    private List _attributeUses;
    private WildcardComponent _attributeWildcard;
    private int _contentTag;
    private SimpleTypeDefinitionComponent _simpleTypeContent;
    private ParticleComponent _particleContent;
    private Set _prohibitedSubstitutions;
    private List _annotations;
}

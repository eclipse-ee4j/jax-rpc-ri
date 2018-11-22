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

import com.sun.xml.rpc.util.NullIterator;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SimpleTypeDefinitionComponent extends TypeDefinitionComponent {
    
    public static final int VARIETY_ATOMIC = 1;
    public static final int VARIETY_LIST = 2;
    public static final int VARIETY_UNION = 3;
    
    public SimpleTypeDefinitionComponent() {}
    
    public boolean isSimple() {
        return true;
    }
    
    public SimpleTypeDefinitionComponent getBaseTypeDefinition() {
        return _baseTypeDefinition;
    }
    
    public void setBaseTypeDefinition(SimpleTypeDefinitionComponent c) {
        _baseTypeDefinition = c;
    }
    
    public SimpleTypeDefinitionComponent getPrimitiveTypeDefinition() {
        return _primitiveTypeDefinition;
    }
    
    public void setPrimitiveTypeDefinition(SimpleTypeDefinitionComponent c) {
        _primitiveTypeDefinition= c;
    }
    
    public SimpleTypeDefinitionComponent getItemTypeDefinition() {
        return _itemTypeDefinition;
    }
    
    public void setItemTypeDefinition(SimpleTypeDefinitionComponent c) {
        _itemTypeDefinition = c;
    }
    
    public void setFinal(Set s) {
        _final = s;
    }
    
    public int getVarietyTag() {
        return _varietyTag;
    }
    
    public void setVarietyTag(int i) {
        _varietyTag = i;
    }
    
    public void addFacet(Facet f) {
        if (_facets == null) {
            _facets = new ArrayList();
        }
        
        _facets.add(f);
    }
    
    public Iterator facets() {
        return _facets == null ?
            NullIterator.getInstance() : _facets.iterator();
    }
    
    public void addFundamentalFacet(FundamentalFacet f) {
        if (_fundamentalFacets == null) {
            _fundamentalFacets = new ArrayList();
        }
        
        _fundamentalFacets.add(f);
    }
    
    public Iterator fundamentalFacets() {
        return _fundamentalFacets == null ?
            NullIterator.getInstance() : _fundamentalFacets.iterator();
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private SimpleTypeDefinitionComponent _baseTypeDefinition;
    private List _facets;
    private List _fundamentalFacets;
    private Set _final;
    private int _varietyTag;
    private SimpleTypeDefinitionComponent _primitiveTypeDefinition;
    private SimpleTypeDefinitionComponent _itemTypeDefinition;
    private List _memberTypeDefinitions;
    private AnnotationComponent _annotation;
}

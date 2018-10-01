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

import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ElementDeclarationComponent extends Component {
    
    public ElementDeclarationComponent() {}
    
    public QName getName() {
        return _name;
    }
    
    public void setName(QName n) {
        _name = n;
    }
    
    public TypeDefinitionComponent getTypeDefinition() {
        return _typeDefinition;
    }
    
    public void setTypeDefinition(TypeDefinitionComponent c) {
        _typeDefinition = c;
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    public ComplexTypeDefinitionComponent getScope() {
        return _scope;
    }
    
    public void setScope(ComplexTypeDefinitionComponent c) {
        _scope = c;
    }
    
    public boolean isNillable() {
        return _nillable;
    }
    
    public void setNillable(boolean b) {
        _nillable = b;
    }
    
    public void setValue(String s) {
        _value = s;
    }
    
    public void setValueKind(Symbol s) {
        _valueKind = s;
    }
    
    public void setDisallowedSubstitutions(Set s) {
        _disallowedSubstitutions = s;
    }
    
    public void setSubstitutionsGroupExclusions(Set s) {
        _substitutionGroupExclusions = s;
    }
    
    public void setAbstract(boolean b) {
        _abstract = b;
    }
    
    private QName _name;
    private TypeDefinitionComponent _typeDefinition;
    private ComplexTypeDefinitionComponent _scope;
    private String _value;
    private Symbol _valueKind;
    private boolean _nillable;
    private List _identityConstraintDefinitions;
    private ElementDeclarationComponent _substitutionGroupAffiliation;
    private Set _substitutionGroupExclusions;
    private Set _disallowedSubstitutions;
    private boolean _abstract;
    private AnnotationComponent _annotation;
}

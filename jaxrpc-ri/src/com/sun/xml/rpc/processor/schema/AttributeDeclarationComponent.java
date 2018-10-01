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

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public class AttributeDeclarationComponent extends Component {
    
    public AttributeDeclarationComponent() {}
    
    public QName getName() {
        return _name;
    }
    
    public void setName(QName name) {
        _name = name;
    }
    
    public SimpleTypeDefinitionComponent getTypeDefinition() {
        return _typeDefinition;
    }
    
    public void setTypeDefinition(SimpleTypeDefinitionComponent c) {
        _typeDefinition = c;
    }
    
    public ComplexTypeDefinitionComponent getScope() {
        return _scope;
    }
    
    public void setScope(ComplexTypeDefinitionComponent c) {
        _scope = c;
    }
    
    public void setValue(String s) {
        _value = s;
    }
    
    // bug fix: 4968046
    public String getValue() {
        return _value;
    }

    public void setValueKind(Symbol s) {
        _valueKind = s;
    }
    
    // bug fix: 4968046
    public Symbol getValueKind() {
        return _valueKind;
    }

    
    public AnnotationComponent getAnnotation() {
        return _annotation;
    }
    
    public void setAnnotation(AnnotationComponent c) {
        _annotation = c;
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private QName _name;
    private SimpleTypeDefinitionComponent _typeDefinition;
    private ComplexTypeDefinitionComponent _scope;
    private String _value;
    private Symbol _valueKind;
    private AnnotationComponent _annotation;
    
}

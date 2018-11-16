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

/**
 *
 * @author JAX-RPC Development Team
 */
public class AttributeUseComponent extends Component {
    
    public AttributeUseComponent() {}
    
    public boolean isRequired() {
        return _required;
    }
    
    public void setRequired(boolean b) {
        _required = b;
    }
    
    public AttributeDeclarationComponent getAttributeDeclaration() {
        return _attributeDeclaration;
    }
    
    public void setAttributeDeclaration(AttributeDeclarationComponent c) {
        _attributeDeclaration = c;
    }
    
    public void setValue(String s) {
        _value = s;
    }
    
    public void setValueKind(Symbol s) {
        _valueKind = s;
    }
    
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
    
    private boolean _required;
    private AttributeDeclarationComponent _attributeDeclaration;
    private String _value;
    private Symbol _valueKind;
    private AnnotationComponent _annotation;
    
    /*
    NOTE - According to the XSD spec, an annotation doesn't belong here,
           yet the WSDL spec relies on being able to specify non-schema
           attributes in cases such as this:
     
      <s:complexType name="ArrayOfItemsItem">
        <s:complexContent mixed="false">
          <s:restriction base="soapenc:Array">
            <s:attribute n1:arrayType="s0:ItemsItem[]"
                ref="soapenc:arrayType"
                xmlns:n1="http://schemas.xmlsoap.org/wsdl/" />
          </s:restriction>
        </s:complexContent>
      </s:complexType>
     
           In this case, given that there is a "ref" attribute, the attribute
           declaration schema component should really be the corresponding
           top-level component, but then adding an annotation with the
           "n1:arrayType" attribute to it would be visible outside this
           complexType!
     */
}

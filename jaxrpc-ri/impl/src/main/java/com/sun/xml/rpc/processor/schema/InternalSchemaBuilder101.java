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

import java.util.Iterator;
import java.util.Properties;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
import com.sun.xml.rpc.wsdl.parser.Constants;

/**
 * @author JAX-RPC Development Team
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InternalSchemaBuilder101 extends InternalSchemaBuilderBase {
    
    /**
     * @param document
     * @param options
     */
    public InternalSchemaBuilder101(AbstractDocument document,
        Properties options) {
            
        super(document, options);
    }
    
    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.schema.InternalSchemaBuilderBase#processElementParticle(com.sun.xml.rpc.wsdl.document.schema.SchemaElement, com.sun.xml.rpc.processor.schema.ParticleComponent, com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent, com.sun.xml.rpc.processor.schema.InternalSchema)
     */
    protected void processElementParticle(
        SchemaElement element,
        ParticleComponent component,
        ComplexTypeDefinitionComponent scope,
        InternalSchema schema) {
            
        // SPEC - 3.9
        component.setTermTag(ParticleComponent.TERM_ELEMENT);
        
        // property: term
        ElementDeclarationComponent term =
            new ElementDeclarationComponent();

        internalBuildElementDeclaration(term, element, schema);

        String refAttr = element.getValueOfAttributeOrNull(Constants.ATTR_REF);
        if (refAttr != null) {
            
            // cannot deal with element refs yet
            failUnimplemented("F004");
        }
        
        // property: name, target namespace
        String nameAttr =
            element.getValueOfMandatoryAttribute(Constants.ATTR_NAME);
        String formAttr =
            element.getValueOfAttributeOrNull(Constants.ATTR_FORM);
        if (formAttr == null) {
            formAttr = element.getRoot().getValueOfAttributeOrNull(
                Constants.ATTR_ELEMENT_FORM_DEFAULT);
            if (formAttr == null) {
                formAttr = "";
            }
        }
        if (formAttr.equals(Constants.ATTRVALUE_QUALIFIED)) {
            term.setName(new QName(
                element.getSchema().getTargetNamespaceURI(), nameAttr));
        } else {
            term.setName(new QName(nameAttr));
        }
        
        // property: scope
        term.setScope(scope);
        
        component.setTermTag(ParticleComponent.TERM_ELEMENT);
        component.setElementTerm(term);
    }
    
    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.schema.InternalSchemaBuilderBase#buildRestrictionSimpleTypeDefinition(com.sun.xml.rpc.processor.schema.SimpleTypeDefinitionComponent, com.sun.xml.rpc.wsdl.document.schema.SchemaElement, com.sun.xml.rpc.processor.schema.InternalSchema)
     */
    protected void buildRestrictionSimpleTypeDefinition(
        SimpleTypeDefinitionComponent component,
        SchemaElement element,
        InternalSchema schema) {
            
        // property: base type definition
        String baseAttr =
            element.getValueOfAttributeOrNull(Constants.ATTR_BASE);
        if (baseAttr != null) {
            TypeDefinitionComponent base =
                schema.findTypeDefinition(element.asQName(baseAttr));
            if (base.isSimple()) {
                component.setBaseTypeDefinition(
                    (SimpleTypeDefinitionComponent)base);
            } else {
                failValidation("validation.notSimpleType",
                    base.getName().getLocalPart());
            }
        } else {
            failUnimplemented("F012");
        }
        
        // property: variety
        component.setVarietyTag(
            component.getBaseTypeDefinition().getVarietyTag());
        
        // property: primitive type definition
        component.setPrimitiveTypeDefinition(
            component.getBaseTypeDefinition().getPrimitiveTypeDefinition());
        
        // property: final
        String finalAttr =
            element.getValueOfAttributeOrNull(Constants.ATTR_FINAL);
        if (finalAttr == null) {
            finalAttr = element.getRoot().getValueOfAttributeOrNull(
                Constants.ATTR_FINAL_DEFAULT);
            if (finalAttr == null) {
                finalAttr = "";
            }
        }
        if (finalAttr.equals("")) {
            
            // no disallowed substitutions
            component.setFinal(_setEmpty);
        } else if (finalAttr.equals(Constants.ATTRVALUE_ALL)) {
            component.setFinal(_setExtResListUnion);
        } else {
            component.setFinal(parseSymbolSet(finalAttr, _setExtResListUnion));
            
            // TODO - implement this
            failUnimplemented("F013");
        }
        
        // right now, we only support the enumeration facet
        boolean gotOne = false;
        EnumerationFacet enumeration = new EnumerationFacet();
        for (Iterator iter = element.children(); iter.hasNext();) {
            SchemaElement child = (SchemaElement)iter.next();
            gotOne = true;
            if (child.getQName().equals(SchemaConstants.QNAME_ENUMERATION)) {
                String valueAttr =
                    child.getValueOfAttributeOrNull(Constants.ATTR_VALUE);
                if (valueAttr == null) {
                    failValidation("validation.missingRequiredAttribute",
                        Constants.ATTR_VALUE, child.getQName().getLocalPart());
                }
                enumeration.addValue(valueAttr);
            } else {
                failUnimplemented("F014");
            }
        }
        
        component.addFacet(enumeration);
    }
    
}

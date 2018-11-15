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

package com.sun.xml.rpc.processor.modeler.j2ee;

import java.util.Properties;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.J2EEModelInfo;
import com.sun.xml.rpc.processor.model.java.JavaStructureType;
import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
import com.sun.xml.rpc.processor.model.soap.SOAPType;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer111;
import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
import com.sun.xml.rpc.wsdl.framework.AbstractDocument;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class J2EESchemaAnalyzer111 extends SchemaAnalyzer111
    implements J2EESchemaAnalyzerIf {

    private JavaSimpleTypeCreator javaSimpleTypeCreator;
    private J2EEModelInfo _j2eeModelInfo;
    private J2EESchemaAnalyzerHelper helper;

    public J2EESchemaAnalyzer111(
        AbstractDocument document,
        J2EEModelInfo modelInfo,
        Properties options,
        Set conflictingClassNames,
        JavaSimpleTypeCreator javaTypes) {
        super(document, modelInfo, options, conflictingClassNames, javaTypes);
        _j2eeModelInfo = (J2EEModelInfo) modelInfo;
        javaSimpleTypeCreator = new JavaSimpleTypeCreator();
        helper = new J2EESchemaAnalyzerHelper(this, modelInfo, _env, javaTypes);
    }

    protected String getJavaNameOfType(
        TypeDefinitionComponent component,
        QName nameHint) {

        return helper.getJavaNameOfType(component, nameHint);            
    }



    // Sets abstract if the java type is abstract or interface
    protected void updateModifiers(JavaStructureType javaStructureType) {

        helper.updateModifiers(javaStructureType);      

    }

    protected String getJavaNameOfSOAPStructureType(
        SOAPStructureType structureType,
        TypeDefinitionComponent component,
        QName nameHint) {
            
        return helper.getJavaNameOfSOAPStructureType(structureType, component,
            nameHint);
    }

    protected SchemaJavaMemberInfo getJavaMemberInfo(
        TypeDefinitionComponent component,
        ElementDeclarationComponent element) {

        return helper.getJavaMemberInfo(component, element);
    }

    protected String getJavaNameOfElementType(
        LiteralStructuredType structureType,
        TypeDefinitionComponent component,
        QName nameHint) {
            
        return helper.getJavaNameOfElementType(structureType, component,
            nameHint);          
    }

    protected SchemaJavaMemberInfo getJavaMemberOfElementInfo(
        QName typeName,
        String memberName) {

        return helper.getJavaMemberOfElementInfo(typeName, memberName);
    }

    protected SOAPType getSOAPMemberType(
        ComplexTypeDefinitionComponent component,
        SOAPStructureType structureType,
        ElementDeclarationComponent element,
        QName nameHint,
        boolean occursZeroOrOne) {

        return helper.getSOAPMemberType(component, structureType, element,
            nameHint, occursZeroOrOne);          
    }

    protected LiteralType getLiteralMemberType(
        ComplexTypeDefinitionComponent component,
        LiteralType memberType,
        ElementDeclarationComponent element,
        LiteralStructuredType structureType) {

        return helper.getLiteralMemberType(component,  memberType, element,
            structureType);
    }

    public SOAPType getSuperSOAPMemberType(
        ComplexTypeDefinitionComponent component,
        SOAPStructureType structureType,
        ElementDeclarationComponent element,
        QName nameHint,
        boolean occursZeroOrOne) {

        return super.getSOAPMemberType(component, structureType, element,
            nameHint, occursZeroOrOne);
    }

    public SchemaJavaMemberInfo getSuperJavaMemberInfo(
        TypeDefinitionComponent component,
        ElementDeclarationComponent element) {
        return super.getJavaMemberInfo(component, element);
    }

}

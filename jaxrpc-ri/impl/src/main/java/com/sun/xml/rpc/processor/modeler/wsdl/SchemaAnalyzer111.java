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

package com.sun.xml.rpc.processor.modeler.wsdl;

import java.util.Properties;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.ModelInfo;
import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
import com.sun.xml.rpc.processor.model.soap.SOAPType;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
import com.sun.xml.rpc.wsdl.framework.AbstractDocument;

/**
 * @author JAX-RPC Development Team
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SchemaAnalyzer111 extends SchemaAnalyzer11 {

    /**
     * @param document
     * @param modelInfo
     * @param options
     * @param conflictingClassNames
     * @param javaTypes
     */
    public SchemaAnalyzer111(
        AbstractDocument document,
        ModelInfo modelInfo,
        Properties options,
        Set conflictingClassNames,
        JavaSimpleTypeCreator javaTypes) {
        super(document, modelInfo, options, conflictingClassNames, javaTypes);
    }

    protected SOAPType nillableSchemaTypeToSOAPType(TypeDefinitionComponent component) {
        // bug fix: 4961579
        QName baseTypeName = getSimpleTypeBaseName(component);                                                                              
        JavaSimpleType javaType =
              (
                  JavaSimpleType) _builtinSchemaTypeToJavaWrapperTypeMap
                      .get(baseTypeName);                                

//        JavaSimpleType javaType =
//            (JavaSimpleType) _builtinSchemaTypeToJavaWrapperTypeMap.get(
//                component.getName());
        if (javaType == null) {
            // disregard the nullability, since it will be taken care of by the section 5 encoding rules anyway
            return schemaTypeToSOAPType(component, component.getName());
        } else {
            // nullability matters
            SOAPSimpleType result =
                (SOAPSimpleType) _nillableSimpleTypeComponentToSOAPTypeMap.get(
                    component);
            if (result != null) {
                return result;
            }
            // bug fix 4961579
            result = new SOAPSimpleType(baseTypeName, javaType);           
//            result = new SOAPSimpleType(component.getName(), javaType);
            result.setSchemaTypeRef(component.getName());
            setReferenceable(result);
            _nillableSimpleTypeComponentToSOAPTypeMap.put(component, result);
            return result;
        }
    }
    
    // bug fix 4961579
    protected LiteralSimpleType getNillableLiteralSimpleType(QName typeName, 
        TypeDefinitionComponent typeDef) {


        QName baseTypeName = getSimpleTypeBaseName(typeDef);                                                                              
        JavaSimpleType javaType =
            (JavaSimpleType) _builtinSchemaTypeToJavaWrapperTypeMap
                                                     .get(baseTypeName);

        if (javaType == null) {
            return null;
        }
           
        LiteralSimpleType result =
            (LiteralSimpleType) _nillableSimpleTypeComponentToLiteralTypeMap
                .get(typeDef);
        if (result == null) {
            result =
                new LiteralSimpleType(typeName,
                    javaType,
                    true);                                        
            result.setSchemaTypeRef(typeDef.getName());
            _nillableSimpleTypeComponentToLiteralTypeMap
                .put(typeDef, result);
        }
        return result;
    }    

}

/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2002 International Business Machines Corp. 2002. All rights reserved.
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

package com.sun.xml.rpc.processor.modeler.j2ee.xml;

/**
* This class represents the complex type <javaXmlTypeMappingType>
*/
public class javaXmlTypeMappingType extends ComplexType {
    public javaXmlTypeMappingType() {
    }

    public void setJavaType(javaTypeType javaType) {
        setElementValue("java-type", javaType);
    }

    public javaTypeType getJavaType() {
        return (javaTypeType) getElementValue("java-type", "javaTypeType");
    }

    public boolean removeJavaType() {
        return removeElement("java-type");
    }

    public void setRootTypeQname(xsdQNameType rootTypeQname) {
        setElementValue("root-type-qname", rootTypeQname);
    }

    public xsdQNameType getRootTypeQname() {
        return (xsdQNameType) getElementValue(
            "root-type-qname",
            "xsdQNameType");
    }

    public boolean removeRootTypeQname() {
        return removeElement("root-type-qname");
    }

    public void setAnonymousTypeQname(string anonymousTypeQname) {
        setElementValue("anonymous-type-qname", anonymousTypeQname);
    }

    public string getAnonymousTypeQname() {
        return (string) getElementValue("anonymous-type-qname", "string");
    }

    public boolean removeAnonymousTypeQname() {
        return removeElement("anonymous-type-qname");
    }

    public void setQnameScope(qnameScopeType qnameScope) {
        setElementValue("qname-scope", qnameScope);
    }

    public qnameScopeType getQnameScope() {
        return (qnameScopeType) getElementValue(
            "qname-scope",
            "qnameScopeType");
    }

    public boolean removeQnameScope() {
        return removeElement("qname-scope");
    }

    public void setVariableMapping(
        int index,
        variableMappingType variableMapping) {
        setElementValue(index, "variable-mapping", variableMapping);
    }

    public variableMappingType getVariableMapping(int index) {
        return (variableMappingType) getElementValue(
            "variable-mapping",
            "variableMappingType",
            index);
    }

    public int getVariableMappingCount() {
        return sizeOfElement("variable-mapping");
    }

    public boolean removeVariableMapping(int index) {
        return removeElement(index, "variable-mapping");
    }

    public void setId(String id) {
        setAttributeValue("id", id);
    }

    public String getId() {
        return getAttributeValue("id");
    }

    public boolean removeId() {
        return removeAttribute("id");
    }

}

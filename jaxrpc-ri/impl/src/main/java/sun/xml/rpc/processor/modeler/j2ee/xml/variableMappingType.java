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
* This class represents the complex type <variableMappingType>
*/
public class variableMappingType extends ComplexType {
    public variableMappingType() {
    }

    public void setJavaVariableName(string javaVariableName) {
        setElementValue("java-variable-name", javaVariableName);
    }

    public string getJavaVariableName() {
        return (string) getElementValue("java-variable-name", "string");
    }

    public boolean removeJavaVariableName() {
        return removeElement("java-variable-name");
    }

    public void setDataMember(emptyType dataMember) {
        setElementValue("data-member", dataMember);
    }

    public emptyType getDataMember() {
        return (emptyType) getElementValue("data-member", "emptyType");
    }

    public boolean removeDataMember() {
        return removeElement("data-member");
    }

    public void setXmlWildcard(emptyType xmlWildcard) {
        setElementValue("xml-wildcard", xmlWildcard);
    }

    public emptyType getXmlWildcard() {
        return (emptyType) getElementValue("xml-wildcard", "emptyType");
    }

    public boolean removeXmlWildcard() {
        return removeElement("xml-wildcard");
    }

    public void setXmlElementName(string xmlElementName) {
        setElementValue("xml-element-name", xmlElementName);
    }

    public string getXmlElementName() {
        return (string) getElementValue("xml-element-name", "string");
    }

    public boolean removeXmlElementName() {
        return removeElement("xml-element-name");
    }

    public void setXmlAttributeName(string xmlAttributeName) {
        setElementValue("xml-attribute-name", xmlAttributeName);
    }

    public string getXmlAttributeName() {
        return (string) getElementValue("xml-attribute-name", "string");
    }

    public boolean removeXmlAttributeName() {
        return removeElement("xml-attribute-name");
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

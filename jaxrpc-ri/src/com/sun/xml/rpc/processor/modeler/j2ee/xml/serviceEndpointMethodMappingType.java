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
* This class represents the complex type <serviceEndpointMethodMappingType>
*/
public class serviceEndpointMethodMappingType extends ComplexType {
    public serviceEndpointMethodMappingType() {
    }

    public void setJavaMethodName(string javaMethodName) {
        setElementValue("java-method-name", javaMethodName);
    }

    public string getJavaMethodName() {
        return (string) getElementValue("java-method-name", "string");
    }

    public boolean removeJavaMethodName() {
        return removeElement("java-method-name");
    }

    public void setWsdlOperation(string wsdlOperation) {
        setElementValue("wsdl-operation", wsdlOperation);
    }

    public string getWsdlOperation() {
        return (string) getElementValue("wsdl-operation", "string");
    }

    public boolean removeWsdlOperation() {
        return removeElement("wsdl-operation");
    }

    public void setWrappedElement(emptyType wrappedElement) {
        setElementValue("wrapped-element", wrappedElement);
    }

    public emptyType getWrappedElement() {
        return (emptyType) getElementValue("wrapped-element", "emptyType");
    }

    public boolean removeWrappedElement() {
        return removeElement("wrapped-element");
    }

    public void setMethodParamPartsMapping(
        int index,
        methodParamPartsMappingType methodParamPartsMapping) {
        setElementValue(
            index,
            "method-param-parts-mapping",
            methodParamPartsMapping);
    }

    public methodParamPartsMappingType getMethodParamPartsMapping(int index) {
        return (methodParamPartsMappingType) getElementValue(
            "method-param-parts-mapping",
            "methodParamPartsMappingType",
            index);
    }

    public int getMethodParamPartsMappingCount() {
        return sizeOfElement("method-param-parts-mapping");
    }

    public boolean removeMethodParamPartsMapping(int index) {
        return removeElement(index, "method-param-parts-mapping");
    }

    public void setWsdlReturnValueMapping(wsdlReturnValueMappingType wsdlReturnValueMapping) {
        setElementValue("wsdl-return-value-mapping", wsdlReturnValueMapping);
    }

    public wsdlReturnValueMappingType getWsdlReturnValueMapping() {
        return (wsdlReturnValueMappingType) getElementValue(
            "wsdl-return-value-mapping",
            "wsdlReturnValueMappingType");
    }

    public boolean removeWsdlReturnValueMapping() {
        return removeElement("wsdl-return-value-mapping");
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

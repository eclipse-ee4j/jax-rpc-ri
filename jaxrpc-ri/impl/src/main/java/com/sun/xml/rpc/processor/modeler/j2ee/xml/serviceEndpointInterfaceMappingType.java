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
* This class represents the complex type <serviceEndpointInterfaceMappingType>
*/
public class serviceEndpointInterfaceMappingType extends ComplexType {
    public serviceEndpointInterfaceMappingType() {
    }

    public void setServiceEndpointInterface(fullyQualifiedClassType serviceEndpointInterface) {
        setElementValue("service-endpoint-interface", serviceEndpointInterface);
    }

    public fullyQualifiedClassType getServiceEndpointInterface() {
        return (fullyQualifiedClassType) getElementValue(
            "service-endpoint-interface",
            "fullyQualifiedClassType");
    }

    public boolean removeServiceEndpointInterface() {
        return removeElement("service-endpoint-interface");
    }

    public void setWsdlPortType(xsdQNameType wsdlPortType) {
        setElementValue("wsdl-port-type", wsdlPortType);
    }

    public xsdQNameType getWsdlPortType() {
        return (xsdQNameType) getElementValue("wsdl-port-type", "xsdQNameType");
    }

    public boolean removeWsdlPortType() {
        return removeElement("wsdl-port-type");
    }

    public void setWsdlBinding(xsdQNameType wsdlBinding) {
        setElementValue("wsdl-binding", wsdlBinding);
    }

    public xsdQNameType getWsdlBinding() {
        return (xsdQNameType) getElementValue("wsdl-binding", "xsdQNameType");
    }

    public boolean removeWsdlBinding() {
        return removeElement("wsdl-binding");
    }

    public void setServiceEndpointMethodMapping(
        int index,
        serviceEndpointMethodMappingType serviceEndpointMethodMapping) {
        setElementValue(
            index,
            "service-endpoint-method-mapping",
            serviceEndpointMethodMapping);
    }

    public serviceEndpointMethodMappingType getServiceEndpointMethodMapping(int index) {
        return (serviceEndpointMethodMappingType) getElementValue(
            "service-endpoint-method-mapping",
            "serviceEndpointMethodMappingType",
            index);
    }

    public int getServiceEndpointMethodMappingCount() {
        return sizeOfElement("service-endpoint-method-mapping");
    }

    public boolean removeServiceEndpointMethodMapping(int index) {
        return removeElement(index, "service-endpoint-method-mapping");
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

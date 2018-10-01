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
* This class represents the complex type <serviceInterfaceMappingType>
*/
public class serviceInterfaceMappingType extends ComplexType {
    public serviceInterfaceMappingType() {
    }

    public void setServiceInterface(fullyQualifiedClassType serviceInterface) {
        setElementValue("service-interface", serviceInterface);
    }

    public fullyQualifiedClassType getServiceInterface() {
        return (fullyQualifiedClassType) getElementValue(
            "service-interface",
            "fullyQualifiedClassType");
    }

    public boolean removeServiceInterface() {
        return removeElement("service-interface");
    }

    public void setWsdlServiceName(xsdQNameType wsdlServiceName) {
        setElementValue("wsdl-service-name", wsdlServiceName);
    }

    public xsdQNameType getWsdlServiceName() {
        return (xsdQNameType) getElementValue(
            "wsdl-service-name",
            "xsdQNameType");
    }

    public boolean removeWsdlServiceName() {
        return removeElement("wsdl-service-name");
    }

    public void setPortMapping(int index, portMappingType portMapping) {
        setElementValue(index, "port-mapping", portMapping);
    }

    public portMappingType getPortMapping(int index) {
        return (portMappingType) getElementValue(
            "port-mapping",
            "portMappingType",
            index);
    }

    public int getPortMappingCount() {
        return sizeOfElement("port-mapping");
    }

    public boolean removePortMapping(int index) {
        return removeElement(index, "port-mapping");
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

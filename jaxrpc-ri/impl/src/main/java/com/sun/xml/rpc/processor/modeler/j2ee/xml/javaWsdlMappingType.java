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
* This class represents the complex type <javaWsdlMappingType>
*/
public class javaWsdlMappingType extends ComplexType {
    public javaWsdlMappingType() {
    }

    public void setPackageMapping(
        int index,
        packageMappingType packageMapping) {
        setElementValue(index, "package-mapping", packageMapping);
    }

    public packageMappingType getPackageMapping(int index) {
        return (packageMappingType) getElementValue(
            "package-mapping",
            "packageMappingType",
            index);
    }

    public int getPackageMappingCount() {
        return sizeOfElement("package-mapping");
    }

    public boolean removePackageMapping(int index) {
        return removeElement(index, "package-mapping");
    }

    public void setJavaXmlTypeMapping(
        int index,
        javaXmlTypeMappingType javaXmlTypeMapping) {
        setElementValue(index, "java-xml-type-mapping", javaXmlTypeMapping);
    }

    public javaXmlTypeMappingType getJavaXmlTypeMapping(int index) {
        return (javaXmlTypeMappingType) getElementValue(
            "java-xml-type-mapping",
            "javaXmlTypeMappingType",
            index);
    }

    public int getJavaXmlTypeMappingCount() {
        return sizeOfElement("java-xml-type-mapping");
    }

    public boolean removeJavaXmlTypeMapping(int index) {
        return removeElement(index, "java-xml-type-mapping");
    }

    public void setExceptionMapping(
        int index,
        exceptionMappingType exceptionMapping) {
        setElementValue(index, "exception-mapping", exceptionMapping);
    }

    public exceptionMappingType getExceptionMapping(int index) {
        return (exceptionMappingType) getElementValue(
            "exception-mapping",
            "exceptionMappingType",
            index);
    }

    public int getExceptionMappingCount() {
        return sizeOfElement("exception-mapping");
    }

    public boolean removeExceptionMapping(int index) {
        return removeElement(index, "exception-mapping");
    }

    public void setServiceInterfaceMapping(
        int index,
        serviceInterfaceMappingType serviceInterfaceMapping) {
        setElementValue(
            index,
            "service-interface-mapping",
            serviceInterfaceMapping);
    }

    public serviceInterfaceMappingType getServiceInterfaceMapping(int index) {
        return (serviceInterfaceMappingType) getElementValue(
            "service-interface-mapping",
            "serviceInterfaceMappingType",
            index);
    }

    public int getServiceInterfaceMappingCount() {
        return sizeOfElement("service-interface-mapping");
    }

    public boolean removeServiceInterfaceMapping(int index) {
        return removeElement(index, "service-interface-mapping");
    }

    public void setServiceEndpointInterfaceMapping(
        int index,
        serviceEndpointInterfaceMappingType serviceEndpointInterfaceMapping) {
        setElementValue(
            index,
            "service-endpoint-interface-mapping",
            serviceEndpointInterfaceMapping);
    }

    public serviceEndpointInterfaceMappingType getServiceEndpointInterfaceMapping(int index) {
        return (serviceEndpointInterfaceMappingType) getElementValue(
            "service-endpoint-interface-mapping",
            "serviceEndpointInterfaceMappingType",
            index);
    }

    public int getServiceEndpointInterfaceMappingCount() {
        return sizeOfElement("service-endpoint-interface-mapping");
    }

    public boolean removeServiceEndpointInterfaceMapping(int index) {
        return removeElement(index, "service-endpoint-interface-mapping");
    }

    public void setVersion(deweyVersionType version) {
        setAttributeValue("version", version);
    }

    public deweyVersionType getVersion() {
        return (deweyVersionType) getAttributeValue(
            "version",
            "deweyVersionType");
    }

    public boolean removeVersion() {
        return removeAttribute("version");
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

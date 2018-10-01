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
* This class represents the complex type <serviceRefType>
*/
public class serviceRefType extends ComplexType {
    public serviceRefType() {
    }

    public void setServiceRefName(String serviceRefName) {
        setElementValue("service-ref-name", serviceRefName);
    }

    public String getServiceRefName() {
        return getElementValue("service-ref-name");
    }

    public boolean removeServiceRefName() {
        return removeElement("service-ref-name");
    }

    public void setServiceInterface(String serviceInterface) {
        setElementValue("service-interface", serviceInterface);
    }

    public String getServiceInterface() {
        return getElementValue("service-interface");
    }

    public boolean removeServiceInterface() {
        return removeElement("service-interface");
    }

    public void setWsdlFile(String wsdlFile) {
        setElementValue("wsdl-file", wsdlFile);
    }

    public String getWsdlFile() {
        return getElementValue("wsdl-file");
    }

    public boolean removeWsdlFile() {
        return removeElement("wsdl-file");
    }

    public void setJaxrpcMappingFile(String jaxrpcMappingFile) {
        setElementValue("jaxrpc-mapping-file", jaxrpcMappingFile);
    }

    public String getJaxrpcMappingFile() {
        return getElementValue("jaxrpc-mapping-file");
    }

    public boolean removeJaxrpcMappingFile() {
        return removeElement("jaxrpc-mapping-file");
    }

    public void setServiceQname(String serviceQname) {
        setElementValue("service-qname", serviceQname);
    }

    public String getServiceQname() {
        return getElementValue("service-qname");
    }

    public boolean removeServiceQname() {
        return removeElement("service-qname");
    }

    public void setPortComponentRef(
        int index,
        portComponentRefType portComponentRef) {
        setElementValue(index, "port-component-ref", portComponentRef);
    }

    public portComponentRefType getPortComponentRef(int index) {
        return (portComponentRefType) getElementValue(
            "port-component-ref",
            "portComponentRefType",
            index);
    }

    public int getPortComponentRefCount() {
        return sizeOfElement("port-component-ref");
    }

    public boolean removePortComponentRef(int index) {
        return removeElement(index, "port-component-ref");
    }

    public void setHandler(int index, serviceRef_handlerType handler) {
        setElementValue(index, "handler", handler);
    }

    public serviceRef_handlerType getHandler(int index) {
        return (serviceRef_handlerType) getElementValue(
            "handler",
            "serviceRef_handlerType",
            index);
    }

    public int getHandlerCount() {
        return sizeOfElement("handler");
    }

    public boolean removeHandler(int index) {
        return removeElement(index, "handler");
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

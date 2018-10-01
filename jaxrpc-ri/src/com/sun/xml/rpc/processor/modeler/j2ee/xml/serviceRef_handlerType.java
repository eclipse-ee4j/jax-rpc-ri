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
* This class represents the complex type <serviceRef_handlerType>
*/
public class serviceRef_handlerType extends ComplexType {
    public serviceRef_handlerType() {
    }

    public void setHandlerName(String handlerName) {
        setElementValue("handler-name", handlerName);
    }

    public String getHandlerName() {
        return getElementValue("handler-name");
    }

    public boolean removeHandlerName() {
        return removeElement("handler-name");
    }

    public void setHandlerClass(String handlerClass) {
        setElementValue("handler-class", handlerClass);
    }

    public String getHandlerClass() {
        return getElementValue("handler-class");
    }

    public boolean removeHandlerClass() {
        return removeElement("handler-class");
    }

    public void setInitParam(int index, String initParam) {
        setElementValue(index, "init-param", initParam);
    }

    public String getInitParam(int index) {
        return getElementValue("init-param", index);
    }

    public int getInitParamCount() {
        return sizeOfElement("init-param");
    }

    public boolean removeInitParam(int index) {
        return removeElement(index, "init-param");
    }

    public void setSoapHeader(int index, String soapHeader) {
        setElementValue(index, "soap-header", soapHeader);
    }

    public String getSoapHeader(int index) {
        return getElementValue("soap-header", index);
    }

    public int getSoapHeaderCount() {
        return sizeOfElement("soap-header");
    }

    public boolean removeSoapHeader(int index) {
        return removeElement(index, "soap-header");
    }

    public void setSoapRole(int index, String soapRole) {
        setElementValue(index, "soap-role", soapRole);
    }

    public String getSoapRole(int index) {
        return getElementValue("soap-role", index);
    }

    public int getSoapRoleCount() {
        return sizeOfElement("soap-role");
    }

    public boolean removeSoapRole(int index) {
        return removeElement(index, "soap-role");
    }

    public void setPortName(int index, String portName) {
        setElementValue(index, "port-name", portName);
    }

    public String getPortName(int index) {
        return getElementValue("port-name", index);
    }

    public int getPortNameCount() {
        return sizeOfElement("port-name");
    }

    public boolean removePortName(int index) {
        return removeElement(index, "port-name");
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

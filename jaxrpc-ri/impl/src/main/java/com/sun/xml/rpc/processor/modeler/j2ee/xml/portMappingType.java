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
* This class represents the complex type <portMappingType>
*/
public class portMappingType extends ComplexType {
    public portMappingType() {
    }

    public void setPortName(string portName) {
        setElementValue("port-name", portName);
    }

    public string getPortName() {
        return (string) getElementValue("port-name", "string");
    }

    public boolean removePortName() {
        return removeElement("port-name");
    }

    public void setJavaPortName(string javaPortName) {
        setElementValue("java-port-name", javaPortName);
    }

    public string getJavaPortName() {
        return (string) getElementValue("java-port-name", "string");
    }

    public boolean removeJavaPortName() {
        return removeElement("java-port-name");
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

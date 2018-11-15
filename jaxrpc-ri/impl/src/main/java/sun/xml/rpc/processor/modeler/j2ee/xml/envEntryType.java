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
* This class represents the complex type <envEntryType>
*/
public class envEntryType extends ComplexType {
    public envEntryType() {
    }

    public void setDescription(int index, descriptionType description) {
        setElementValue(index, "description", description);
    }

    public descriptionType getDescription(int index) {
        return (descriptionType) getElementValue(
            "description",
            "descriptionType",
            index);
    }

    public int getDescriptionCount() {
        return sizeOfElement("description");
    }

    public boolean removeDescription(int index) {
        return removeElement(index, "description");
    }

    public void setEnvEntryName(jndiNameType envEntryName) {
        setElementValue("env-entry-name", envEntryName);
    }

    public jndiNameType getEnvEntryName() {
        return (jndiNameType) getElementValue("env-entry-name", "jndiNameType");
    }

    public boolean removeEnvEntryName() {
        return removeElement("env-entry-name");
    }

    public void setEnvEntryType(envEntryTypeValuesType envEntryType) {
        setElementValue("env-entry-type", envEntryType);
    }

    public envEntryTypeValuesType getEnvEntryType() {
        return (envEntryTypeValuesType) getElementValue(
            "env-entry-type",
            "envEntryTypeValuesType");
    }

    public boolean removeEnvEntryType() {
        return removeElement("env-entry-type");
    }

    public void setEnvEntryValue(xsdStringType envEntryValue) {
        setElementValue("env-entry-value", envEntryValue);
    }

    public xsdStringType getEnvEntryValue() {
        return (xsdStringType) getElementValue(
            "env-entry-value",
            "xsdStringType");
    }

    public boolean removeEnvEntryValue() {
        return removeElement("env-entry-value");
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

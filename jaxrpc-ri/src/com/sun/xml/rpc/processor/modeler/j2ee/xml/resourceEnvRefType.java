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
* This class represents the complex type <resourceEnvRefType>
*/
public class resourceEnvRefType extends ComplexType {
    public resourceEnvRefType() {
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

    public void setResourceEnvRefName(jndiNameType resourceEnvRefName) {
        setElementValue("resource-env-ref-name", resourceEnvRefName);
    }

    public jndiNameType getResourceEnvRefName() {
        return (jndiNameType) getElementValue(
            "resource-env-ref-name",
            "jndiNameType");
    }

    public boolean removeResourceEnvRefName() {
        return removeElement("resource-env-ref-name");
    }

    public void setResourceEnvRefType(fullyQualifiedClassType resourceEnvRefType) {
        setElementValue("resource-env-ref-type", resourceEnvRefType);
    }

    public fullyQualifiedClassType getResourceEnvRefType() {
        return (fullyQualifiedClassType) getElementValue(
            "resource-env-ref-type",
            "fullyQualifiedClassType");
    }

    public boolean removeResourceEnvRefType() {
        return removeElement("resource-env-ref-type");
    }

    public void setDeploymentExtension(
        int index,
        deploymentExtensionType deploymentExtension) {
        setElementValue(index, "deployment-extension", deploymentExtension);
    }

    public deploymentExtensionType getDeploymentExtension(int index) {
        return (deploymentExtensionType) getElementValue(
            "deployment-extension",
            "deploymentExtensionType",
            index);
    }

    public int getDeploymentExtensionCount() {
        return sizeOfElement("deployment-extension");
    }

    public boolean removeDeploymentExtension(int index) {
        return removeElement(index, "deployment-extension");
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

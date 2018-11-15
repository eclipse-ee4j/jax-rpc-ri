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
* This class represents the complex type <ejbLocalRefType>
*/
public class ejbLocalRefType extends ComplexType {
    public ejbLocalRefType() {
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

    public void setEjbRefName(ejbRefNameType ejbRefName) {
        setElementValue("ejb-ref-name", ejbRefName);
    }

    public ejbRefNameType getEjbRefName() {
        return (ejbRefNameType) getElementValue(
            "ejb-ref-name",
            "ejbRefNameType");
    }

    public boolean removeEjbRefName() {
        return removeElement("ejb-ref-name");
    }

    public void setEjbRefType(ejbRefTypeType ejbRefType) {
        setElementValue("ejb-ref-type", ejbRefType);
    }

    public ejbRefTypeType getEjbRefType() {
        return (ejbRefTypeType) getElementValue(
            "ejb-ref-type",
            "ejbRefTypeType");
    }

    public boolean removeEjbRefType() {
        return removeElement("ejb-ref-type");
    }

    public void setLocalHome(localHomeType localHome) {
        setElementValue("local-home", localHome);
    }

    public localHomeType getLocalHome() {
        return (localHomeType) getElementValue("local-home", "localHomeType");
    }

    public boolean removeLocalHome() {
        return removeElement("local-home");
    }

    public void setLocal(localType local) {
        setElementValue("local", local);
    }

    public localType getLocal() {
        return (localType) getElementValue("local", "localType");
    }

    public boolean removeLocal() {
        return removeElement("local");
    }

    public void setEjbLink(ejbLinkType ejbLink) {
        setElementValue("ejb-link", ejbLink);
    }

    public ejbLinkType getEjbLink() {
        return (ejbLinkType) getElementValue("ejb-link", "ejbLinkType");
    }

    public boolean removeEjbLink() {
        return removeElement("ejb-link");
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

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
* This class represents the complex type <messageDestinationRefType>
*/
public class messageDestinationRefType extends ComplexType {
    public messageDestinationRefType() {
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

    public void setMessageDestinationRefName(jndiNameType messageDestinationRefName) {
        setElementValue(
            "message-destination-ref-name",
            messageDestinationRefName);
    }

    public jndiNameType getMessageDestinationRefName() {
        return (jndiNameType) getElementValue(
            "message-destination-ref-name",
            "jndiNameType");
    }

    public boolean removeMessageDestinationRefName() {
        return removeElement("message-destination-ref-name");
    }

    public void setMessageDestinationType(messageDestinationTypeType messageDestinationType) {
        setElementValue("message-destination-type", messageDestinationType);
    }

    public messageDestinationTypeType getMessageDestinationType() {
        return (messageDestinationTypeType) getElementValue(
            "message-destination-type",
            "messageDestinationTypeType");
    }

    public boolean removeMessageDestinationType() {
        return removeElement("message-destination-type");
    }

    public void setMessageDestinationUsage(messageDestinationUsageType messageDestinationUsage) {
        setElementValue("message-destination-usage", messageDestinationUsage);
    }

    public messageDestinationUsageType getMessageDestinationUsage() {
        return (messageDestinationUsageType) getElementValue(
            "message-destination-usage",
            "messageDestinationUsageType");
    }

    public boolean removeMessageDestinationUsage() {
        return removeElement("message-destination-usage");
    }

    public void setMessageDestinationLink(messageDestinationLinkType messageDestinationLink) {
        setElementValue("message-destination-link", messageDestinationLink);
    }

    public messageDestinationLinkType getMessageDestinationLink() {
        return (messageDestinationLinkType) getElementValue(
            "message-destination-link",
            "messageDestinationLinkType");
    }

    public boolean removeMessageDestinationLink() {
        return removeElement("message-destination-link");
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

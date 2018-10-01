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
* This class represents the complex type <iconType>
*/
public class iconType extends ComplexType {
    public iconType() {
    }

    public void setSmallIcon(pathType smallIcon) {
        setElementValue("small-icon", smallIcon);
    }

    public pathType getSmallIcon() {
        return (pathType) getElementValue("small-icon", "pathType");
    }

    public boolean removeSmallIcon() {
        return removeElement("small-icon");
    }

    public void setLargeIcon(pathType largeIcon) {
        setElementValue("large-icon", largeIcon);
    }

    public pathType getLargeIcon() {
        return (pathType) getElementValue("large-icon", "pathType");
    }

    public boolean removeLargeIcon() {
        return removeElement("large-icon");
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

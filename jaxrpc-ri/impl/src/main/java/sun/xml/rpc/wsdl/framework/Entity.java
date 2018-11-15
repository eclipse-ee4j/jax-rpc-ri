/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.rpc.wsdl.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * An entity, typically corresponding to an XML element.
 *
 * @author JAX-RPC Development Team
 */
public abstract class Entity implements Elemental {

    public Entity() {
    }

    public Object getProperty(String key) {
        if (_properties == null)
            return null;
        return _properties.get(key);
    }

    public void setProperty(String key, Object value) {
        if (value == null) {
            removeProperty(key);
            return;
        }

        if (_properties == null) {
            _properties = new HashMap();
        }
        _properties.put(key, value);
    }

    public void removeProperty(String key) {
        if (_properties != null) {
            _properties.remove(key);
        }
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        // no-op by default
    }

    public void withAllQNamesDo(QNameAction action) {
        action.perform(getElementName());
    }

    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        // no-op by default
    }

    public abstract void validateThis();

    protected void failValidation(String key) {
        throw new ValidationException(key, getElementName().getLocalPart());
    }

    protected void failValidation(String key, String arg) {
        throw new ValidationException(
            key,
            new Object[] { arg, getElementName().getLocalPart()});
    }

    private Map _properties;
}

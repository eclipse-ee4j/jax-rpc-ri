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

package com.sun.xml.rpc.encoding.soap;

import java.io.Serializable;

/**
 *
 * @author JAX-RPC Development Team
 */
public class JAXRpcMapEntry implements Serializable {

    private Object key = null;
    private Object value = null;

    public JAXRpcMapEntry() {
    }

    public JAXRpcMapEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj != null && getClass() == obj.getClass()) {
            JAXRpcMapEntry map_entry = (JAXRpcMapEntry) obj;
            return (
                (key == null && map_entry.key == null)
                    || (key != null && key.equals(map_entry.key)))
                && ((value == null && map_entry.value == null)
                    || (value != null && value.equals(map_entry.value)));
        } else {
            return false;
        }
    }

}

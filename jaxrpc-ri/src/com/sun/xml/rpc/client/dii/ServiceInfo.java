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

/**
 * @author JAX-RPC Development Team
 */
package com.sun.xml.rpc.client.dii;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

public class ServiceInfo {
    protected Map portInfoMap;
    protected String defaultNamespace;

    //stores service information from examined wsdl definitions
    //stores service port information in a port map
    public ServiceInfo() {
        init();
    }

    protected void init() {
        portInfoMap = new HashMap();
        defaultNamespace = "";
    }

    public void setDefaultNamespace(String namespace) {
        defaultNamespace = namespace;
    }

    public PortInfo getPortInfo(QName portName) {
        PortInfo port = (PortInfo) portInfoMap.get(portName);
        if (port == null) {
            port = new PortInfo(portName);
            port.setDefaultNamespace(defaultNamespace);
            portInfoMap.put(portName, port);
        }

        return port;
    }

    public Iterator getPortNames() {
        return portInfoMap.keySet().iterator();
    }
}

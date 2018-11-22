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

package com.sun.xml.rpc.server.http.ea;

/**
 * WSDLPortInfo contains information about a port inside a WSDL document.
 *
 * @author JAX-RPC Development Team
 */
public class WSDLPortInfo {

    public WSDLPortInfo(
        String targetNamespace,
        String serviceName,
        String portName) {
        this.targetNamespace = targetNamespace;
        this.serviceName = serviceName;
        this.portName = portName;
    }

    public String getTargetNamespace() {
        return targetNamespace;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPortName() {
        return portName;
    }

    private String targetNamespace;
    private String serviceName;
    private String portName;
}

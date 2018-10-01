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

package com.sun.xml.rpc.processor.config;

import com.sun.xml.rpc.soap.SOAPVersion;

/**
 *
 * @author JAX-RPC Development Team
 */
public class RmiInterfaceInfo {

    public RmiInterfaceInfo() {}

    public RmiModelInfo getParent() {
        return parent;
    }

    public void setParent(RmiModelInfo rsi) {
        parent = rsi;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public String getServantName() {
        return servantName;
    }

    public void setServantName(String s) {
        servantName = s;
    }

    public String getSOAPAction() {
        return soapAction;
    }

    public void setSOAPAction(String s) {
        soapAction = s;
    }

    public String getSOAPActionBase() {
        return soapActionBase;
    }

    public void setSOAPActionBase(String s) {
        soapActionBase = s;
    }
    
    public SOAPVersion getSOAPVersion() {
        return soapVersion;
    }
    
    public void setSOAPVersion(SOAPVersion version) {
        soapVersion = version;
    }

    public HandlerChainInfo getClientHandlerChainInfo() {
        if (clientHandlerChainInfo != null) {
            return clientHandlerChainInfo;
        } else {
            return parent.getClientHandlerChainInfo();
        }
    }

    public void setClientHandlerChainInfo(HandlerChainInfo i) {
        clientHandlerChainInfo = i;
    }

    public HandlerChainInfo getServerHandlerChainInfo() {
        if (serverHandlerChainInfo != null) {
            return serverHandlerChainInfo;
        } else {
            return parent.getServerHandlerChainInfo();
        }
    }

    public void setServerHandlerChainInfo(HandlerChainInfo i) {
        serverHandlerChainInfo = i;
    }

    private RmiModelInfo parent;
    private String soapAction;
    private String soapActionBase;
    private String name;
    private String servantName;
    private HandlerChainInfo clientHandlerChainInfo;
    private HandlerChainInfo serverHandlerChainInfo;
    private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
}

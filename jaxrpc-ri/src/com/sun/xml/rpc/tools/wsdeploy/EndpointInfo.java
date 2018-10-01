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

package com.sun.xml.rpc.tools.wsdeploy;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.HandlerChainInfo;

/**
 *
 * @author JAX-RPC Development Team
 */
public class EndpointInfo {
    
    public EndpointInfo() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String s) {
        displayName = s;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String s) {
        description = s;
    }
    
    public String getInterface() {
        return interfaceClass;
    }
    
    public void setInterface(String s) {
        interfaceClass = s;
    }
    
    public String getImplementation() {
        return implementationClass;
    }
    
    public void setImplementation(String s) {
        implementationClass = s;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String s) {
        model = s;
    }
    
    public HandlerChainInfo getClientHandlerChainInfo() {
        return clientHandlerChainInfo;
    }
    
    public void setClientHandlerChainInfo(HandlerChainInfo i) {
        clientHandlerChainInfo = i;
    }
    
    public HandlerChainInfo getServerHandlerChainInfo() {
        return serverHandlerChainInfo;
    }
    
    public void setServerHandlerChainInfo(HandlerChainInfo i) {
        serverHandlerChainInfo = i;
    }
    
    public boolean isRuntimeDeployed() {
        return runtimeDeployed;
    }
    
    public void setRuntimeDeployed(boolean b) {
        runtimeDeployed = b;
    }
    
    public String getRuntimeModel() {
        return runtimeModel;
    }
    
    public void setRuntimeModel(String s) {
        runtimeModel = s;
    }
    
    public String getRuntimeWSDL() {
        return runtimeWSDL;
    }
    
    public void setRuntimeWSDL(String s) {
        runtimeWSDL = s;
    }
    
    public String getRuntimeTie() {
        return runtimeTie;
    }
    
    public void setRuntimeTie(String s) {
        runtimeTie = s;
    }
    
    public QName getRuntimeServiceName() {
        return runtimeServiceName;
    }
    
    public void setRuntimeServiceName(QName n) {
        runtimeServiceName = n;
    }
    
    public QName getRuntimePortName() {
        return runtimePortName;
    }
    
    public void setRuntimePortName(QName n) {
        runtimePortName = n;
    }
    
    public String getRuntimeUrlPattern() {
        return runtimeUrlPattern;
    }
    
    public void setRuntimeUrlPattern(String s) {
        runtimeUrlPattern = s;
    }
    
    private String name;
    private String displayName;
    private String description;
    private String interfaceClass;
    private String implementationClass;
    private String model;
    private HandlerChainInfo clientHandlerChainInfo;
    private HandlerChainInfo serverHandlerChainInfo;
    
    // runtime information - apologies for the funny names
    private boolean runtimeDeployed;
    private String runtimeModel;
    private String runtimeWSDL;
    private String runtimeTie;
    private QName runtimeServiceName;
    private QName runtimePortName;
    private String runtimeUrlPattern;
}

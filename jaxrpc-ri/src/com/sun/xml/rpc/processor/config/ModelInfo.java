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

import java.util.Properties;

import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.modeler.Modeler;

/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class ModelInfo implements com.sun.xml.rpc.spi.tools.ModelInfo {

    protected ModelInfo() {} 

    public Configuration getParent() {
        return _parent;
    }

    public void setParent(Configuration c) {
        _parent = c;
    }

    public String getName() {
        return _name;
    }

    public void setName(String s) {
        _name = s;
    }

    public Configuration getConfiguration() {
        return _parent;
    }

    public TypeMappingRegistryInfo getTypeMappingRegistry() {
        return _typeMappingRegistryInfo;
    }

    public void setTypeMappingRegistry(TypeMappingRegistryInfo i) {
        _typeMappingRegistryInfo = i;
    }

    public HandlerChainInfo getClientHandlerChainInfo() {
        return _clientHandlerChainInfo;
    }

    public void setClientHandlerChainInfo(HandlerChainInfo i) {
        _clientHandlerChainInfo = i;
    }

    public HandlerChainInfo getServerHandlerChainInfo() {
        return _serverHandlerChainInfo;
    }

    public void setServerHandlerChainInfo(HandlerChainInfo i) {
        _serverHandlerChainInfo = i;
    }

    public NamespaceMappingRegistryInfo getNamespaceMappingRegistry() {
        return _namespaceMappingRegistryInfo;
    }

    public void setNamespaceMappingRegistry(
        com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo i) {
            
        _namespaceMappingRegistryInfo = (NamespaceMappingRegistryInfo) i;
    }

    public String getJavaPackageName() {
        return _javaPackageName;
    }

    public void setJavaPackageName(String s) {
        _javaPackageName = s;
    }

    public Model buildModel(Properties options){
        return getModeler(options).buildModel();
    }

    protected abstract Modeler getModeler(Properties options);

    private Configuration _parent;
    private String _name;
    private String _javaPackageName;
    private TypeMappingRegistryInfo _typeMappingRegistryInfo;
    private HandlerChainInfo _clientHandlerChainInfo;
    private HandlerChainInfo _serverHandlerChainInfo;
    private NamespaceMappingRegistryInfo _namespaceMappingRegistryInfo;
}

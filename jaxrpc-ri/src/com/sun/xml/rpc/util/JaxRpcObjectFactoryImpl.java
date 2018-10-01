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

package com.sun.xml.rpc.util;

import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.sun.xml.rpc.client.ClientTransportFactory;
import com.sun.xml.rpc.client.http.HttpClientTransportFactory;
import com.sun.xml.rpc.client.local.LocalClientTransportFactory;
import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.config.HandlerInfo;
import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
import com.sun.xml.rpc.processor.config.NamespaceMappingRegistryInfo;
import com.sun.xml.rpc.processor.config.NoMetadataModelInfo;
import com.sun.xml.rpc.processor.config.parser.ModelInfoPlugin;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.processor.util.XMLModelFileFilter;
import com.sun.xml.rpc.server.http.Implementor;
import com.sun.xml.rpc.server.http.ImplementorCache;
import com.sun.xml.rpc.server.http.JAXRPCServletDelegate;
import com.sun.xml.rpc.server.http.RuntimeEndpointInfo;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;
import com.sun.xml.rpc.tools.plugin.ToolPluginConstants;
import com.sun.xml.rpc.tools.plugin.ToolPluginFactory;
import com.sun.xml.rpc.tools.wscompile.CompileTool;
import com.sun.xml.rpc.wsdl.parser.WSDLParser;
import com.sun.xml.rpc.wsdl.parser.WSDLUtil;

/**
 * Singleton factory class to instantiate concrete objects.
 *
 * @author JAX-RPC Development Team
 */
public class JaxRpcObjectFactoryImpl
    extends com.sun.xml.rpc.spi.JaxRpcObjectFactory {

    public JaxRpcObjectFactoryImpl() {
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .tools
        .ModelFileModelInfo createModelFileModelInfo() {
        return new ModelFileModelInfo();
    }

    public com.sun.xml.rpc.spi.tools.J2EEModelInfo createJ2EEModelInfo(
        java.net.URL mappingFile)
        throws Exception {

        ModelInfoPlugin plugin =
            (ModelInfoPlugin) ToolPluginFactory.getInstance().getPlugin(
                ToolPluginConstants.J2EE_PLUGIN);
        return (com.sun.xml.rpc.spi.tools.J2EEModelInfo) plugin.createModelInfo(
            mappingFile);
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .runtime
        .ClientTransportFactory createClientTransportFactory(
            int type,
            java.io.OutputStream outputStream) {
        ClientTransportFactory clientFactory = null;
        switch (type) {
            case com.sun.xml.rpc.spi.runtime.ClientTransportFactoryTypes.HTTP :
                return new HttpClientTransportFactory(outputStream);

            case com
                .sun
                .xml
                .rpc
                .spi
                .runtime
                .ClientTransportFactoryTypes
                .LOCAL :
                return new LocalClientTransportFactory(null, outputStream);
        }
        return clientFactory;
    }

    public com.sun.xml.rpc.spi.tools.CompileTool createCompileTool(
        OutputStream outputStream,
        String str) {
        return new CompileTool(outputStream, str);
    }

    public com.sun.xml.rpc.spi.runtime.Implementor createImplementor(
        ServletContext servletContext,
        com.sun.xml.rpc.spi.runtime.Tie tie) {
        return new Implementor(servletContext, tie);
    }

    public com.sun.xml.rpc.spi.runtime.ImplementorCache createImplementorCache(
        ServletConfig servletConfig) {
        return new ImplementorCache(servletConfig);
    }

    public com.sun.xml.rpc.spi.tools.Configuration createConfiguration(
        com.sun.xml.rpc.spi.tools.ProcessorEnvironment processorEnvironment) {
        return new Configuration(processorEnvironment);
    }

    public com.sun.xml.rpc.spi.tools.HandlerInfo createHandlerInfo() {
        return new HandlerInfo();
    }

    public com.sun.xml.rpc.spi.tools.Names createNames() {
        return new Names();
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .tools
        .NamespaceMappingInfo createNamespaceMappingInfo(
            String namespaceURI,
            String javaPackageName) {
        return new NamespaceMappingInfo(namespaceURI, javaPackageName);
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .tools
        .NamespaceMappingRegistryInfo createNamespaceMappingRegistryInfo() {
        return new NamespaceMappingRegistryInfo();
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .tools
        .NoMetadataModelInfo createNoMetadataModelInfo() {
        return new NoMetadataModelInfo();
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .runtime
        .RuntimeEndpointInfo createRuntimeEndpointInfo() {
        return new RuntimeEndpointInfo();
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .runtime
        .SOAPMessageContext createSOAPMessageContext() {
        return new SOAPMessageContext();
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .runtime
        .ServletDelegate createServletDelegate() {
        return new JAXRPCServletDelegate();
    }

    public com
        .sun
        .xml
        .rpc
        .spi
        .tools
        .XMLModelFileFilter createXMLModelFileFilter() {
        return new XMLModelFileFilter();
    }

    public com.sun.xml.rpc.spi.tools.WSDLUtil createWSDLUtil() {
        return new WSDLUtil();
    }

    public com.sun.xml.rpc.spi.tools.WSDLParser createWSDLParser() {
        return new WSDLParser();
    }
}

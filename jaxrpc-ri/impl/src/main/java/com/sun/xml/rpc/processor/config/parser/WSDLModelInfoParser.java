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

package com.sun.xml.rpc.processor.config.parser;

import com.sun.xml.rpc.processor.config.ModelInfo;
import com.sun.xml.rpc.processor.config.WSDLModelInfo;
import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
import com.sun.xml.rpc.streaming.XMLReader;

/**
 *
 * @author JAX-RPC Development Team
 */
public class WSDLModelInfoParser extends ModelInfoParser {

    public WSDLModelInfoParser(ProcessorEnvironment env) {
        super(env);
    }
    
    public ModelInfo parse(XMLReader reader) {
        WSDLModelInfo modelInfo = new WSDLModelInfo();
        String location = ParserUtil.getMandatoryNonEmptyAttribute(reader,
            Constants.ATTR_LOCATION);
        modelInfo.setLocation(location);
        String packageName = ParserUtil.getMandatoryNonEmptyAttribute(reader,
            Constants.ATTR_PACKAGE_NAME);
        modelInfo.setJavaPackageName(packageName);
        
        boolean gotTypeMappingRegistry = false;
        boolean gotHandlerChains = false;
        boolean gotNamespaceMappingRegistry = false;
        while (reader.nextElementContent() != XMLReader.END) {
            if (reader.getName().equals(
                Constants.QNAME_TYPE_MAPPING_REGISTRY)) {
                    
                if (gotTypeMappingRegistry) {
                    ParserUtil.failWithLocalName("configuration.invalidElement",
                        reader);
                } else {
                    modelInfo.setTypeMappingRegistry(
                        parseTypeMappingRegistryInfo(reader));
                    gotTypeMappingRegistry = true;
                }
            } else if (reader.getName().equals(
                Constants.QNAME_HANDLER_CHAINS)) {
                    
                if (gotHandlerChains) {
                    ParserUtil.failWithLocalName("configuration.invalidElement",
                        reader);
                } else {
                    HandlerChainInfoData data =
                        parseHandlerChainInfoData(reader);
                    modelInfo.setClientHandlerChainInfo(
                        data.getClientHandlerChainInfo());
                    modelInfo.setServerHandlerChainInfo(
                        data.getServerHandlerChainInfo());
                    gotHandlerChains = true;
                }
            } else if (reader.getName().equals(
                Constants.QNAME_NAMESPACE_MAPPING_REGISTRY)) {
                    
                if (gotNamespaceMappingRegistry) {
                    ParserUtil.failWithLocalName("configuration.invalidElement",
                        reader);
                } else {
                    modelInfo.setNamespaceMappingRegistry(
                        parseNamespaceMappingRegistryInfo(reader));
                    gotNamespaceMappingRegistry = true;
                }
            } else {
                ParserUtil.failWithLocalName("configuration.invalidElement",
                    reader);
            }
        }
        return modelInfo;
    }
}

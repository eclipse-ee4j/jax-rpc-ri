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

import java.io.IOException;

import com.sun.xml.rpc.processor.config.ConfigurationException;
import com.sun.xml.rpc.processor.config.J2EEModelInfo;
import com.sun.xml.rpc.processor.config.ModelInfo;
import com.sun.xml.rpc.processor.modeler.j2ee.JaxRpcMappingXml;
import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class J2EEModelInfoParser extends ModelInfoParser {
    
    public J2EEModelInfoParser(ProcessorEnvironment env) {
        super(env);
    }

    public ModelInfo parse(XMLReader reader) {
        J2EEModelInfo modelInfo = new J2EEModelInfo();
        modelInfo.setJavaPackageName("package_ignored");
        String location = ParserUtil.getMandatoryNonEmptyAttribute(reader,
            Constants.ATTR_WSDL_LOCATION);
        modelInfo.setLocation(location);
        String mapping = ParserUtil.getMandatoryNonEmptyAttribute(reader,
            Constants.ATTR_LOCATION);
        try {
            modelInfo.setJaxRcpMappingXml(new JaxRpcMappingXml(mapping));
        } catch (IOException e) {
            throw new ConfigurationException(
                "configuration.nestedConfigurationError", 
                new LocalizableExceptionAdapter(e));            
        }

        return modelInfo;
    }
}

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

package com.sun.xml.rpc.processor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sun.xml.rpc.processor.ProcessorAction;
import com.sun.xml.rpc.processor.ProcessorOptions;
import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.model.exporter.ModelExporter;

/**
 * This class writes out a Model as an XML document.
 *
 * @author JAX-RPC Development Team
 */
public class XMLModelWriter implements ProcessorAction {
    
    public XMLModelWriter(File file) throws FileNotFoundException {
        exporter = new ModelExporter(new FileOutputStream(file));
    }
    
    public void perform(Model model, Configuration config, Properties options) {
        
        /* set the target version specified using -source switch or
         * default if not specified
         */
        model.setSource(options.getProperty(
            ProcessorOptions.JAXRPC_SOURCE_VERSION));
        exporter.doExport(model);
    }
    
    private ModelExporter exporter;
}


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

package com.sun.xml.rpc.processor.modeler.modelfile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.sun.xml.rpc.processor.ProcessorOptions;
import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.model.ModelException;
import com.sun.xml.rpc.processor.model.exporter.ModelImporter;
import com.sun.xml.rpc.processor.modeler.Modeler;
import com.sun.xml.rpc.processor.modeler.ModelerException;
import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ModelFileModeler implements Modeler {
    
    public ModelFileModeler(ModelFileModelInfo modelInfo, Properties options) {
        _modelInfo = modelInfo;
        _options = options;
        _messageFactory = new LocalizableMessageFactory(
            "com.sun.xml.rpc.resources.modeler");
        _env = (ProcessorEnvironment)modelInfo.getParent().getEnvironment();
    }
    
    public Model buildModel() {
        try {
            URL url = null;
            try {
                url = new URL(_modelInfo.getLocation());
            } catch (MalformedURLException e) {
                url = new File(_modelInfo.getLocation()).toURL();
            }
            
            InputStream is = url.openStream();
            ModelImporter im = new ModelImporter(url.openStream());
            Model model = im.doImport();
            
            /* set the target version (-source). If its null,
             * then don't set the value, it would have been already
             * set to default target.
             */
            if(model.getSource() != null) {
                _options.setProperty(ProcessorOptions.JAXRPC_SOURCE_VERSION,
                    model.getSource());
            }
            return model;
        } catch (IOException e) {
            throw new ModelerException(new LocalizableExceptionAdapter(e));
        } catch (ModelException e) {
            throw new ModelerException(e);
        }
    }
    
    private ModelFileModelInfo _modelInfo;
    private Properties _options;
    private LocalizableMessageFactory _messageFactory;
    private ProcessorEnvironment _env;
}

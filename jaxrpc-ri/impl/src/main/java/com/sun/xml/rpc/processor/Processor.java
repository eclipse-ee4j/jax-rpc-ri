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

package com.sun.xml.rpc.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.config.ModelInfo;
import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Processor implements com.sun.xml.rpc.spi.tools.Processor {
    
    public Processor(Configuration configuration, Properties options) {
        _configuration = configuration;
        _options = options;
        _actions = new ArrayList();
        
        // find the value of the "print stack traces" property
        _printStackTrace = Boolean.valueOf(_options.getProperty(
            ProcessorOptions.PRINT_STACK_TRACE_PROPERTY)).booleanValue();
        _env = (ProcessorEnvironment)_configuration.getEnvironment();
    }
    
    public void add(ProcessorAction action) {
        _actions.add(action);
    }
    
    public com.sun.xml.rpc.spi.model.Model getModel() {
        return _model;
    }
    
    public void run() {
        runModeler();
        if (_model != null) {
            runActions();
        }
    }
    
    public void runModeler() {
        try {
            ModelInfo modelInfo = (ModelInfo)_configuration.getModelInfo();
            if (modelInfo == null) {
                throw new ProcessorException("processor.missing.model");
            }
            
            _model = modelInfo.buildModel(_options);
            
        } catch (JAXRPCExceptionBase e) {
            if (_printStackTrace) {
                _env.printStackTrace(e);
            }
            _env.error(e);
        } catch (Exception e) {
            if (_printStackTrace) {
                _env.printStackTrace(e);
            }
            _env.error(new LocalizableExceptionAdapter(e));
        }
    }
    
    public void runActions() {
        try {
            if (_model == null) {

                // avoid reporting yet another error here
                return;
            }
            
            for (Iterator iter = _actions.iterator(); iter.hasNext();) {
                ProcessorAction action = (ProcessorAction) iter.next();
                action.perform(_model, _configuration, _options);
            }
        } catch (JAXRPCExceptionBase e) {
            if (_printStackTrace) {
                _env.printStackTrace(e);
            }
            _env.error(e);
        } catch (Exception e) {
            if (_printStackTrace) {
                _env.printStackTrace(e);
            }
            _env.error(new LocalizableExceptionAdapter(e));
        }
    }
    
    private Properties _options;
    private Configuration _configuration;
    private List _actions;
    private Model _model;
    private boolean _printStackTrace;
    private ProcessorEnvironment _env;
}

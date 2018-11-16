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

import com.sun.xml.rpc.processor.util.ProcessorEnvironment;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Configuration implements com.sun.xml.rpc.spi.tools.Configuration {

    public Configuration(com.sun.xml.rpc.spi.tools.ProcessorEnvironment env) {
        _env = (ProcessorEnvironment)env;
    }

    public com.sun.xml.rpc.spi.tools.ModelInfo getModelInfo() {
        return _modelInfo;
    }

    public void setModelInfo(com.sun.xml.rpc.spi.tools.ModelInfo i) {
        _modelInfo = (ModelInfo)i;
        _modelInfo.setParent(this);
    }
    
    public com.sun.xml.rpc.spi.tools.ProcessorEnvironment getEnvironment() {
        return _env;
    }
    
    private ProcessorEnvironment _env;
    private ModelInfo _modelInfo;
}

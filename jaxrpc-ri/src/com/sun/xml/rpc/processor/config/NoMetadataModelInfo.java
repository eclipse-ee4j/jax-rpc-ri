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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.modeler.Modeler;
import com.sun.xml.rpc.processor.modeler.nometadata.NoMetadataModeler;

/**
 *
 * @author JAX-RPC Development Team
 */
public class NoMetadataModelInfo extends ModelInfo
    implements com.sun.xml.rpc.spi.tools.NoMetadataModelInfo {

    public NoMetadataModelInfo() {}

    protected Modeler getModeler(Properties options) {
        return new NoMetadataModeler(this, options);
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String s) {
        _location = s;
    }

    public String getServiceInterfaceName() {
	return _serviceInterfaceName;
    }
    
    public void setServiceInterfaceName(String s) {
	_serviceInterfaceName = s;
    }
    
    public String getInterfaceName() {
	return _interfaceName;
    }
    
    public void setInterfaceName(String s) {
	_interfaceName = s;
    }

    public String getServantName() {
	return _servantName;
    }
    
    public void setServantName(String s) {
	_servantName = s;
    }
    
    public QName getServiceName() {
	return _serviceName;
    }
    
    public void setServiceName(QName n) {
	_serviceName = n;
    }
    
    public QName getPortName() {
	return _portName;
    }
    
    public void setPortName(QName n) {
	_portName = n;
    }
    
    private String _location;
    private String _serviceInterfaceName;
    private String _interfaceName;
    private String _servantName;
    private QName _serviceName;
    private QName _portName;
}

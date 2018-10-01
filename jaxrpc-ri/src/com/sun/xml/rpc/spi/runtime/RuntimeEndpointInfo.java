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

package com.sun.xml.rpc.spi.runtime;

import javax.xml.namespace.QName;

/**
 * This class is implemented by 
 * com.sun.xml.rpc.server.http.RuntimeEndpointInfo
 */
public interface RuntimeEndpointInfo {
    public void setRemoteInterface(Class klass);
    public void setImplementationClass(Class klass);
    public void setTieClass(Class klass);
    public void setName(String s);
    public void setDeployed(boolean b);
    public void setPortName(QName n);
    public void setServiceName(QName n);
    public void setUrlPattern(String s);
    public Class getTieClass();
    public Class getRemoteInterface();
    public Class getImplementationClass();
}

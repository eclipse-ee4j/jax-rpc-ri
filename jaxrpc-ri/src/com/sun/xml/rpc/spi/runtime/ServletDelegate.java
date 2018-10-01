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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A delegate for the JAX-RPC dispatcher servlet.
 * <p>
 * This interface is implemented by 
 * com.sun.xml.rpc.server.http.ServletDelegate
 *
 */
public interface ServletDelegate {
    public void init(ServletConfig servletConfig) throws ServletException;
    public void destroy();
    public void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException;
    public void registerEndpointUrlPattern(RuntimeEndpointInfo info);
    public void setSecondDelegate(ServletSecondDelegate delegate);
    public void setSystemHandlerDelegate(SystemHandlerDelegate systemHandlerDelegate);
}

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

package com.sun.xml.rpc.server.http;

import java.rmi.Remote;

import javax.servlet.ServletContext;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.server.ServiceLifecycle;

import com.sun.xml.rpc.server.Tie;

/**
 * 
 * @author JAX-RPC Development Team
 */
public class Implementor implements com.sun.xml.rpc.spi.runtime.Implementor {

    public Implementor(
        ServletContext servletContext,
        com.sun.xml.rpc.spi.runtime.Tie tie) {
        this.tie = (Tie) tie;
        this.context = new ServletEndpointContextImpl(servletContext, this);
    }

    public com.sun.xml.rpc.spi.runtime.Tie getTie() {
        return tie;
    }

    public Remote getTarget() {
        return tie.getTarget();
    }

    public ServletEndpointContextImpl getContext() {
        return context;
    }

    public void init() throws ServiceException {
        Remote servant = tie.getTarget();
        if (servant != null && (servant instanceof ServiceLifecycle)) {
            ((ServiceLifecycle) servant).init(context);
        }
    }

    public void destroy() {
        Remote servant = tie.getTarget();
        if (servant != null && (servant instanceof ServiceLifecycle)) {
            ((ServiceLifecycle) servant).destroy();
        }
        tie.destroy();
    }

    private Tie tie;
    private Remote endpoint;
    private ServletEndpointContextImpl context;
}

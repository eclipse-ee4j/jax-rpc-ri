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

import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.server.ServletEndpointContext;

/**
 * An implementation of the javax.xml.rpc.server.ServletEndpointContext interface.
 *
 * @author JAX-RPC Development Team
 */
public class ServletEndpointContextImpl implements ServletEndpointContext {

    public ServletEndpointContextImpl(ServletContext c) {
        this(c, null);
    }

    public ServletEndpointContextImpl(ServletContext c, Implementor i) {
        servletContext = c;
        implementor = i;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public Principal getUserPrincipal() {
        return getHttpServletRequest().getUserPrincipal();
    }

    public HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    public MessageContext getMessageContext() {
        return (MessageContext) messageContext.get();
    }

    public void setMessageContext(MessageContext c) {
        messageContext.set(c);
    }

    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) httpRequest.get();
    }

    public void setHttpServletRequest(HttpServletRequest r) {
        httpRequest.set(r);
    }

    public void clear() {
        setMessageContext(null);
        setHttpServletRequest(null);
    }

    public boolean isUserInRole(String role) {
        return ((HttpServletRequest) httpRequest.get()).isUserInRole(role);
    }

    public Implementor getImplementor() {
        return implementor;
    }

    private ServletContext servletContext;
    private Implementor implementor;

    // thread local data
    private static ThreadLocal messageContext = new ThreadLocal();
    private static ThreadLocal httpRequest = new ThreadLocal();
}

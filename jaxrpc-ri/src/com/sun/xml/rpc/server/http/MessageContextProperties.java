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

/**
 *
 * @author JAX-RPC Development Team
 */
public interface MessageContextProperties {

    public static final String SERVLET_CONTEXT =
        "com.sun.xml.rpc.server.http.ServletContext";
    public static final String HTTP_SERVLET_REQUEST =
        "com.sun.xml.rpc.server.http.HttpServletRequest";
    public static final String HTTP_SERVLET_RESPONSE =
        "com.sun.xml.rpc.server.http.HttpServletResponse";
    public static final String IMPLEMENTOR =
        "com.sun.xml.rpc.server.http.Implementor";
    public static final String ONE_WAY_OPERATION =
        "com.sun.xml.rpc.server.OneWayOperation";
    public static final String CLIENT_BAD_REQUEST =
        "com.sun.xml.rpc.server.http.ClientBadRequest";
    public static final String CLIENT_INVALID_CONTENT_TYPE =
        "com.sun.xml.rpc.server.http.ClientInvalidContentType";        
}

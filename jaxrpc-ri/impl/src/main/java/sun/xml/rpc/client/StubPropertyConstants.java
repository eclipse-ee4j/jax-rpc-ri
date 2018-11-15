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

package com.sun.xml.rpc.client;

import javax.xml.rpc.Stub;

/**
 * @author JAX-RPC Development Team
 */
public interface StubPropertyConstants {
    public static final String SERVICEIMPL_NAME = "serviceImplementationName";
    public static final String USERNAME_PROPERTY = Stub.USERNAME_PROPERTY;
    public static final String PASSWORD_PROPERTY = Stub.PASSWORD_PROPERTY;
    public static final String ENDPOINT_ADDRESS_PROPERTY =
        Stub.ENDPOINT_ADDRESS_PROPERTY;
    public static final String SESSION_MAINTAIN_PROPERTY =
        Stub.SESSION_MAINTAIN_PROPERTY;
    public static final String OPERATION_STYLE_PROPERTY =
        "com.sun.client.OperationStyleProperty";
    public static final String ENCODING_STYLE_PROPERTY =
        " com.sun.client.EncodingStyleProperty";
    public static final String HOSTNAME_VERIFICATION_PROPERTY =
        "com.sun.xml.rpc.client.http.HostnameVerificationProperty";
    public static final String HTTP_COOKIE_JAR =
        "com.sun.xml.rpc.client.http.CookieJar";
    public static final String SECURITY_CONTEXT =
        "com.sun.xml.rpc.security.context";
    public static final String HTTP_STATUS_CODE =
        "com.sun.xml.rpc.client.http.HTTPStatusCode";
    /*public static final String ATTACHMENT_CONTEXT =
        "com.sun.xml.rpc.attachment.AttachmentContext";*/
    public static final String REDIRECT_REQUEST_PROPERTY =
        "com.sun.xml.rpc.client.http.RedirectRequestProperty";
    public static final String SET_ATTACHMENT_PROPERTY =
        "com.sun.xml.rpc.attachment.SetAttachmentContext";
    public static final String GET_ATTACHMENT_PROPERTY =
        "com.sun.xml.rpc.attachment.GetAttachmentContext";
    
    // A string-valued property "none", "pessimistic" and "optimistic"
    // Used for Fast Infoset content negotiation
    public static final String CONTENT_NEGOTIATION_PROPERTY =
        "com.sun.xml.rpc.client.ContentNegotiation";
}

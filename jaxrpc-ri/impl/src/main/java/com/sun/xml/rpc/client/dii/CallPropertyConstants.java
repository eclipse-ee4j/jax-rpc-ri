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

package com.sun.xml.rpc.client.dii;

import javax.xml.rpc.Call;

import com.sun.xml.rpc.client.StubPropertyConstants;

/**
 * @author JAX-RPC Development Team
 */
public interface CallPropertyConstants {
    public static final String USERNAME_PROPERTY = Call.USERNAME_PROPERTY;
    public static final String PASSWORD_PROPERTY = Call.PASSWORD_PROPERTY;
    public static final String ENDPOINT_ADDRESS_PROPERTY =
        "javax.xml.rpc.endpoint";
    public static final String OPERATION_STYLE_PROPERTY =
        Call.OPERATION_STYLE_PROPERTY;
    public static final String SOAPACTION_USE_PROPERTY =
        Call.SOAPACTION_USE_PROPERTY;
    public static final String SOAPACTION_URI_PROPERTY =
        Call.SOAPACTION_URI_PROPERTY;
    public static final String SESSION_MAINTAIN_PROPERTY =
        Call.SESSION_MAINTAIN_PROPERTY;
    public static final String ENCODING_STYLE_PROPERTY =
        Call.ENCODINGSTYLE_URI_PROPERTY;
    public static final String HTTP_COOKIE_JAR =
        StubPropertyConstants.HTTP_COOKIE_JAR;
    public static final String RPC_LITERAL_RESPONSE_QNAME =
        "com.sun.xml.rpc.client.responseQName";
    public static final String HOSTNAME_VERIFICATION_PROPERTY =
        StubPropertyConstants.HOSTNAME_VERIFICATION_PROPERTY;
    public static final String REDIRECT_REQUEST_PROPERTY =
        StubPropertyConstants.REDIRECT_REQUEST_PROPERTY;
    public static final String SECURITY_CONTEXT =
        StubPropertyConstants.SECURITY_CONTEXT;
    public static final String HTTP_STATUS_CODE =
        StubPropertyConstants.HTTP_STATUS_CODE;
    /*public static final String ATTACHMENT_CONTEXT =
        StubPropertyConstants.ATTACHMENT_CONTEXT;*/
    public static final String SET_ATTACHMENT_PROPERTY =
        "com.sun.xml.rpc.attachment.SetAttachmentContext";
    public static final String GET_ATTACHMENT_PROPERTY =
        "com.sun.xml.rpc.attachment.GetAttachmentContext";
    
    // A string-valued property "none", "pessimistic" and "optimistic"
    // Used for Fast Infoset content negotiation
    public static final String CONTENT_NEGOTIATION_PROPERTY =
        StubPropertyConstants.CONTENT_NEGOTIATION_PROPERTY;
}

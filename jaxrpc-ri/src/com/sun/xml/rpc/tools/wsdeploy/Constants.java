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

package com.sun.xml.rpc.tools.wsdeploy;

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public interface Constants {
    public static final String NS_DD = "http://java.sun.com/xml/ns/jax-rpc/ri/dd";
    
    public static final QName QNAME_WEB_SERVICES            = new QName(NS_DD, "webServices");
    public static final QName QNAME_ENDPOINT                = new QName(NS_DD, "endpoint");
    public static final QName QNAME_HANDLER_CHAINS          = new QName(NS_DD, "handlerChains");
    public static final QName QNAME_CHAIN                   = new QName(NS_DD, "chain");
    public static final QName QNAME_HANDLER                 = new QName(NS_DD, "handler");
    public static final QName QNAME_PROPERTY                = new QName(NS_DD, "property");
    public static final QName QNAME_ENDPOINT_MAPPING        = new QName(NS_DD, "endpointMapping");
    public static final QName QNAME_ENDPOINT_CLIENT_MAPPING = new QName(NS_DD, "client");
    
    public static final String ATTR_SERVICE               = "service";
    public static final String ATTR_VERSION               = "version";
    public static final String ATTR_TARGET_NAMESPACE_BASE = "targetNamespaceBase";
    public static final String ATTR_TYPE_NAMESPACE_BASE   = "typeNamespaceBase";
    public static final String ATTR_URL_PATTERN_BASE      = "urlPatternBase";
    public static final String ATTR_NAME                  = "name";
    public static final String ATTR_DISPLAY_NAME          = "displayName";
    public static final String ATTR_DESCRIPTION           = "description";
    public static final String ATTR_INTERFACE             = "interface";
    public static final String ATTR_IMPLEMENTATION        = "implementation";
    public static final String ATTR_PORT                  = "port";
    public static final String ATTR_WSDL                  = "wsdl";
    public static final String ATTR_MODEL                 = "model";
    public static final String ATTR_ENDPOINT_NAME         = "endpointName";
    public static final String ATTR_URL_PATTERN           = "urlPattern";
    public static final String ATTR_RUN_AT                = "runAt";
    public static final String ATTR_ROLES                 = "roles";
    public static final String ATTR_CLASS_NAME            = "className";
    public static final String ATTR_HEADERS               = "headers";
    public static final String ATTR_VALUE                 = "value";
    
    public static final String ATTRVALUE_VERSION_1_0 = "1.0";
    public static final String ATTRVALUE_CLIENT      = "client";
    public static final String ATTRVALUE_SERVER      = "server";
    public static final int    ENDPOINTLIST_INDEX    = 0;
}

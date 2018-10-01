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

package com.sun.xml.rpc.client.dii.webservice.parser;

import javax.xml.namespace.QName;

/**
 * @author JAX-RPC Development Team
 */
public interface Constants {
    public static final String NS_NAME =
        "http://java.sun.com/xml/ns/jax-rpc/ri/client";

    public static final QName QNAME_CLIENT =
        new QName(NS_NAME, "webServicesClient");
    public static final QName QNAME_SERVICE = new QName(NS_NAME, "service");

    public static final QName QNAME_PROPERTY = new QName(NS_NAME, "property");

    public static final String ATTR_WSDL_LOCATION = "wsdlLocation";
    public static final String ATTR_MODEL = "model";

    public static final String ATTR_NAME = "name";
    public static final String ATTR_VALUE = "value";

    public static final String ATTRVALUE_VERSION_1_0 = "1.0";
    public static final String ATTRVALUE_CLIENT = "client";
    public static final String ATTRVALUE_SERVER = "server";

}

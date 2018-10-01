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

package com.sun.xml.rpc.wsdl.document;

import javax.xml.namespace.QName;

/**
 * Interface defining WSDL-related constants.
 *
 * @author JAX-RPC Development Team
 */
public interface WSDLConstants {

    // namespace URIs
    public static String NS_XMLNS = "http://www.w3.org/2000/xmlns/";
    public static String NS_WSDL = "http://schemas.xmlsoap.org/wsdl/";

    // QNames
    public static QName QNAME_BINDING = new QName(NS_WSDL, "binding");
    public static QName QNAME_DEFINITIONS = new QName(NS_WSDL, "definitions");
    public static QName QNAME_DOCUMENTATION =
        new QName(NS_WSDL, "documentation");
    public static QName QNAME_FAULT = new QName(NS_WSDL, "fault");
    public static QName QNAME_IMPORT = new QName(NS_WSDL, "import");
    public static QName QNAME_INPUT = new QName(NS_WSDL, "input");
    public static QName QNAME_MESSAGE = new QName(NS_WSDL, "message");
    public static QName QNAME_OPERATION = new QName(NS_WSDL, "operation");
    public static QName QNAME_OUTPUT = new QName(NS_WSDL, "output");
    public static QName QNAME_PART = new QName(NS_WSDL, "part");
    public static QName QNAME_PORT = new QName(NS_WSDL, "port");
    public static QName QNAME_PORT_TYPE = new QName(NS_WSDL, "portType");
    public static QName QNAME_SERVICE = new QName(NS_WSDL, "service");
    public static QName QNAME_TYPES = new QName(NS_WSDL, "types");

    public static QName QNAME_ATTR_ARRAY_TYPE = new QName(NS_WSDL, "arrayType");
}

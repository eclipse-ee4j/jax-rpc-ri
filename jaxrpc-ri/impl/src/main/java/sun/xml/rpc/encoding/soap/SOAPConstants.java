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

package com.sun.xml.rpc.encoding.soap;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.soap.streaming.SOAPNamespaceConstants;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SOAPConstants implements com.sun.xml.rpc.wsdl.document.soap.SOAPConstants,
    com.sun.xml.rpc.spi.runtime.SOAPConstants {

    public static final String URI_HTTP = SOAPNamespaceConstants.TRANSPORT_HTTP;

    public static final QName QNAME_ENCODING_ARRAY = QNAME_TYPE_ARRAY;
    public static final QName QNAME_ENCODING_ARRAYTYPE = QNAME_ATTR_ARRAY_TYPE;
    public static final QName QNAME_ENCODING_BASE64 = QNAME_TYPE_BASE64;
    public static final QName QNAME_ENVELOPE_ENCODINGSTYLE = new QName(URI_ENVELOPE, "encodingStyle");

    public final static QName QNAME_SOAP_FAULT             = new QName(URI_ENVELOPE, "Fault");
    public final static QName FAULT_CODE_CLIENT            = new QName(URI_ENVELOPE, "Client");
    public final static QName FAULT_CODE_MUST_UNDERSTAND   = new QName(URI_ENVELOPE, "MustUnderstand");

    public final static QName FAULT_CODE_VERSION_MISMATCH  = new QName(URI_ENVELOPE, "VersionMismatch");
    public final static QName FAULT_CODE_DATA_ENCODING_UNKNOWN = new QName(URI_ENVELOPE, "DataEncodingUnknown");
    public final static QName FAULT_CODE_PROCEDURE_NOT_PRESENT = new QName(URI_ENVELOPE, "ProcedureNotPresent");
    public final static QName FAULT_CODE_BAD_ARGUMENTS      = new QName(URI_ENVELOPE, "BadArguments");
}

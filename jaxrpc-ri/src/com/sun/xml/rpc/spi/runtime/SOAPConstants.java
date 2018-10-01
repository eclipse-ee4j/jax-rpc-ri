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

import javax.xml.namespace.QName;

/**
 * This class is implemented by 
 * com.sun.xml.rpc.encoding.soap.SOAPConstants
 */
public interface SOAPConstants
	extends com.sun.xml.rpc.spi.tools.SOAPConstants {

	public static final String URI_ENCODING = NS_SOAP_ENCODING;
	public final static QName FAULT_CODE_SERVER =
		new QName(URI_ENVELOPE, "Server");

	// FAULT_CODE_CLIENT  This is needed
	// during error processing on an HTTP POST.  
	static final QName FAULT_CODE_CLIENT =
		new QName(SOAPConstants.URI_ENVELOPE, "Client");

	//
	// Internal MessageContextProperties that should be exposed :
	//

	// Used to set http servlet response object so that TieBase
	// will correctly flush response code for one-way operations.
	static final String HTTP_SERVLET_RESPONSE =
		"com.sun.xml.rpc.server.http.HttpServletResponse";

	// Used to detect ONE_WAY_OPERATION so reply can be skipped
	// in HTTP POST post-invocation processing
	static final String ONE_WAY_OPERATION =
		"com.sun.xml.rpc.server.OneWayOperation";

	// Another internal property used in reply processing.
	static final String CLIENT_BAD_REQUEST =
		"com.sun.xml.rpc.server.http.ClientBadRequest";
}

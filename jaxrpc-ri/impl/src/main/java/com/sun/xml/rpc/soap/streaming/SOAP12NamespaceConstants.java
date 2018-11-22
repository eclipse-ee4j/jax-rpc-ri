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

package com.sun.xml.rpc.soap.streaming;

/**
 * @author JAX-RPC Development Team
 */
public class SOAP12NamespaceConstants {
	public static final String ENVELOPE =
		"http://www.w3.org/2002/06/soap-envelope";
	public static final String ENCODING =
		"http://www.w3.org/2002/06/soap-encoding";
	public static final String SOAP_RPC = "http://www.w3.org/2002/06/soap-rpc";
	public static final String XSD = "http://www.w3.org/2001/XMLSchema";
	public static final String XSI =
		"http://www.w3.org/2001/XMLSchema-instance";
	public static final String TRANSPORT_HTTP =
		"http://www.w3.org/2002/06/soap/bindings/HTTP/";
	public static final String ACTOR_NEXT =
		"http://www.w3.org/2002/06/soap-envelope/role/next";
	public static final String SOAP_UPGRADE =
		"http://www.w3.org/2002/06/soap-upgrade";

	public static final String TAG_ENVELOPE = "Envelope";
	public static final String TAG_HEADER = "Header";
	public static final String TAG_BODY = "Body";
	public static final String TAG_RESULT = "result";

	public static final String ATTR_ACTOR = "role";
	public static final String ATTR_MUST_UNDERSTAND = "mustUnderstand";
	public static final String ATTR_MISUNDERSTOOD = "missUnderstood";
	public static final String ATTR_ENCODING_STYLE = "encodingStyle";
}

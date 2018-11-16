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
public class SOAPNamespaceConstants
	implements com.sun.xml.rpc.spi.tools.SOAPNamespaceConstants {
	//    public static final String ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
	public static final String ENCODING =
		"http://schemas.xmlsoap.org/soap/encoding/";
	public static final String XSD = "http://www.w3.org/2001/XMLSchema";
	public static final String XSI =
		"http://www.w3.org/2001/XMLSchema-instance";
	public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
	public static final String TRANSPORT_HTTP =
		"http://schemas.xmlsoap.org/soap/http/";
	public static final String ACTOR_NEXT =
		"http://schemas.xmlsoap.org/soap/actor/next";

	public static final String TAG_ENVELOPE = "Envelope";
	public static final String TAG_HEADER = "Header";
	public static final String TAG_BODY = "Body";

	public static final String ATTR_ACTOR = "actor";
	public static final String ATTR_MUST_UNDERSTAND = "mustUnderstand";
	public static final String ATTR_ENCODING_STYLE = "encodingStyle";
}

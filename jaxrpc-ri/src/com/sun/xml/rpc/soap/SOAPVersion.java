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

package com.sun.xml.rpc.soap;

/**
 * @author JAX-RPC Development Team
 */

public class SOAPVersion {

	private final String version;

	private SOAPVersion(String ver) {
		this.version = ver;
	}

	public String toString() {
		return this.version;
	}

	public boolean equals(String strVersion) {
		return version.equals(strVersion);
	}

	public static final SOAPVersion SOAP_11 = new SOAPVersion("soap1.1");
	public static final SOAPVersion SOAP_12 = new SOAPVersion("soap1.2");
}

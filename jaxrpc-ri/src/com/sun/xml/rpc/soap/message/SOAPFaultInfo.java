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

package com.sun.xml.rpc.soap.message;

import javax.xml.namespace.QName;

/**
 * @author JAX-RPC Development Team
 */
public class SOAPFaultInfo {

	// called SOAPFaultInfo to avoid clashes with the SOAPFault in JAXM
	public SOAPFaultInfo(QName code, String string, String actor) {
		this(code, string, actor, null);
	}

	public SOAPFaultInfo(
		QName code,
		String string,
		String actor,
		Object detail) {
		this.code = code;
		this.string = string;
		this.actor = actor;
		this.detail = detail;
	}

	public QName getCode() {
		return code;
	}

	public void setCode(QName code) {
		this.code = code;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	private QName code;
	private String string;
	private String actor;
	private Object detail;
}

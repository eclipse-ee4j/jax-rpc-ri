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

import com.sun.xml.rpc.encoding.JAXRPCSerializer;

/**
 * @author JAX-RPC Development Team
 */
public class SOAPBlockInfo {

	public SOAPBlockInfo(QName name) {
		_name = name;
	}

	public QName getName() {
		return _name;
	}

	public Object getValue() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}

	public JAXRPCSerializer getSerializer() {
		return _serializer;
	}

	public void setSerializer(JAXRPCSerializer s) {
		_serializer = s;
	}

	private QName _name;
	private Object _value;
	private JAXRPCSerializer _serializer;
}

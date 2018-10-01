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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPMessage;

import com.sun.xml.rpc.util.NullIterator;

/**
 * The internal representation of a SOAP message.
 * 
 * @author JAX-RPC Development Team
 */
public class InternalSOAPMessage {

	public static final int NO_OPERATION = -1;

	public InternalSOAPMessage(SOAPMessage message) {
		_message = message;
		_operationCode = NO_OPERATION;
	}

	public SOAPMessage getMessage() {
		return _message;
	}

	public void add(SOAPHeaderBlockInfo headerInfo) {
		if (headerInfo != null) {
			if (_headers == null) {
				_headers = new ArrayList();
			}
			_headers.add(headerInfo);
		}
	}

	public Iterator headers() {
		if (_headers == null) {
			return NullIterator.getInstance();
		} else {
			return _headers.iterator();
		}
	}

	public SOAPBlockInfo getBody() {
		return _body;
	}

	public void setBody(SOAPBlockInfo body) {
		_body = body;
	}

	public int getOperationCode() {
		return _operationCode;
	}

	public void setOperationCode(int i) {
		_operationCode = i;
	}

	public boolean isHeaderNotUnderstood() {
		return _headerNotUnderstood;
	}

	public void setHeaderNotUnderstood(boolean b) {
		_headerNotUnderstood = b;
	}

	public boolean isFailure() {
		return _failure;
	}

	public void setFailure(boolean b) {
		_failure = b;
	}

	private SOAPMessage _message;
	private List _headers;
	private SOAPBlockInfo _body;
	private int _operationCode;
	private boolean _failure;
	private boolean _headerNotUnderstood;
}

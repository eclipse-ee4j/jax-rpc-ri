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

package com.sun.xml.rpc.util.xml;

/**
 * @author JAX-RPC Development Team
 */
public final class CDATA {

	public CDATA(String text) {
		_text = text;
	}

	public String getText() {
		return _text;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof CDATA))
			return false;

		CDATA cdata = (CDATA) obj;

		return this._text.equals(cdata._text);
	}

	public int hashCode() {
		return _text.hashCode();
	}

	private String _text;
}

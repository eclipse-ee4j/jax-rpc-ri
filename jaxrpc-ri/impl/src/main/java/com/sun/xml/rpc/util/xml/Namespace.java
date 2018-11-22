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
public final class Namespace {

	private Namespace(String prefix, String uri) {
		_prefix = prefix;
		_uri = uri;
	}

	public String getPrefix() {
		return _prefix;
	}

	public String getURI() {
		return _uri;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Namespace))
			return false;

		Namespace namespace = (Namespace) obj;

		return this._prefix.equals(namespace._prefix)
			&& this._uri.equals(namespace._uri);
	}

	public int hashCode() {
		return _prefix.hashCode() ^ _uri.hashCode();
	}

	private String _prefix;
	private String _uri;

	////////

	public static Namespace getNamespace(String prefix, String uri) {
		// TODO - modify this to cache namespace objects, possibly with weak references to collect them

		if (prefix == null) {
			prefix = "";
		}
		if (uri == null) {
			uri = "";
		}

		return new Namespace(prefix, uri);
	}
}

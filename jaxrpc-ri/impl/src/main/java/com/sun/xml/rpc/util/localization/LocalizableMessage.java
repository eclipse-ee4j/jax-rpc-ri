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

package com.sun.xml.rpc.util.localization;

/**
 * @author JAX-RPC Development Team
 */
public class LocalizableMessage implements Localizable {

	protected String _bundlename;
	protected String _key;
	protected Object[] _args;

	public LocalizableMessage(String bundlename, String key) {
		this(bundlename, key, (Object[]) null);
	}

	public LocalizableMessage(String bundlename, String key, String arg) {
		this(bundlename, key, new Object[] { arg });
	}

	protected LocalizableMessage(
		String bundlename,
		String key,
		Object[] args) {
		_bundlename = bundlename;
		_key = key;
		_args = args;
	}

	public String getKey() {
		return _key;
	}

	public Object[] getArguments() {
		return _args;
	}

	public String getResourceBundleName() {
		return _bundlename;
	}
}

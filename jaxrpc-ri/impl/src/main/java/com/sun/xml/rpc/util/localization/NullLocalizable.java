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
 * NullLocalizable
 *
 * @author JAX-RPC Development Team
 */

public class NullLocalizable implements Localizable {
	protected static NullLocalizable instance = null;

	public NullLocalizable(String key) {
		_key = key;
	}

	public String getKey() {
		return _key;
	}
	public Object[] getArguments() {
		return null;
	}
	public String getResourceBundleName() {
		return "";
	}

	private String _key;

	public static NullLocalizable instance() {
		if (instance == null) {
			instance = new NullLocalizable(null);
		}
		return instance;
	}
}

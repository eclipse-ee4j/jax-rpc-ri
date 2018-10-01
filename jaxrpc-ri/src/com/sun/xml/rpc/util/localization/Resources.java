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

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author JAX-RPC Development Team
 */
public class Resources {

	private ResourceBundle _bundle;

	public Resources(String bundleName) throws MissingResourceException {
		_bundle = ResourceBundle.getBundle(bundleName);
	}

	public String getString(String key) {
		return getText(key, null);
	}

	public String getString(String key, String arg) {
		return getText(key, new String[] { arg });
	}

	public String getString(String key, String[] args) {
		return getText(key, args);
	}

	private String getText(String key, String[] args) {
		if (_bundle == null)
			return "";

		try {
			return MessageFormat.format(_bundle.getString(key), args);
		} catch (MissingResourceException e) {
			String msg = "Missing resource: key={0}";
			return MessageFormat.format(msg, new String[] { key });
		}
	}
}

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

package com.sun.xml.rpc.util;

import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.localization.Localizable;

/**
 * @author JAX-RPC Development Team
 */

public class HolderException extends JAXRPCExceptionBase {

	public HolderException(String key) {
		super(key);
	}

	public HolderException(String key, String arg) {
		super(key, arg);
	}

	public HolderException(String key, Localizable localizable) {
		super(key, localizable);
	}

	public HolderException(String key, Object[] args) {
		super(key, args);
	}

	public String getResourceBundleName() {
		return "com.sun.xml.rpc.resources.util";
	}
}

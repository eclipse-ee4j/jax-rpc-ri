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

/**
 * This interface holds version information for the whole JAX-RPC RI.
 *
 * @author JAX-RPC Development Team
 */

public interface Version {

	/**
	 * JAX-RPC RI product name
	 */
	public static final String PRODUCT_NAME = "JAX-RPC Standard Implementation";

	/**
	 * JAX-RPC RI version number
	 */
	public static final String VERSION_NUMBER = "1.1.3";

	/**
	 * JAX-RPC RI build number
	 */
	public static final String BUILD_NUMBER = "R2";
}

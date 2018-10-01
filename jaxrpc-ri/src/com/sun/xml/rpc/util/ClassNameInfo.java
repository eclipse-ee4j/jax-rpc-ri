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
 * @author JAX-RPC Development Team
 */

public final class ClassNameInfo {

	public static String getName(String className) {
		String qual = getQualifier(className);
		return qual != null
			? className.substring(qual.length() + 1)
			: className;
	}

	public static String getQualifier(String className) {
		int idot = className.indexOf(' ');
		if (idot <= 0)
			idot = className.length();
		else
			idot -= 1; // back up over previous dot
		int index = className.lastIndexOf('.', idot - 1);
		return (index < 0) ? null : className.substring(0, index);
	}

	public static String replaceInnerClassSym(String name) {
		return name.replace('$', '_');
	}
}

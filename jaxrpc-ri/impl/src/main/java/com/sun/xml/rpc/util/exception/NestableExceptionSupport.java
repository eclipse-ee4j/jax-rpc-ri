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

package com.sun.xml.rpc.util.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * NestableExceptionSupport
 *
 * @author JAX-RPC Development Team
 */

public class NestableExceptionSupport {
	protected Throwable cause = null;

	public NestableExceptionSupport() {
	}

	public NestableExceptionSupport(Throwable cause) {
		this.cause = cause;
	}

	public void printStackTrace() {
		//super.printStackTrace();
		if (cause != null) {
			System.err.println("\nCAUSE:\n");
			cause.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream s) {
		//super.printStackTrace(s);
		if (cause != null) {
			s.println("\nCAUSE:\n");
			cause.printStackTrace(s);
		}
	}

	public void printStackTrace(PrintWriter s) {
		//super.printStackTrace(s);
		if (cause != null) {
			s.println("\nCAUSE:\n");
			cause.printStackTrace(s);
		}
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
}

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

import com.sun.xml.rpc.util.localization.Localizable;
import com.sun.xml.rpc.util.localization.Localizer;
import com.sun.xml.rpc.util.localization.NullLocalizable;

/**
 * LocalizableExceptionAdapter
 *
 * @author JAX-RPC Development Team
 */
public class LocalizableExceptionAdapter
	extends Exception
	implements Localizable {
	protected Localizable localizablePart;
	protected Throwable nestedException;

	public LocalizableExceptionAdapter(Throwable nestedException) {
		this.nestedException = nestedException;
		if (nestedException instanceof Localizable) {
			localizablePart = (Localizable) nestedException;
		} else {
			localizablePart = new NullLocalizable(nestedException.toString());
		}
	}

	public String getKey() {
		return localizablePart.getKey();
	}

	public Object[] getArguments() {
		return localizablePart.getArguments();
	}

	public String getResourceBundleName() {
		return localizablePart.getResourceBundleName();
	}

	public String toString() {
		// for debug purposes only
		return nestedException.toString();
	}

	public String getLocalizedMessage() {
		if (nestedException == localizablePart) {
			Localizer localizer = new Localizer();
			return localizer.localize(localizablePart);
		} else {
			return nestedException.getLocalizedMessage();
		}
	}

	public String getMessage() {
		return getLocalizedMessage();
	}

	public Throwable getNestedException() {
		return nestedException;
	}

	public void printStackTrace() {
		nestedException.printStackTrace();
	}

	public void printStackTrace(PrintStream s) {
		nestedException.printStackTrace(s);
	}

	public void printStackTrace(PrintWriter s) {
		nestedException.printStackTrace(s);
	}
}

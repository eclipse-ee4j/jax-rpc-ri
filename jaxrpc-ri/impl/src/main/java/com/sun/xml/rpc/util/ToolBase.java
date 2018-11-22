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

import java.io.OutputStream;
import java.io.PrintStream;

import com.sun.xml.rpc.util.localization.Localizable;
import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
import com.sun.xml.rpc.util.localization.Localizer;

/**
 * A base class for command-line tools.
 *
 * @author JAX-RPC Development Team
 */
public abstract class ToolBase {

	public ToolBase(OutputStream out, String program) {
		this.out = out;
		this.program = program;
		initialize();
	}

	protected void initialize() {
		messageFactory = new LocalizableMessageFactory(getResourceBundleName());
		localizer = new Localizer();
	}

	public boolean run(String[] args) {
		if (!parseArguments(args)) {
			return false;
		}

		try {
			run();
			return wasSuccessful();
		} catch (Exception e) {
			if (e instanceof Localizable) {
				report((Localizable) e);
			} else {
				report(getMessage(getGenericErrorMessage(), e.toString()));
			}
			printStackTrace(e);
			return false;
		}
	}

	public boolean wasSuccessful() {
		return true;
	}

	protected abstract boolean parseArguments(String[] args);
	protected abstract void run() throws Exception;
	protected abstract String getGenericErrorMessage();
	protected abstract String getResourceBundleName();

	public void printStackTrace(Throwable t) {
		PrintStream outstream =
			out instanceof PrintStream
				? (PrintStream) out
				: new PrintStream(out, true);
		t.printStackTrace(outstream);
		outstream.flush();
	}

	protected void report(String msg) {
		PrintStream outstream =
			out instanceof PrintStream
				? (PrintStream) out
				: new PrintStream(out, true);
		outstream.println(msg);
		outstream.flush();
	}

	protected void report(Localizable msg) {
		report(localizer.localize(msg));
	}

	public Localizable getMessage(String key) {
		return getMessage(key, (Object[]) null);
	}

	public Localizable getMessage(String key, String arg) {
		return messageFactory.getMessage(key, new Object[] { arg });
	}

	public Localizable getMessage(String key, String arg1, String arg2) {
		return messageFactory.getMessage(key, new Object[] { arg1, arg2 });
	}

	public Localizable getMessage(
		String key,
		String arg1,
		String arg2,
		String arg3) {
		return messageFactory.getMessage(
			key,
			new Object[] { arg1, arg2, arg3 });
	}

	public Localizable getMessage(String key, Localizable localizable) {
		return messageFactory.getMessage(key, new Object[] { localizable });
	}

	public Localizable getMessage(String key, Object[] args) {
		return messageFactory.getMessage(key, args);
	}

	protected OutputStream out;
	protected String program;
	protected Localizer localizer;
	protected LocalizableMessageFactory messageFactory;

	protected final static String TRUE = "true";
	protected final static String FALSE = "false";

}

/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package photocatalog;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class LogOutputStream extends PrintStream {
	boolean autoFlush = false;

	public LogOutputStream(OutputStream out) {
		super(out);
		this.out = out;
	}

	public LogOutputStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		this.out = out;
		this.autoFlush = autoFlush;
	}

	public LogOutputStream reopen() throws FileNotFoundException {
		out = new FileOutputStream(System.getProperty("log.dir") +
				System.getProperty("file.separator") +
				System.getProperty("soap.msgs.file"), true);
		LogOutputStream log = new LogOutputStream(out, autoFlush);

		return log;
	}

	public void close() {
		super.close();
	}

}


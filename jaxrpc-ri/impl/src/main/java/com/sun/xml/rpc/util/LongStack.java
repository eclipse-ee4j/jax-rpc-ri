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
public final class LongStack {
	private long[] values = null;
	private int topOfStack = 0;

	public LongStack() {
		this(32);
	}

	public LongStack(int size) {
		values = new long[size];
	}

	public void push(long newValue) {
		resize();
		values[topOfStack] = newValue;
		++topOfStack;
	}

	public long pop() {
		--topOfStack;
		return values[topOfStack];
	}

	public long peek() {
		return values[topOfStack - 1];
	}

	private void resize() {
		if (topOfStack >= values.length) {
			long[] newValues = new long[values.length * 2];
			System.arraycopy(values, 0, newValues, 0, values.length);
			values = newValues;
		}
	}
}

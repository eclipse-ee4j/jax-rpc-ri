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
public final class IntegerArrayList {
	private int values[] = null;
	private int length = 0;

	public IntegerArrayList() {
		this(8);
	}
	
	public IntegerArrayList(int size) {
		values = new int[size];
	}

	public boolean add(int value) {
		resize();
		values[length++] = value;

		return true;
	}

	public int get(int index) {
		return values[index];
	}

	public void clear() {
		values = new int[length];
		length = 0;
	}

	public int[] toArray() {
		int[] array = new int[length];
		System.arraycopy(values, 0, array, 0, length);
		return array;
	}

	public int size() {
		return length;
	}
	
	private void resize() {
		if (length >= values.length) {
			int newValues[] = new int[values.length * 2];
			System.arraycopy(values, 0, newValues, 0, values.length);
			values = newValues;
		}
	}
}

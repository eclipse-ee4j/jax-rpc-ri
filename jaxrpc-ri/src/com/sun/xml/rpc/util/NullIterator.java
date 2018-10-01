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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An Iterator on an empty collection.
 *
 * @author JAX-RPC Development Team
 */
public final class NullIterator implements Iterator {

	public static NullIterator getInstance() {
		return _instance;
	}

	private static final NullIterator _instance = new NullIterator();

	private NullIterator() {
	}

	public boolean hasNext() {
		return false;
	}

	public Object next() {
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}

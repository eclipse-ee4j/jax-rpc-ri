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
import java.lang.UnsupportedOperationException;
import java.lang.IllegalStateException;

/**
 * An Iterator on a single element collection.
 *
 * @author JAX-RPC Development Team
 */
public class SingleElementIterator implements Iterator {
	protected boolean hasNext = false;
	protected Object element;

	public SingleElementIterator() {
	}
	
	public SingleElementIterator(Object element) {
		this.element = element;
		hasNext = true;
	}
	
	public boolean hasNext() {
		return hasNext;
	}
	
	public Object next() throws NoSuchElementException {
		if (!hasNext) {
			throw new NoSuchElementException("No elements left in SingleElementIterator next()");
		}
		hasNext = false;
		return element;
	}
	
	public void remove()
		throws UnsupportedOperationException, IllegalStateException {
		throw new UnsupportedOperationException("SingleElementIterator does not support remove()");
	}
}

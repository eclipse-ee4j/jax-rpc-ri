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

/**
 * <p> Combines two iterators into one. </p>
 *
 * @author JAX-RPC Development Team
 */

package com.sun.xml.rpc.util;

import java.util.Iterator;

public class CombinedIterator implements Iterator {
	protected Iterator currentIterator;
	protected Iterator secondIterator;

	public CombinedIterator(Iterator firstIterator, Iterator secondIterator) {
		this.currentIterator = firstIterator;
		this.secondIterator = secondIterator;
	}

	public boolean hasNext() {
		if (!currentIterator.hasNext()) {
			currentIterator = secondIterator;
		}
		return currentIterator.hasNext();
	}

	public Object next() {
		if (!currentIterator.hasNext()) {
			currentIterator = secondIterator;
		}
		return currentIterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}

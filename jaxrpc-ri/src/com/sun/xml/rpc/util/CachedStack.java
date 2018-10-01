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

import java.util.ArrayList;
import java.util.List;

/**
 * @author JAX-RPC Development Team
 */
public abstract class CachedStack {
	protected List elements = new ArrayList();
	protected int topOfStack = -1;

	protected abstract Object createObject() throws Exception;

	public void push() throws Exception {
		++topOfStack;
		if (elements.size() == topOfStack) {
			elements.add(topOfStack, createObject());
		}
	}

	public void pop() {
		if (topOfStack < 0) {
			throw new ArrayIndexOutOfBoundsException(topOfStack);
		}
		--topOfStack;
	}

	public Object peek() {
		if (topOfStack == -1) {
			return null;
		}
		return elements.get(topOfStack);
	}

	public int depth() {
		return topOfStack + 1;
	}
}

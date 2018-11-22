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

package com.sun.xml.rpc.streaming;

/**
 * <p> A stack of int-valued element IDs. </p>
 *
 * @author JAX-RPC Development Team
 */
public final class ElementIdStack {

    public ElementIdStack() {
        this(INITIAL_SIZE);
    }

    public ElementIdStack(int size) {
        _values = new int[size];
        reset();
    }

    public void reset() {
        _tos = 0;
        _nextElementId = 1;
    }
    
    public int getCurrent() {
        return _values[_tos - 1];
    }

    public int pushNext() {
        ensureCapacity();
        _values[_tos++] = _nextElementId;
        return _nextElementId++;
    }

    public int pop() {
        --_tos;
        return _values[_tos];
    }

    private void ensureCapacity() {
        if (_tos >= _values.length) {
            int[] newValues = new int[_values.length * 2];
            System.arraycopy(_values, 0, newValues, 0, _values.length);
            _values = newValues;
        }
    }

    private int[] _values;
    private int _tos;
    private int _nextElementId;

    private static final int INITIAL_SIZE = 32;
}

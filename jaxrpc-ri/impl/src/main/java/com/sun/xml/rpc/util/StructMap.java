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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p> A Map that keeps track of the order in which entries are made. The
 * <code>values()</code> method returns an unmodifiable List of the values
 * in the order in which they were added. A new method,
 * <code>keys()</code> has been added. It returns an unmodifiable List of the
 * keys in the order in which they were added. </p>
 *
 * @author JAX-RPC Development Team
 */
public class StructMap implements Map {
	protected HashMap map = new HashMap();
	protected ArrayList keys = new ArrayList();
	protected ArrayList values = new ArrayList();

	public int size() {
		return map.size();
	}
	public boolean isEmpty() {
		return map.isEmpty();
	}
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	public Object get(Object key) {
		return map.get(key);
	}
	public Object put(Object key, Object value) {
		keys.add(key);
		values.add(value);
		return map.put(key, value);
	}
	public Object remove(Object key) {
		Object value = map.get(key);
		keys.remove(key);
		values.remove(value);
		return map.remove(key);
	}
	public void putAll(Map t) {
		if (!(t instanceof StructMap))
			throw new IllegalArgumentException("Cannot putAll members of anything other than a StructMap");
		StructMap that = (StructMap) t;
		for (int i = 0; i < that.keys.size(); ++i) {
			put(that.keys.get(i), that.values.get(i));
		}
	}
	public void clear() {
		keys.clear();
		values.clear();
		map.clear();
	}
	public Set keySet() {
		return map.keySet();
	}
	public Collection values() {
		return Collections.unmodifiableList(values);
	}
	public Set entrySet() {
		return map.entrySet();
	}
	public boolean equals(Object o) {
		return map.equals(o);
	}
	public int hashCode() {
		return map.hashCode() ^ keys.hashCode() ^ values.hashCode();
	}

	// new
	public Collection keys() {
		return Collections.unmodifiableList(keys);
	}
	public void set(int index, Object key, Object value) {
		keys.set(index, key);
		values.set(index, value);
		map.put(key, value);
	}
	public void set(int index, Object value) {
		Object key = keys.get(index);
		values.set(index, value);
		map.put(key, value);
	}
}

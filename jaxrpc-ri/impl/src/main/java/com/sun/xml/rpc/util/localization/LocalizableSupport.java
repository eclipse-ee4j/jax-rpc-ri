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

package com.sun.xml.rpc.util.localization;

/**
 * @author JAX-RPC Development Team
 */
public class LocalizableSupport {
    protected String key;
    protected Object[] arguments;

    public LocalizableSupport(String key) {
        this(key, (Object[]) null);
    }

    public LocalizableSupport(String key, String argument) {
        this(key, new Object[] { argument });
    }

    public LocalizableSupport(String key, Localizable localizable) {
        this(key, new Object[] { localizable });
    }

    public LocalizableSupport(String key, Object[] arguments) {
        this.key = key;
        this.arguments = arguments;
    }

    public String getKey() {
        return key;
    }
    public Object[] getArguments() {
        return arguments;
    }

    //abstract public String getResourceBundleName();
}

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

package com.sun.xml.rpc.wsdl.framework;

import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.localization.Localizable;

/**
 * An exception signalling a parsing error.
 *
 * @author JAX-RPC Development Team
 */
public class ParseException extends JAXRPCExceptionBase {

    public ParseException(String key) {
        super(key);
    }

    public ParseException(String key, String arg) {
        super(key, arg);
    }

    public ParseException(String key, Localizable localizable) {
        super(key, localizable);
    }

    public ParseException(String key, String arg, Localizable localizable) {
        this(key, new Object[] { arg, localizable });
    }

    public ParseException(String key, Object[] args) {
        super(key, args);
    }

    public String getResourceBundleName() {
        return "com.sun.xml.rpc.resources.wsdl";
    }
}

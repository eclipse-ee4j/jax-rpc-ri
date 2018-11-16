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

package com.sun.xml.rpc.client;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.xml.rpc.ServiceException;

import com.sun.xml.rpc.util.exception.NestableExceptionSupport;
import com.sun.xml.rpc.util.localization.Localizable;
import com.sun.xml.rpc.util.localization.LocalizableSupport;
import com.sun.xml.rpc.util.localization.Localizer;

/**
 * @author JAX-RPC Development Team
 */
public class ServiceExceptionImpl
    extends ServiceException
    implements Localizable {
    protected LocalizableSupport localizablePart;
    protected NestableExceptionSupport nestablePart;

    public ServiceExceptionImpl() {
        nestablePart = new NestableExceptionSupport();
    }

    public ServiceExceptionImpl(String key) {
        this();
        localizablePart = new LocalizableSupport(key);
    }

    public ServiceExceptionImpl(String key, String arg) {
        this();
        localizablePart = new LocalizableSupport(key, arg);
    }

    public ServiceExceptionImpl(String key, Localizable localizable) {
        this(key, new Object[] { localizable });
    }

    public ServiceExceptionImpl(String key, Object[] args) {
        this();
        localizablePart = new LocalizableSupport(key, args);
        if (args != null && nestablePart.getCause() == null) {
            for (int i = 0; i < args.length; ++i) {
                if (args[i] instanceof Throwable) {
                    nestablePart.setCause((Throwable) args[i]);
                    break;
                }
            }
        }
    }

    public ServiceExceptionImpl(Localizable arg) {
        this("service.exception.nested", arg);
    }

    public String getResourceBundleName() {
        return "com.sun.xml.rpc.resources.dii";
    }

    public String getKey() {
        return localizablePart.getKey();
    }

    public Object[] getArguments() {
        return localizablePart.getArguments();
    }

    public String toString() {
        // for debug purposes only
        //return getClass().getName() + " (" + getKey() + ")";
        return getMessage();
    }

    public String getMessage() {
        Localizer localizer = new Localizer();
        return localizer.localize(this);
    }

    public Throwable getLinkedException() {
        return nestablePart.getCause();
    }

    public void printStackTrace() {
        super.printStackTrace();
        nestablePart.printStackTrace();
    }

    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        nestablePart.printStackTrace(s);
    }

    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        nestablePart.printStackTrace(s);
    }
}

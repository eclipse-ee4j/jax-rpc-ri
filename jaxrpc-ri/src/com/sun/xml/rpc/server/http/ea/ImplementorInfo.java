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

package com.sun.xml.rpc.server.http.ea;

import java.rmi.Remote;

import javax.servlet.ServletContext;

import com.sun.xml.rpc.server.Tie;
import com.sun.xml.rpc.server.http.Implementor;

/**
 * ImplementorInfo contains basic information about the implementor of a port.
 *
 * @author JAX-RPC Development Team
 */
public class ImplementorInfo {

    public ImplementorInfo(Class tieClass, Class servantClass) {
        _tieClass = tieClass;
        _servantClass = servantClass;
    }

    public Class getTieClass() {
        return _tieClass;
    }
    public Class getServantClass() {
        return _servantClass;
    }

    public Implementor createImplementor(ServletContext context)
        throws IllegalAccessException, InstantiationException {
        Tie tie = (Tie) _tieClass.newInstance();
        Remote servant = (Remote) _servantClass.newInstance();
        tie.setTarget(servant);
        return new Implementor(context, tie);
    }

    private Class _tieClass;
    private Class _servantClass;
}

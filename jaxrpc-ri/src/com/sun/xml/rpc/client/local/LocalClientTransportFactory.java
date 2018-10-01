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

package com.sun.xml.rpc.client.local;

import java.io.OutputStream;

import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportFactory;
import com.sun.xml.rpc.soap.message.Handler;

/**
 * @author JAX-RPC Development Team
 */
public class LocalClientTransportFactory implements ClientTransportFactory {

    //this class is used primarily for debugging purposes
    public LocalClientTransportFactory(Handler handler) {
        _handler = handler;
        _logStream = null;
    }

    public LocalClientTransportFactory(
        Handler handler,
        OutputStream logStream) {
        _handler = handler;
        _logStream = logStream;
    }

    public ClientTransport create() {
        return new LocalClientTransport(_handler, _logStream);
    }

    private Handler _handler;
    private OutputStream _logStream;
}

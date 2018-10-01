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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportException;
import com.sun.xml.rpc.soap.message.Handler;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
import com.sun.xml.rpc.util.localization.Localizable;

/**
 * @author JAX-RPC Development Team
 */
public class LocalClientTransport implements ClientTransport {

    //this class is used primarily for debugging purposes
    public LocalClientTransport(Handler handler) {
        _handler = handler;
    }

    public LocalClientTransport(Handler handler, OutputStream logStream) {
        _handler = handler;
        _logStream = logStream;
    }

    public void invoke(String endpoint, SOAPMessageContext context) {
        try {
            if (_logStream != null) {
                String s = "\n******************\nRequest\n";
                _logStream.write(s.getBytes());
                context.getMessage().writeTo(_logStream);
            }

            setSOAPMessageFromSAAJ(context);

            _handler.handle(context);

            setSOAPMessageFromSAAJ(context);            

            // set this because a sender cannot rely on it being set
            // automatically
            context.setFailure(false);

            if (_logStream != null) {
                String s = "\nResponse\n";
                _logStream.write(s.getBytes());
                context.getMessage().writeTo(_logStream);
                s = "\n******************\n\n";
                _logStream.write(s.getBytes());
            }
        } catch (Exception e) {
            if (e instanceof Localizable) {
                throw new ClientTransportException(
                    "local.client.failed",
                    (Localizable) e);
            } else {
                throw new ClientTransportException(
                    "local.client.failed",
                    new LocalizableExceptionAdapter(e));
            }
        }
    }

    /**
     * Set the SOAPMessage in the context using stream thru SAAJ, this is 
     * to closely match http behaviour
     * 
     * @param context
     */
    private void setSOAPMessageFromSAAJ(SOAPMessageContext context) throws Exception{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        context.getMessage().writeTo(os);

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

        SOAPMessage message =
            MessageFactory.newInstance().createMessage(
                context.getMessage().getMimeHeaders(),
                is);
        context.setMessage(message);
    }

    public void invokeOneWay(String endpoint, SOAPMessageContext context) {
        try {
            if (_logStream != null) {
                String s = "\n******************\nRequest\n";
                _logStream.write(s.getBytes());
                context.getMessage().writeTo(_logStream);
            }

            _handler.handle(context);

            // set this because a sender cannot rely on it being set
            // automatically
            context.setFailure(false);

        } catch (Exception e) {
            if (e instanceof Localizable) {
                throw new ClientTransportException(
                    "local.client.failed",
                    (Localizable) e);
            } else {
                throw new ClientTransportException(
                    "local.client.failed",
                    new LocalizableExceptionAdapter(e));
            }
        }
    }

    private Handler _handler;
    private OutputStream _logStream;
}

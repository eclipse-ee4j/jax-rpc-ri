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

import javax.xml.rpc.handler.HandlerChain;

import com.sun.xml.rpc.client.dii.BasicCall;
import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;

/**
 * <p> The internal state of an otherwise stateless StreamingSender. </p>
 *
 * @author JAX-RPC Development Team
 */
public class StreamingSenderState {

    public StreamingSenderState(SOAPMessageContext context,
        HandlerChain handlerChain, boolean useFastInfoset, 
        boolean acceptFastInfoset) 
    {
        _context = context;
        _context.setMessage(_context.createMessage(useFastInfoset, acceptFastInfoset));
        _handlerChain = handlerChain;
    }

    public SOAPMessageContext getMessageContext() {
        return _context;
    }

    public boolean isFailure() {
        return _context.isFailure();
    }

    public InternalSOAPMessage getRequest() {
        if (_request == null) {
            _request = new InternalSOAPMessage(_context.getMessage());
        }

        return _request;
    }

    public InternalSOAPMessage getResponse() {
        if (_response == null) {
            _response = new InternalSOAPMessage(_context.getMessage());
            _response.setOperationCode(getRequest().getOperationCode());
        }

        return _response;
    }

    public HandlerChain getHandlerChain() {
        return _handlerChain;
    }

    public BasicCall getCall() {
        return _call;
    }
    
    public void setCall(BasicCall call) {
        _call = call;
    }
    
    private SOAPMessageContext _context;
    private InternalSOAPMessage _request;
    private InternalSOAPMessage _response;
    private HandlerChain _handlerChain;
    private BasicCall _call;
}

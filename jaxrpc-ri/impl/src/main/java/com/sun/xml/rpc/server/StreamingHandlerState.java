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

package com.sun.xml.rpc.server;

import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;


/**
 * The internal state of an otherwise stateless StreamingHandler. 
 *
 * @author JAX-RPC Development Team
 */
public class StreamingHandlerState {
        
    public StreamingHandlerState(SOAPMessageContext context) {
        _context = context;
        _request = new InternalSOAPMessage(_context.getMessage());
        _response = null;
    }

    public boolean isFastInfoset() {
        return _context.isFastInfoset();
    }
    
    public boolean acceptFastInfoset() {
        return _context.acceptFastInfoset();
    }
    
    public SOAPMessageContext getMessageContext() {
        return _context;
    }

    public boolean isFailure() {
        if (_response == null) {
            return false;
        } else {
            return _response.isFailure();
        }
    }

    public InternalSOAPMessage getRequest() {
        return _request;
    }

    public InternalSOAPMessage getResponse() {
        if (_response == null) {
            // Create FI response if accepted by client
            _response = new InternalSOAPMessage(
                _context.createMessage(_context.acceptFastInfoset(), 
                                       _context.acceptFastInfoset())); 
        }

        return _response;
    }

    public void setResponse(InternalSOAPMessage msg) {
        _response = msg;
    }

    public InternalSOAPMessage resetResponse() {
        _response = null;
        return getResponse();
    }

    private SOAPMessageContext _context;
    private InternalSOAPMessage _request;
    private InternalSOAPMessage _response;

    public static final int CALL_NO_HANDLERS = -1;
    public static final int CALL_FAULT_HANDLERS = 0;
    public static final int CALL_RESPONSE_HANDLERS = 1;

    int handlerFlag = CALL_RESPONSE_HANDLERS;
    
    public void setHandlerFlag(int handlerFlag) {
        this.handlerFlag = handlerFlag;
    }

    public int getHandlerFlag() {
        return handlerFlag;
    }
    
}

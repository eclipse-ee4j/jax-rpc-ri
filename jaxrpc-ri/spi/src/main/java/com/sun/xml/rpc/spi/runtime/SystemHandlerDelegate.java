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

package com.sun.xml.rpc.spi.runtime;

import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;

/**
 * The methods of this interface are invoked by the JAXRPCServletDelegate of
 * on the path to web sevice endpoints deployed as servlets.
 *
 * NOTE: The methods of this interface may also be called on the client side of
 * jaxrpc invocations, although at this time, we have not decided from
 * where such invocations will be made.
 *
 * @author Ron Monzillo
 */

public interface SystemHandlerDelegate {

   /**
    * The processRequest method is invoked with an object that 
    * implements com.sun.xml.rpc.spi.runtime.SOAPMessageContext.
    * <p>
    * When this method is called by the JAXRPCServletDelegate
    * (on the server side of jaxrpc servlet container invocation processing)
    * it must be called just before the call to implementor.getTie().handle(),
    * and at the time of the request message and the following properties 
    * must have been set on the SOAPMessageContext.
    * <p>
    * com.sun.xml.rpc.server.http.MessageContextProperties.IMPLEMENTOR
    * <br>
    * This property must be set to the com.sun.xml.rpc.spi.runtime.Implementor 
    * object corresponding to the target endpoint.
    *
    * NOTE: I'd like us to be able to hang the ServletAuthContext off the Implementor.
    *
    * <p>
    * com.sun.xml.rpc.server.http.MessageContextProperties.HTTP_SERVLET_REQUEST
    * <br>
    * This property must be
    * set to the javax.servlet.http.HttpServletRequest object containing the 
    * JAXRPC invocation.
    * <p>
    * com.sun.xml.rpc.server.http.MessageContextProperties.HTTP_SERVLET_RESPONSE
    * <br>
    * This property must be
    * set to the javax.servlet.http.HttpServletResponse object corresponding to
    * the JAXRPC invocation.
    * <p>
    * com.sun.xml.rpc.server.MessageContextProperties.HTTP_SERVLET_CONTEXT
    * <br>
    * This property must be
    * set to the javax.servlet.ServletContext object corresponding to web application
    * in which the JAXRPC servlet is running.
    * @param messageContext the SOAPMessageContext object containing the request
    * message and the properties described above.
    * @return true if processing by the delegate was such that the caller
    * should continue with its normal message processing. Returns false if the
    * processing by the delegate resulted in the messageContext containing a response
    * message that should be returned without the caller proceding to its normal
    * message processing. 
    * @throws java.lang.RuntimeException when the processing by the delegate failed,
    * without yielding a response message. In this case, the expectation is that
    * the caller will return a HTTP layer response code reporting that an internal
    * error occured.
    */
    public boolean processRequest(SOAPMessageContext messageContext);

   /**
    * The processResponse method is invoked with an object that 
    * implements com.sun.xml.rpc.spi.runtime.SOAPMessageContext.
    * <p>
    * When this method is called by the JAXRPCServletDelegate
    * (on the server side of jaxrpc servlet container invocation processing)
    * it must be called just just after the call to implementor.getTie().handle().
    * In the special case where the handle method throws an exception, the
    * processResponse message must not be called.
    * <p>
    * The SOAPMessageContext passed to the processRequest and handle messages is
    * passed to the processResponse method.
    * @throws java.lang.RuntimeException when the processing by the delegate failed,
    * in which case the caller is expected to return an HTTP layer 
    * response code reporting that an internal error occured.
    */
    public void processResponse(SOAPMessageContext messageContext);
}

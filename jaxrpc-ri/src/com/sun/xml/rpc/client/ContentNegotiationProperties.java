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

import java.util.Map;

import javax.xml.rpc.JAXRPCException;

public class ContentNegotiationProperties {
    
    static public void initFromSystemProperties(Map props)
        throws JAXRPCException 
    {
        String value =
                System.getProperty(StubPropertyConstants.CONTENT_NEGOTIATION_PROPERTY);
        
        if (value == null) {            
            props.put(StubPropertyConstants.CONTENT_NEGOTIATION_PROPERTY,
                      "none");      // FI is off by default
        } 
        else if (value.equals("none") || value.equals("pessimistic")
                    || value.equals("optimistic")) {
            props.put(StubPropertyConstants.CONTENT_NEGOTIATION_PROPERTY,
                    value.intern());
        } 
        else {
            throw new JAXRPCException("Illegal value '" + value + "' for property " +
                    StubPropertyConstants.CONTENT_NEGOTIATION_PROPERTY);
        }
    }
}

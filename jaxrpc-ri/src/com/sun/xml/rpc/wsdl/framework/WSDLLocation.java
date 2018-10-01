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

/**    
 *
 * Maintains wsdl:location context. This is used with 
 * ParserContext, where one each WSDL being imported its location is pushed, this will be used
 * latter to resolve relative imports of schema in SchemaParser.
 *
 * @author JAX-RPC Development Team
 */

package com.sun.xml.rpc.wsdl.framework;

public class WSDLLocation {
    WSDLLocation() {
        reset();
    }

    public void push() {
        int max = contexts.length;
        idPos++;
        if (idPos >= max) {
            LocationContext newContexts[] = new LocationContext[max * 2];
            System.arraycopy(contexts, 0, newContexts, 0, max);
            max *= 2;
            contexts = newContexts;
        }
        currentContext = contexts[idPos];
        if (currentContext == null) {
            contexts[idPos] = currentContext = new LocationContext();
        }
        if (idPos > 0) {
            currentContext.setParent(contexts[idPos - 1]);
        }

    }

    public void pop() {
        idPos--;
        if (idPos >= 0) {
            currentContext = contexts[idPos];
        }
    }

    public void reset() {
        contexts = new LocationContext[32];
        idPos = 0;
        contexts[idPos] = currentContext = new LocationContext();
    }

    public String getLocation() {
        return currentContext.getLocation();
    }

    public void setLocation(String loc) {
        currentContext.setLocation(loc);
    }

    private LocationContext[] contexts;
    private int idPos;
    private LocationContext currentContext;

    // LocationContext - inner class
    private static class LocationContext {
        void setLocation(String loc) {
            location = loc;
        }

        String getLocation() {
            return location;
        }

        void setParent(LocationContext parent) {
            parentLocation = parent;
        }

        private String location;
        private LocationContext parentLocation;
    }
}

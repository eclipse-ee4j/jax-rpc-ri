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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the delegate of the ServletDelegate, which allows some
 * implementation of the ServletDelegate to be overwritten.  Though
 * it screams for a better name.  ServletDelegateDelegate??
 * <p>
 * S1AS will extend this class provide its implementation of
 * the ServletDelegate behavior.
 */
public abstract class ServletSecondDelegate {

    public ServletSecondDelegate() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        //no op
    }

    /**
     * This method should be called after ServletDelegate.init()
     * is done.  Any initialization needed by the second delegate
     * should be done by overriding this method, i.e. the implementation
     * of ServletDelegate should call _secondDelegate.postInit()
     * at the end of its init() call.
     * @see ServletDelegate
     */
    public void postInit(ServletConfig config) throws ServletException {
        //no op
    }

    public void warnMissingContextInformation() {
        // context info not used within j2ee integration, so override
        // this method to prevent warning message
    }

    public ImplementorCache createImplementorCache(ServletConfig config) {
        return null;
    }
}

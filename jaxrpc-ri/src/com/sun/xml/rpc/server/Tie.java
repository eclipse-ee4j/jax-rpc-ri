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

import java.rmi.Remote;

/** Tie interface supports delegation mechanism for the implementation
 *  of RPC-based service. In the delegation approach, an implementation 
 *  class implements the methods defined in the Remote interface. Tie 
 *  instance delegates the incoming RPC call to the target 
 *  implementation object.
 *
 * @author JAX-RPC Development Team
 */
public interface Tie extends com.sun.xml.rpc.spi.runtime.Tie {

    /** Signals the Tie that it's about to be disposed of, giving
     *  it a chance to release any resources it might hold.
    **/
    public void destroy();

    /** Sets the target service implementation object (that 
     *  implements java.rmi.Remote interface) for this Tie 
     *  instance.
    **/

    /** Gets the target service implementation object (that 
     *  implements java.rmi.Remote interface) for this Tie 
     *  instance.
    **/
    public Remote getTarget();
}

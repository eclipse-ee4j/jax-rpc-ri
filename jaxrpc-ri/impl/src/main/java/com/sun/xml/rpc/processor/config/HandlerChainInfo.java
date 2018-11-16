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

package com.sun.xml.rpc.processor.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author JAX-RPC Development Team
 */
public class HandlerChainInfo
    implements com.sun.xml.rpc.spi.tools.HandlerChainInfo {

    public HandlerChainInfo() {
        handlers = new ArrayList();
        roles = new HashSet();
    }

    public void add(HandlerInfo i) {
        handlers.add(i);
    }

    public Iterator getHandlers() {
        return handlers.iterator();
    }

    public int getHandlersCount() {
        return handlers.size();
    }

    /* serialization */
    public List getHandlersList() {
        return handlers;
    }

    /* serialization */
    public void setHandlersList(List l) {
        handlers = l;
    }
    
    public void addRole(String s) {
        roles.add(s);
    }

    public Set getRoles() {
        return roles;
    }
    
    /* serialization */
    public void setRoles(Set s) {
        roles = s;
    }

    private List handlers;
    private Set roles;
}

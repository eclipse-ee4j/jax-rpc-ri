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

package com.sun.xml.rpc.processor.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Response extends Message {

    public Response() {}

    public void addFaultBlock(Block b) {
        if (_faultBlocks.containsKey(b.getName())) {
            throw new ModelException("model.uniqueness");
        }
        _faultBlocks.put(b.getName(), b);
    }

    public Iterator getFaultBlocks() {
        return _faultBlocks.values().iterator();
    }

    public int getFaultBlockCount () {
        return _faultBlocks.size();
    }

    /* serialization */
    public Map getFaultBlocksMap() {
        return _faultBlocks;
    }
    
    public void setFaultBlocksMap(Map m) {
        _faultBlocks = m;
    }
    
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    private Map _faultBlocks = new HashMap();
}

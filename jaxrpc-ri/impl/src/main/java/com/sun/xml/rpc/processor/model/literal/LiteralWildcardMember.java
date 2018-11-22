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

package com.sun.xml.rpc.processor.model.literal;

import com.sun.xml.rpc.processor.model.java.JavaStructureMember;

/**
 *
 * @author JAX-RPC Development Team
 */
public class LiteralWildcardMember extends LiteralElementMember {
    
    public LiteralWildcardMember() {}
    
    public LiteralWildcardMember(LiteralType type) {
        this(type, null);
    }
    
    public LiteralWildcardMember(LiteralType type,
        JavaStructureMember javaStructureMember) {
            
        super(null, type, javaStructureMember);
    }
    
    public String getExcludedNamespaceName() {
        return _excludedNamespaceName;
    }
    
    public void setExcludedNamespaceName(String s) {
        _excludedNamespaceName = s;
    }
    
    public boolean isWildcard() {
        return true;
    }
    
    private String _excludedNamespaceName;
}

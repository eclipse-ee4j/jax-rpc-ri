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

package com.sun.xml.rpc.processor.schema;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JAX-RPC Development Team
 */
public final class Symbol {
    
    public static final Symbol DEFAULT;
    public static final Symbol FIXED;
    
    public static final Symbol EXTENSION;
    public static final Symbol RESTRICTION;
    public static final Symbol SUBSTITUTION;
    
    public static final Symbol SKIP;
    public static final Symbol LAX;
    public static final Symbol STRICT;
    
    public static final Symbol KEY;
    public static final Symbol KEYREF;
    public static final Symbol UNIQUE;
    
    public static final Symbol ALL;
    public static final Symbol CHOICE;
    public static final Symbol SEQUENCE;
    
    public static final Symbol ATOMIC;
    public static final Symbol LIST;
    public static final Symbol UNION;
    
    public static Symbol named(String s) {
        return (Symbol) _symbolMap.get(s);
    }
    
    private static Map _symbolMap;
    
    static {
        _symbolMap = new HashMap();
        
        DEFAULT = new Symbol("default");
        FIXED = new Symbol("fixed");
        EXTENSION = new Symbol("extension");
        RESTRICTION = new Symbol("restriction");
        SUBSTITUTION = new Symbol("substitution");
        
        SKIP = new Symbol("skip");
        LAX = new Symbol("lax");
        STRICT = new Symbol("strict");
        
        KEY = new Symbol("key");
        KEYREF = new Symbol("keyref");
        UNIQUE = new Symbol("unique");
        
        ALL = new Symbol("all");
        CHOICE = new Symbol("choice");
        SEQUENCE = new Symbol("sequence");
        
        ATOMIC = new Symbol("atomic");
        LIST = new Symbol("list");
        UNION = new Symbol("union");
    }
    
    
    private Symbol(String s) {
        _name = s;
        _symbolMap.put(s, this);
    }
    
    public String getName() {
        return _name;
    }
    
    private String _name;
}

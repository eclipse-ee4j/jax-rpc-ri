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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaSimpleType;

/**
 *
 * @author JAX-RPC Development Team
 */
public class LiteralIDType extends LiteralType {
    
    public LiteralIDType() {}
    
    public LiteralIDType(QName name) {
        this(name, null);
    }
    
    public LiteralIDType(QName name, JavaSimpleType javaType) {
        this(name, javaType, false);
    }
    
    public LiteralIDType(QName name, JavaSimpleType javaType,
        boolean resolveIDREF) {
            
        super(name, javaType);
        this.resolveIDREF = resolveIDREF;
    }
    
    public void accept(LiteralTypeVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    public boolean getResolveIDREF() {
        return resolveIDREF;
    }
    
    public void setResolveIDREF(boolean resolveIDREF) {
        this.resolveIDREF = resolveIDREF;
    }
    
    //flag which represents command line -f:resolveidref flag.
    private boolean resolveIDREF;
    
}
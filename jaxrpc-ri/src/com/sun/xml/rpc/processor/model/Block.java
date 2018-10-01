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

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Block extends ModelObject {
    
    public static final int BODY   = 1;
    public static final int HEADER = 2;
    public static final int ATTACHMENT = 3;

    public Block() {}
    
    public Block(QName name) {
        this.name = name;
    }
    
    public Block(QName name, AbstractType type) {
        this.name = name;
        this.type = type;
    }
    
    public QName getName() {
        return name;
    }
    
    public void setName(QName n) {
        name = n;
    }
    
    public AbstractType getType() {
        return type;
    }
    
    public void setType(AbstractType type) {
        this.type = type;
    }
    
    public int getLocation() {
        return location;
    }
    
    public void setLocation(int i) {
        location = i;
    }
    
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private QName name;
    private AbstractType type;
    private int location;
}

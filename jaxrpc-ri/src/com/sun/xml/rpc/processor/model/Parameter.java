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

import com.sun.xml.rpc.processor.model.java.JavaParameter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Parameter extends ModelObject {
    
    public Parameter() {}
    
    public Parameter(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public JavaParameter getJavaParameter() {
        return javaParameter;
    }
    
    public void setJavaParameter(JavaParameter p) {
        javaParameter = p;
    }
    
    public AbstractType getType() {
        return type;
    }
    
    public void setType(AbstractType t) {
        type = t;
    }
    
    public Block getBlock() {
        return block;
    }
    
    public void setBlock(Block d) {
        block = d;
    }
    
    public Parameter getLinkedParameter() {
        return link;
    }
    
    public void setLinkedParameter(Parameter p) {
        link = p;
    }
    
    public boolean isEmbedded() {
        return embedded;
    }
    
    public void setEmbedded(boolean b) {
        embedded = b;
    }
    
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private String name;
    private JavaParameter javaParameter;
    private AbstractType type;
    private Block block;
    private Parameter link;
    private boolean embedded;
}

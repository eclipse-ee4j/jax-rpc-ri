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

package com.sun.xml.rpc.processor.model.java;

/**
 *
 * @author JAX-RPC Development Team
 */
public class JavaArrayType extends JavaType {
    
    public JavaArrayType() {
    }
    
    public JavaArrayType(String name) {
        super(name, true, "null");
    }
    
    public JavaArrayType(String name, String elementName,
        JavaType elementType) {
            
        super(name, true, "null");
        this.elementName = elementName;
        this.elementType = elementType;
    }
    
    public String getElementName() {
        return elementName;
    }
    
    public void setElementName(String name) {
        elementName = name;
    }
    
    public JavaType getElementType() {
        return elementType;
    }
    
    public void setElementType(JavaType type) {
        elementType = type;
    }
    
    // bug fix:4904604
    public String getSOAPArrayHolderName() {
        return soapArrayHolderName;
    }
    
    public void setSOAPArrayHolderName(String holderName) {
        this.soapArrayHolderName = holderName;
    }
    
    private String elementName;
    private JavaType elementType;
    private String soapArrayHolderName;
}

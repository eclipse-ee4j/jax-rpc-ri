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

package com.sun.xml.rpc.processor.model.soap;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaType;
import com.sun.xml.rpc.soap.SOAPVersion;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SOAPArrayType extends SOAPType {
    
    public SOAPArrayType() {}
    
    public SOAPArrayType(QName name) {
        this(name, SOAPVersion.SOAP_11);
    }
    
    public SOAPArrayType(QName name, SOAPVersion version) {
        this(name, null, null, null, version);
    }
    
    public SOAPArrayType(QName name, QName elementName, SOAPType elementType,
        JavaType javaType) {
            
        this(name, elementName, elementType, javaType, SOAPVersion.SOAP_11);
    }
    
    public SOAPArrayType(QName name, QName elementName, SOAPType elementType,
        JavaType javaType, SOAPVersion version) {
            
        super(name, javaType, version);
        this.elementName = elementName;
        this.elementType = elementType;
    }
    
    public QName getElementName() {
        return elementName;
    }
    
    public void setElementName(QName name) {
        elementName = name;
    }
    
    public SOAPType getElementType() {
        return elementType;
    }
    
    public void setElementType(SOAPType type) {
        elementType = type;
    }
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int i) {
        rank = i;
    }
    
    public int[] getSize() {
        return size;
    }
    
    public void setSize(int[] a) {
        size = a;
    }
    
    public void accept(SOAPTypeVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private QName elementName;
    private SOAPType elementType;
    private int rank;
    private int[] size;
}

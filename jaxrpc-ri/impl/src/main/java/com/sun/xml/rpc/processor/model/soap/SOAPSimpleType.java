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

import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
import com.sun.xml.rpc.soap.SOAPVersion;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SOAPSimpleType extends SOAPType {
    
    public SOAPSimpleType() {}
    
    public SOAPSimpleType(QName name) {
        this(name, null);
    }
    
    public SOAPSimpleType(QName name, JavaSimpleType javaType) {
        this(name, javaType, true);
    }
    
    public SOAPSimpleType(QName name, JavaSimpleType javaType,
        SOAPVersion version) {
        
        this(name, javaType, true, version);
    }
    
    public SOAPSimpleType(QName name, JavaSimpleType javaType,
        boolean referenceable) {
        
        this(name, javaType, referenceable, SOAPVersion.SOAP_11);
    }
    
    public SOAPSimpleType(QName name, JavaSimpleType javaType,
        boolean referenceable, SOAPVersion version) {
        
        super(name, javaType, version);
        this.referenceable = referenceable;
    }
    
    public QName getSchemaTypeRef() {
        return schemaTypeRef;
    }
    
    public void setSchemaTypeRef(QName n) {
        schemaTypeRef = n;
    }
    
    public boolean isReferenceable() {
        return referenceable;
    }
    
    public void setReferenceable(boolean b) {
        referenceable = b;
    }
    
    public void accept(SOAPTypeVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private QName schemaTypeRef;
    private boolean referenceable;
}

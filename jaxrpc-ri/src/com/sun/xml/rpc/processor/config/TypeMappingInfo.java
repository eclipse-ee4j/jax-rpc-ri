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

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public class TypeMappingInfo {

    public TypeMappingInfo() {}
    
    public TypeMappingInfo(String encodingStyle,
                           QName xmlType,
                           String javaTypeName,
                           String serializerFactoryName,
                           String deserializerFactoryName) {
            
        this.encodingStyle = encodingStyle;
        this.xmlType = xmlType;
        this.javaTypeName = javaTypeName;
        this.serializerFactoryName = serializerFactoryName;
        this.deserializerFactoryName = deserializerFactoryName;
    }

    public String getEncodingStyle() {
        return encodingStyle;
    }
    
    public void setEncodingStyle(String s) {
        encodingStyle = s;
    }

    public QName getXMLType() {
        return xmlType;
    }
    
    public void setXMLType(QName n) {
        xmlType = n;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }
    
    public void setJavaTypeName(String s) {
        javaTypeName = s;
    }

    public String getSerializerFactoryName() {
        return serializerFactoryName;
    }
    
    public void setSerializerFactoryName(String s) {
        serializerFactoryName = s;
    }

    public String getDeserializerFactoryName() {
        return deserializerFactoryName;
    }
    
    public void setDeserializerFactoryName(String s) {
        deserializerFactoryName = s;
    }

    private String encodingStyle;
    private QName xmlType;
    private String javaTypeName;
    private String serializerFactoryName;
    private String deserializerFactoryName;
}

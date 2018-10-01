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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 *
 * @author JAX-RPC Development Team
 */
public class TypeMappingRegistryInfo {

    public TypeMappingRegistryInfo() {
        xmlTypeMap = new HashMap();
        javaTypeMap = new HashMap();
        extraTypeNames = new HashSet();
        importedDocuments = new HashMap();
    }

    public void addMapping(TypeMappingInfo i) {
        xmlTypeMap.put(getKeyFor(i.getEncodingStyle(), i.getXMLType()), i);
        javaTypeMap.put(
            getKeyFor(i.getEncodingStyle(), i.getJavaTypeName()), i);
    }

    public TypeMappingInfo getTypeMappingInfo(String encodingStyle,
        QName xmlType) {
            
        return (TypeMappingInfo) xmlTypeMap.get(
            getKeyFor(encodingStyle, xmlType));
    }

    public TypeMappingInfo getTypeMappingInfo(String encodingStyle,
        String javaTypeName) {
            
        return (TypeMappingInfo) javaTypeMap.get(
            getKeyFor(encodingStyle, javaTypeName));
    }

    public Iterator getExtraTypeNames() {
        return extraTypeNames.iterator();
    }

    public void addExtraTypeName(String s) {
        extraTypeNames.add(s);
    }

    public int getExtraTypeNamesCount() {
        return extraTypeNames.size();
    }
    
    public Iterator getImportedDocuments() {
        return importedDocuments.values().iterator();
    }

    public ImportedDocumentInfo getImportedDocument(String namespace) {
        return (ImportedDocumentInfo) importedDocuments.get(namespace);
    }

    public void addImportedDocument(ImportedDocumentInfo i) {
        importedDocuments.put(i.getNamespace(), i);
    }

    private String getKeyFor(String s, QName q) {
        return getKeyFor(s, q.toString());
    }

    private String getKeyFor(String s, String t) {
        return s + "***" + t;
    }

    private Map xmlTypeMap;
    private Map javaTypeMap;
    private Set extraTypeNames;
    private Map importedDocuments;
}

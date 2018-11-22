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

package com.sun.xml.rpc.encoding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

/**
 * An implementation of the standard TypeMappingRegistry interface
 *
 * @author JAX-RPC Development Team
 */

public class TypeMappingRegistryImpl
    implements TypeMappingRegistry, SerializerConstants {
    protected Map mappings;
    protected TypeMapping defaultMapping;

    public TypeMappingRegistryImpl() {
        init();
    }

    protected void init() {
        mappings = new HashMap();
        defaultMapping = null;
    }

    public TypeMapping register(String namespaceURI, TypeMapping mapping) {
        if (mapping == null || namespaceURI == null) {
            throw new IllegalArgumentException();
        }

        if (!mappingSupportsEncoding(mapping, namespaceURI)) {
            throw new TypeMappingException(
                "typemapping.mappingDoesNotSupportEncoding",
                namespaceURI);
        }

        TypeMapping oldMapping = (TypeMapping) mappings.get(namespaceURI);
        mappings.put(namespaceURI, mapping);
        return oldMapping;
    }
    
    public void registerDefault(TypeMapping mapping) {
        defaultMapping = mapping;
    }
    
    public TypeMapping getDefaultTypeMapping() {
        return defaultMapping;
    }
    
    public String[] getRegisteredEncodingStyleURIs() {
        Set namespaceSet = mappings.keySet();
        return (String[]) namespaceSet.toArray(new String[namespaceSet.size()]);
    }
    
    public TypeMapping getTypeMapping(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException();
        }

        TypeMapping mapping = (TypeMapping) mappings.get(namespaceURI);
        if (mapping == null) {
            mapping = defaultMapping;
        }
        return mapping;
    }
    
    public TypeMapping createTypeMapping() {
        return new TypeMappingImpl();
    }
    
    public TypeMapping unregisterTypeMapping(String namespaceURI) {
        return (TypeMapping) mappings.remove(namespaceURI);
    }
    
    public boolean removeTypeMapping(TypeMapping mapping) {
        if (mapping == null) {
            throw new IllegalArgumentException("mapping cannot be null");
        }
        Set typeEntries = mappings.entrySet();
        Iterator eachEntry = typeEntries.iterator();
        boolean typeMappingFound = false;

        while (eachEntry.hasNext()) {
            Map.Entry currentEntry = (Map.Entry) eachEntry.next();
            if (mapping.equals(currentEntry.getValue())) {
                eachEntry.remove();
                typeMappingFound = true;
            }
        }
        return typeMappingFound;
    }
    
    public void clear() {
        mappings.clear();
    }
    
    protected static boolean mappingSupportsEncoding(
        TypeMapping mapping,
        String namespaceURI) {
        String[] encodings =
            ((TypeMappingImpl) mapping).getSupportedEncodings();
        for (int i = 0; i < encodings.length; ++i) {
            if (encodings[i].equals(namespaceURI)) {
                return true;
            }
        }

        return false;
    }
}

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;

/**
 *
 * @author JAX-RPC Development Team
 */ 
public class Model extends ModelObject
    implements com.sun.xml.rpc.spi.model.Model {
    
    public Model() {
    }
    
    public Model(QName name) {
        this.name = name;
    }
    
    public QName getName() {
        return name;
    }
    
    public void setName(QName n) {
        name = n;
    }
    
    public String getTargetNamespaceURI() {
        return targetNamespace;
    }
    
    public void setTargetNamespaceURI(String s) {
        targetNamespace = s;
    }
    
    public void addService(Service service) {
        if (servicesByName.containsKey(service.getName())) {
            throw new ModelException("model.uniqueness");
        }
        services.add(service);
        servicesByName.put(service.getName(), service);
    }
    
    public Iterator getServices() {
        return services.iterator();
    }
    
    public Service getServiceByName(QName name) {
        if (servicesByName.size() != services.size()) {
            initializeServicesByName();
        }
        return (Service)servicesByName.get(name);
    }
    
    /* serialization */
    public List getServicesList() {
        return services;
    }
    
    /* serialization */
    public void setServicesList(List l) {
        services = l;
    }
    
    private void initializeServicesByName() {
        servicesByName = new HashMap();
        if (services != null) {
            for (Iterator iter = services.iterator(); iter.hasNext();) {
                Service service = (Service)iter.next();
                if (service.getName() != null &&
                    servicesByName.containsKey(service.getName())) {
                        
                    throw new ModelException("model.uniqueness");
                }
                servicesByName.put(service.getName(), service);
            }
        }
    }
    
    public void addExtraType(AbstractType type) {
        extraTypes.add(type);
    }
    
    public Iterator getExtraTypes() {
        return extraTypes.iterator();
    }
    
    /* serialization */
    public Set getExtraTypesSet() {
        return extraTypes;
    }
    
    /* serialization */
    public void setExtraTypesSet(Set s) {
        extraTypes = s;
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
    
    /* serialization */
    public Map getImportedDocumentsMap() {
        return importedDocuments;
    }
    
    /* serialization */
    public void setImportedDocumentsMap(Map m) {
        importedDocuments = m;
    }
    
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    /**
     * @return
     */
    public String getSource() {
        return source;
    }
    
    /**
     * @param string
     */
    public void setSource(String string) {
        source = string;
    }
    
    private QName name;
    private String targetNamespace;
    private List services = new ArrayList();
    private Map servicesByName = new HashMap();
    private Set extraTypes = new HashSet();
    private Map importedDocuments = new HashMap();
    private String source;
}

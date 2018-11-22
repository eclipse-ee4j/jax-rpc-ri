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

package com.sun.xml.rpc.wsdl.document.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
import com.sun.xml.rpc.wsdl.framework.Defining;
import com.sun.xml.rpc.wsdl.framework.DuplicateEntityException;
import com.sun.xml.rpc.wsdl.framework.Extension;
import com.sun.xml.rpc.wsdl.framework.Kind;
import com.sun.xml.rpc.wsdl.framework.ValidationException;
import com.sun.xml.rpc.wsdl.parser.Constants;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Schema extends Extension implements Defining {

    public Schema(AbstractDocument document) {
        _document = document;
        _nsPrefixes = new HashMap();
        _definedEntities = new ArrayList();
    }

    public QName getElementName() {
        return SchemaConstants.QNAME_SCHEMA;
    }

    public SchemaElement getContent() {
        return _content;
    }

    public void setContent(SchemaElement entity) {
        _content = entity;
        _content.setSchema(this);
    }

    public void setTargetNamespaceURI(String uri) {
        _targetNamespaceURI = uri;
    }

    public String getTargetNamespaceURI() {
        return _targetNamespaceURI;
    }

    public void addPrefix(String prefix, String uri) {
        _nsPrefixes.put(prefix, uri);
    }

    public String getURIForPrefix(String prefix) {
        return (String) _nsPrefixes.get(prefix);
    }

    public Iterator prefixes() {
        return _nsPrefixes.keySet().iterator();
    }

    public void defineAllEntities() {
        if (_content == null) {
            throw new ValidationException(
                "validation.shouldNotHappen",
                "missing schema content");
        }

        for (Iterator iter = _content.children(); iter.hasNext();) {
            SchemaElement child = (SchemaElement) iter.next();
            if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
                QName name =
                    new QName(
                        _targetNamespaceURI,
                        child.getValueOfMandatoryAttribute(
                            Constants.ATTR_NAME));
                defineEntity(child, SchemaKinds.XSD_ATTRIBUTE, name);
            } else if (
                child.getQName().equals(
                    SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
                QName name =
                    new QName(
                        _targetNamespaceURI,
                        child.getValueOfMandatoryAttribute(
                            Constants.ATTR_NAME));
                defineEntity(child, SchemaKinds.XSD_ATTRIBUTE_GROUP, name);
            } else if (
                child.getQName().equals(SchemaConstants.QNAME_ELEMENT)) {
                QName name =
                    new QName(
                        _targetNamespaceURI,
                        child.getValueOfMandatoryAttribute(
                            Constants.ATTR_NAME));
                defineEntity(child, SchemaKinds.XSD_ELEMENT, name);
            } else if (child.getQName().equals(SchemaConstants.QNAME_GROUP)) {
                QName name =
                    new QName(
                        _targetNamespaceURI,
                        child.getValueOfMandatoryAttribute(
                            Constants.ATTR_NAME));
                defineEntity(child, SchemaKinds.XSD_GROUP, name);
            } else if (
                child.getQName().equals(SchemaConstants.QNAME_COMPLEX_TYPE)) {
                QName name =
                    new QName(
                        _targetNamespaceURI,
                        child.getValueOfMandatoryAttribute(
                            Constants.ATTR_NAME));
                defineEntity(child, SchemaKinds.XSD_TYPE, name);
            } else if (
                child.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE)) {
                QName name =
                    new QName(
                        _targetNamespaceURI,
                        child.getValueOfMandatoryAttribute(
                            Constants.ATTR_NAME));
                defineEntity(child, SchemaKinds.XSD_TYPE, name);
            }
        }
    }

    public void defineEntity(SchemaElement element, Kind kind, QName name) {
        /*SchemaEntity entity = new SchemaEntity(this, element, kind, name);
        _document.define(entity);
        _definedEntities.add(entity);*/
    	SchemaEntity entity = new SchemaEntity(this, element, kind, name);
        try{
            _document.define(entity);
        }catch(DuplicateEntityException e){            
            return;
        }
        _definedEntities.add(entity);
    }

    public Iterator definedEntities() {
        return _definedEntities.iterator();
    }

    public void validateThis() {
        if (_content == null) {
            throw new ValidationException(
                "validation.shouldNotHappen",
                "missing schema content");
        }
    }

    public String asString(QName name) {
        if (name.getNamespaceURI().equals("")) {
            return name.getLocalPart();
        } else {
            // look for a prefix
            for (Iterator iter = prefixes(); iter.hasNext();) {
                String prefix = (String) iter.next();
                if (prefix.equals(name.getNamespaceURI())) {
                    return prefix + ":" + name.getLocalPart();
                }
            }

            // not found
            return null;
        }
    }

    private AbstractDocument _document;
    private String _targetNamespaceURI;
    private SchemaElement _content;
    private List _definedEntities;
    private Map _nsPrefixes;
}

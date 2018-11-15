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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.WriterContext;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SchemaAttribute {

    public SchemaAttribute() {
    }

    public SchemaAttribute(String localName) {
        _localName = localName;
    }

    public String getNamespaceURI() {
        return _nsURI;
    }

    public void setNamespaceURI(String s) {
        _nsURI = s;
    }

    public String getLocalName() {
        return _localName;
    }

    public void setLocalName(String s) {
        _localName = s;
    }

    public QName getQName() {
        return new QName(_nsURI, _localName);
    }

    public String getValue() {
        if (_qnameValue != null) {
            if (_parent == null) {
                throw new IllegalStateException();
            } else {
                return _parent.asString(_qnameValue);
            }
        } else {
            return _value;
        }
    }

    public String getValue(WriterContext context) {
        if (_qnameValue != null) {
            return context.getQNameString(_qnameValue);
        } else {
            return _value;
        }
    }

    public void setValue(String s) {
        _value = s;
    }

    public void setValue(QName name) {
        _qnameValue = name;
    }

    public SchemaElement getParent() {
        return _parent;
    }

    public void setParent(SchemaElement e) {
        _parent = e;
    }

    private String _nsURI;
    private String _localName;
    private String _value;
    private QName _qnameValue;
    private SchemaElement _parent;
}

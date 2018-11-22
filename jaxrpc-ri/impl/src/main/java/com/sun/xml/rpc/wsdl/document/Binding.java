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

package com.sun.xml.rpc.wsdl.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
import com.sun.xml.rpc.wsdl.framework.Defining;
import com.sun.xml.rpc.wsdl.framework.Entity;
import com.sun.xml.rpc.wsdl.framework.EntityAction;
import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
import com.sun.xml.rpc.wsdl.framework.Extensible;
import com.sun.xml.rpc.wsdl.framework.Extension;
import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
import com.sun.xml.rpc.wsdl.framework.Kind;
import com.sun.xml.rpc.wsdl.framework.QNameAction;

/**
 * Entity corresponding to the "binding" WSDL element.
 *
 * @author JAX-RPC Development Team
 */
public class Binding extends GlobalEntity implements Extensible {

    public Binding(Defining defining) {
        super(defining);
        _operations = new ArrayList();
        _helper = new ExtensibilityHelper();
    }

    public void add(BindingOperation operation) {
        _operations.add(operation);
    }

    public Iterator operations() {
        return _operations.iterator();
    }

    public QName getPortType() {
        return _portType;
    }

    public void setPortType(QName n) {
        _portType = n;
    }

    public PortType resolvePortType(AbstractDocument document) {
        return (PortType) document.find(Kinds.PORT_TYPE, _portType);
    }

    public Kind getKind() {
        return Kinds.BINDING;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_BINDING;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        for (Iterator iter = _operations.iterator(); iter.hasNext();) {
            action.perform((Entity) iter.next());
        }
        _helper.withAllSubEntitiesDo(action);
    }

    public void withAllQNamesDo(QNameAction action) {
        super.withAllQNamesDo(action);

        if (_portType != null) {
            action.perform(_portType);
        }
    }

    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        super.withAllEntityReferencesDo(action);
        if (_portType != null) {
            action.perform(Kinds.PORT_TYPE, _portType);
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        //bug fix: 4947340, extensions should be the first element        
        _helper.accept(visitor);
        for (Iterator iter = _operations.iterator(); iter.hasNext();) {
            ((BindingOperation) iter.next()).accept(visitor);
        }
        visitor.postVisit(this);
    }

    public void validateThis() {
        if (getName() == null) {
            failValidation("validation.missingRequiredAttribute", "name");
        }
        if (_portType == null) {
            failValidation("validation.missingRequiredAttribute", "type");
        }
    }

    public void addExtension(Extension e) {
        _helper.addExtension(e);
    }

    public Iterator extensions() {
        return _helper.extensions();
    }

    private ExtensibilityHelper _helper;
    private Documentation _documentation;
    private QName _portType;
    private List _operations;
}

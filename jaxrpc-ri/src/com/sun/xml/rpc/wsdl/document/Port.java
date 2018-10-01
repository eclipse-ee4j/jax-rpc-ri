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

import java.util.Iterator;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
import com.sun.xml.rpc.wsdl.framework.Defining;
import com.sun.xml.rpc.wsdl.framework.EntityAction;
import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
import com.sun.xml.rpc.wsdl.framework.Extensible;
import com.sun.xml.rpc.wsdl.framework.Extension;
import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
import com.sun.xml.rpc.wsdl.framework.Kind;
import com.sun.xml.rpc.wsdl.framework.QNameAction;

/**
 * Entity corresponding to the "port" WSDL element.
 *
 * @author JAX-RPC Development Team
 */
public class Port extends GlobalEntity implements Extensible {

    public Port(Defining defining) {
        super(defining);
        _helper = new ExtensibilityHelper();
    }

    public Service getService() {
        return _service;
    }

    public void setService(Service s) {
        _service = s;
    }

    public QName getBinding() {
        return _binding;
    }

    public void setBinding(QName n) {
        _binding = n;
    }

    public Binding resolveBinding(AbstractDocument document) {
        return (Binding) document.find(Kinds.BINDING, _binding);
    }

    public Kind getKind() {
        return Kinds.PORT;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_PORT;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void withAllQNamesDo(QNameAction action) {
        super.withAllQNamesDo(action);

        if (_binding != null) {
            action.perform(_binding);
        }
    }

    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        super.withAllEntityReferencesDo(action);
        if (_binding != null) {
            action.perform(Kinds.BINDING, _binding);
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        _helper.accept(visitor);
        visitor.postVisit(this);
    }

    public void validateThis() {
        if (getName() == null) {
            failValidation("validation.missingRequiredAttribute", "name");
        }
        if (_binding == null) {
            failValidation("validation.missingRequiredAttribute", "binding");
        }
    }

    public void addExtension(Extension e) {
        _helper.addExtension(e);
    }

    public Iterator extensions() {
        return _helper.extensions();
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        _helper.withAllSubEntitiesDo(action);
    }

    private ExtensibilityHelper _helper;
    private Documentation _documentation;
    private Service _service;
    private QName _binding;
}

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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.Entity;
import com.sun.xml.rpc.wsdl.framework.EntityReferenceAction;
import com.sun.xml.rpc.wsdl.framework.Kind;
import com.sun.xml.rpc.wsdl.framework.QNameAction;

/**
 * Entity corresponding to a WSDL message part.
 *
 * @author JAX-RPC Development Team
 */
public class MessagePart extends Entity {

    public static final int SOAP_BODY_BINDING = 1;
    public static final int SOAP_HEADER_BINDING = 2;
    public static final int SOAP_HEADERFAULT_BINDING = 3;
    public static final int SOAP_FAULT_BINDING = 4;
    public static final int WSDL_MIME_BINDING = 5;
    
    public MessagePart() {
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public QName getDescriptor() {
        return _descriptor;
    }

    public void setDescriptor(QName n) {
        _descriptor = n;
    }

    public Kind getDescriptorKind() {
        return _descriptorKind;
    }

    public void setDescriptorKind(Kind k) {
        _descriptorKind = k;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_PART;
    }

    public int getBindingExtensibilityElementKind(){
        return _bindingKind;
    }
    
    public void setBindingExtensibilityElementKind(int kind) {
        _bindingKind = kind;
    }
    
    public void withAllQNamesDo(QNameAction action) {
        if (_descriptor != null) {
            action.perform(_descriptor);
        }
    }

    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        super.withAllEntityReferencesDo(action);
        if (_descriptor != null && _descriptorKind != null) {
            action.perform(_descriptorKind, _descriptor);
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    public void validateThis() {
        if (_descriptorKind == null || _descriptor == null) {
            failValidation("validation.missingRequiredProperty", "descriptor");
        }
    }

    private String _name;
    private QName _descriptor;
    private Kind _descriptorKind;
    private int _bindingKind;
}

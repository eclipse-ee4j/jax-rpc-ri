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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.Defining;
import com.sun.xml.rpc.wsdl.framework.Entity;
import com.sun.xml.rpc.wsdl.framework.EntityAction;
import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
import com.sun.xml.rpc.wsdl.framework.Kind;

/**
 * Entity corresponding to the "message" WSDL element.
 *
 * @author JAX-RPC Development Team
 */
public class Message extends GlobalEntity {

    public Message(Defining defining) {
        super(defining);
        _parts = new ArrayList();
        _partsByName = new HashMap();
    }

    public void add(MessagePart part) {

		/* Nagesh: Do not throw exception to support the wsdl where multiple header elements are 
		specified in the binding for the operation and the parts in the message referes to the same 
		element in the xml schema. */
        /* if (_partsByName.get(part.getName()) != null)
            throw new ValidationException(
                "validation.duplicateName",
                part.getName()); */
        _partsByName.put(part.getName(), part);
        _parts.add(part);
    }

    public Iterator parts() {
        return _parts.iterator();
    }

    public MessagePart getPart(String name) {
        return (MessagePart) _partsByName.get(name);
    }

    public int numParts() {
        return _parts.size();
    }

    public Kind getKind() {
        return Kinds.MESSAGE;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_MESSAGE;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        super.withAllSubEntitiesDo(action);

        for (Iterator iter = _parts.iterator(); iter.hasNext();) {
            action.perform((Entity) iter.next());
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        for (Iterator iter = _parts.iterator(); iter.hasNext();) {
            ((MessagePart) iter.next()).accept(visitor);
        }
        visitor.postVisit(this);
    }

    public void validateThis() {
        if (getName() == null) {
            failValidation("validation.missingRequiredAttribute", "name");
        }
    }

    private Documentation _documentation;
    private List _parts;
    private Map _partsByName;
}

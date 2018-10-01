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

package com.sun.xml.rpc.wsdl.document.mime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.framework.Entity;
import com.sun.xml.rpc.wsdl.framework.EntityAction;
import com.sun.xml.rpc.wsdl.framework.Extension;
import com.sun.xml.rpc.wsdl.framework.ExtensionVisitor;

/**
 * A MIME multipartRelated extension.
 *
 * @author JAX-RPC Development Team
 */
public class MIMEMultipartRelated extends Extension {

    public MIMEMultipartRelated() {
        _parts = new ArrayList();
    }

    public QName getElementName() {
        return MIMEConstants.QNAME_MULTIPART_RELATED;
    }

    public void add(MIMEPart part) {
        _parts.add(part);
    }

    public Iterator getParts() {
        return _parts.iterator();
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        super.withAllSubEntitiesDo(action);

        for (Iterator iter = _parts.iterator(); iter.hasNext();) {
            action.perform((Entity) iter.next());
        }
    }

    public void accept(ExtensionVisitor visitor) throws Exception {
        visitor.preVisit(this);
        visitor.postVisit(this);
    }

    public void validateThis() {
    }

    private List _parts;
}

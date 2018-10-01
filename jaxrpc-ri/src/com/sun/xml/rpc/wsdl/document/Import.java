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

/**
 * Entity corresponding to the "import" WSDL element.
 *
 * @author JAX-RPC Development Team
 */
public class Import
    extends Entity
    implements com.sun.xml.rpc.spi.tools.Import {

    public Import() {
    }

    public String getNamespace() {
        return _namespace;
    }

    public void setNamespace(String s) {
        _namespace = s;
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String s) {
        _location = s;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_IMPORT;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    public void validateThis() {
        if (_location == null) {
            failValidation("validation.missingRequiredAttribute", "location");
        }
        if (_namespace == null) {
            failValidation("validation.missingRequiredAttribute", "namespace");
        }
    }

    private Documentation _documentation;
    private String _location;
    private String _namespace;
}

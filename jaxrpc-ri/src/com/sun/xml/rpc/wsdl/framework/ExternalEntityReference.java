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

package com.sun.xml.rpc.wsdl.framework;

import javax.xml.namespace.QName;

/**
 * A reference to a globally known entity in a document.
 *
 * @author JAX-RPC Development Team
 */
public class ExternalEntityReference {

    public ExternalEntityReference(
        AbstractDocument document,
        Kind kind,
        QName name) {
        _document = document;
        _kind = kind;
        _name = name;
    }

    public AbstractDocument getDocument() {
        return _document;
    }

    public Kind getKind() {
        return _kind;
    }

    public QName getName() {
        return _name;
    }

    public GloballyKnown resolve() {
        return _document.find(_kind, _name);
    }

    private AbstractDocument _document;
    private Kind _kind;
    private QName _name;
}

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

package com.sun.xml.rpc.streaming;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.util.xml.CDATA;

/**
 * <p> A base class for XMLWriter implementations. </p>
 *
 * <p> It provides the implementation of some derived XMLWriter methods. </p>
 *
 * @author JAX-RPC Development Team
 */
public abstract class XMLWriterBase implements XMLWriter {

    public void startElement(String localName) {
        startElement(localName, "");
    }

    public void startElement(QName name) {
        startElement(name.getLocalPart(), name.getNamespaceURI());
    }

    public void startElement(QName name, String prefix) {
        startElement(name.getLocalPart(), name.getNamespaceURI(), prefix);
    }

    public void writeAttribute(String localName, String value) {
        writeAttribute(localName, "", value);
    }

    public void writeAttribute(QName name, String value) {
        writeAttribute(name.getLocalPart(), name.getNamespaceURI(), value);
    }

    public void writeAttributeUnquoted(QName name, String value) {
        writeAttributeUnquoted(
            name.getLocalPart(),
            name.getNamespaceURI(),
            value);
    }

    public void writeAttributeUnquoted(String localName, String value) {
        writeAttributeUnquoted(localName, "", value);
    }

    public abstract void writeChars(CDATA chars);

    public abstract void writeChars(String chars);

    public void writeComment(String comment) {
    }
}

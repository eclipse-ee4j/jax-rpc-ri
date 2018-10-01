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

/**
 * <p> XMLWriterUtil provides some utility methods intended to be used
 * in conjunction with a XMLWriter. </p>
 *
 * @see XMLWriter
 *
 * @author JAX-RPC Development Team
 */
public class XMLWriterUtil {

    private XMLWriterUtil() {
    }

    // sample method signature:
    // public static void foo(XMLWriter writer, args...);
    //

    public static String encodeQName(XMLWriter writer, QName qname) {
        // NOTE: Here it is assumed that we do not serialize using default
        // namespace declarations and therefore that writer.getPrefix will
        // never return ""

        String namespaceURI = qname.getNamespaceURI();
        String localPart = qname.getLocalPart();

        if (namespaceURI == null || namespaceURI.equals("")) {
            return localPart;
        } else {
            String prefix = writer.getPrefix(namespaceURI);
            if (prefix == null) {
                writer.writeNamespaceDeclaration(namespaceURI);
                prefix = writer.getPrefix(namespaceURI);
            }
            return prefix + ":" + localPart;
        }
    }
}

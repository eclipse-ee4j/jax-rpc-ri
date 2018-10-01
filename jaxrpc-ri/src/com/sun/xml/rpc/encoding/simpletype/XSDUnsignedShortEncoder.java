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

package com.sun.xml.rpc.encoding.simpletype;

import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class XSDUnsignedShortEncoder extends SimpleTypeEncoderBase {
    private static final SimpleTypeEncoder encoder =
        new XSDUnsignedShortEncoder();

    private static final int upperBound = 65535;

    private XSDUnsignedShortEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
        if (obj == null) {
            return null;
        }
        return ((Integer) obj).toString();
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
            
        if (str == null) {
            return null;
        }

        try {
            Integer value = Integer.valueOf(str);
            if (value < 0 || value > upperBound) {
                throw new com.sun.xml.rpc.encoding.DeserializationException(
                        "xsd.invalid.unsignedShort",
                        str);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new com.sun.xml.rpc.encoding.DeserializationException(
                    "xsd.invalid.unsignedShort",
                    str);
        }

    }

    public void writeValue(Object obj, XMLWriter writer) throws Exception {
        writer.writeCharsUnquoted(objectToString(obj, writer));
    }
}

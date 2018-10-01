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

import java.util.Calendar;

/**
 * Encoder for xsd:time. For this type returns java.util.Calendar object.
 *
 * @author JAX-RPC Development Team
 */

public class XSDTimeEncoder extends SimpleTypeEncoderBase {
    private static final SimpleTypeEncoder encoder = new XSDTimeEncoder();
    private static final String FORMAT = "%h:%m:%s%z";

    protected XSDTimeEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
        if (null == obj)
            return null;


        if(!(obj instanceof Calendar))    throw new IllegalArgumentException();

        return CalendarFormatter.format( FORMAT, (Calendar)obj );
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
            
        if (str == null)
            return null;

        return CalendarParser.parse(FORMAT, str);
    }

}

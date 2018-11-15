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
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;

/**
 * Encoder for xsd:time. For this type returns java.util.Calendar object.
 *
 * @author JAX-RPC Development Team
 */

public class XSDListEncoder extends SimpleTypeEncoderBase {
    private static final SimpleTypeEncoder encoder = new XSDListEncoder();

    protected XSDListEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
        if (null == obj)
            return null;

        if (!(obj instanceof java.util.List))
            throw new IllegalArgumentException();

        if (((List) obj).isEmpty())
            return new String();

        ListIterator li = ((List) obj).listIterator();
        StringBuffer result = new StringBuffer();
        while (li.hasNext()) {
            result.append(li.next());
            result.append(' ');
        }
        return result.toString();
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
            
        if (str == null)
            return null;
        ArrayList list = new ArrayList();
        StringTokenizer in = new StringTokenizer(str.trim(), " ");
        while (in.hasMoreTokens()) {
            list.add(in.nextToken());
        }
        return list;
    }
}

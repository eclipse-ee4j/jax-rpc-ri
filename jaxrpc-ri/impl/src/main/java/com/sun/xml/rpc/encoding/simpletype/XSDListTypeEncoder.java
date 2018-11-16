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

import java.lang.reflect.Array;
import java.util.StringTokenizer;

import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

/**
 * Serializes and Deserializes arrays.
 *
 * @author JAX-RPC Development Team
 */
public class XSDListTypeEncoder extends SimpleTypeEncoderBase {
    private SimpleTypeEncoder encoder = null;
    private Class typeClass = null;

    protected XSDListTypeEncoder(SimpleTypeEncoder encoder, Class typeClass) {
        this.encoder = encoder;
        this.typeClass = typeClass;
    }

    //bug fix: 4906014 - this class is re-written, gets the type's class and forms 
    //array or gets string from array. Also sim[plified how encoding/decoding is done.
    public static SimpleTypeEncoder getInstance(
        SimpleTypeEncoder itemEnc,
        Class typeClass) {
            
        return new XSDListTypeEncoder(itemEnc, typeClass);
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
        if (null == obj)
            return null;

        if (!obj.getClass().isArray())
            throw new IllegalArgumentException();

        StringBuffer ret = new StringBuffer();

        int len = Array.getLength(obj);
        if (len == 0)
            return "";

        for (int i = 0; i < len; i++) {
            ret.append(Array.get(obj, i));
            if (i + 1 < len)
                ret.append(' ');
        }
        return ret.toString();
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
            
        if (str == null)
            return null;
        StringTokenizer in = new StringTokenizer(str.trim(), " ");
        Object objArray = Array.newInstance(typeClass, in.countTokens());
        if (in.countTokens() == 0)
            return objArray;
        int i = 0;
        while (in.hasMoreTokens()) {
            Array.set(
                objArray,
                i++,
                encoder.stringToObject(in.nextToken(), reader));
        }
        return objArray;
    }

    public void writeValue(Object obj, XMLWriter writer) throws Exception {
        writer.writeCharsUnquoted(objectToString(obj, writer));
    }
}

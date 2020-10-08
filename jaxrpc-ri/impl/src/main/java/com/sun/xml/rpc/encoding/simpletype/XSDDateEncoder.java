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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class XSDDateEncoder extends SimpleTypeEncoderBase {
    private static final SimpleTypeEncoder encoder = new XSDDateEncoder();

    protected XSDDateEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
        if (obj == null) {
            return null;
        }
        Calendar cal = (Calendar) obj;
        boolean isBC = (cal.get(Calendar.ERA) == GregorianCalendar.BC);
        StringBuffer buf = new StringBuffer();
        if (isBC) {
            cal.set(Calendar.ERA, GregorianCalendar.AD);
            buf.append("-");
        }
        synchronized (dateFormatter) {
            buf.append(dateFormatter.format(((Calendar) obj).getTime()));
        }
        return buf.toString();
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
        if (str == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance(gmtTimeZone);
        boolean isNeg = false;

        str = EncoderUtils.collapseWhitespace(str);
        if (str.charAt(0) == '+') {
            str = str.substring(1);
        }
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            isNeg = true;
        }
        validateDateStr(str);
        synchronized (dateFormatter) {
            cal.setTime(dateFormatter.parse(str));
        }
        if (isNeg) {
            cal.set(Calendar.ERA, GregorianCalendar.BC);
        }
        return cal;
    }

    public static void validateDateStr(String dateStr) throws Exception {
        // TODO how much should we validate
        if (dateStr.length() < 10)
            throw new DeserializationException("xsd.invalid.date", dateStr);
    }

    protected static final Locale locale = new Locale("en_US");
    protected static final SimpleDateFormat dateFormatter =
        new SimpleDateFormat("yyyy-MM-dd", locale);
    protected static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");  
    static {
        dateFormatter.setTimeZone(gmtTimeZone);
    }
}

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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class XSDDateTimeDateEncoder extends SimpleTypeEncoderBase {
    private static final SimpleTypeEncoder encoder =
        new XSDDateTimeDateEncoder();

    protected XSDDateTimeDateEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
        if (obj == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(gmtTimeZone);
        calendar.setTime((Date) obj);
        boolean isBC = (calendar.get(Calendar.ERA) == GregorianCalendar.BC);
        StringBuffer buf = new StringBuffer();
        if (isBC) {
            calendar.set(Calendar.ERA, GregorianCalendar.AD);
            buf.append("-");
        }
        synchronized (dateFormatter) {
            buf.append(dateFormatter.format(calendar.getTime()));
        }
//        System.out.println("date: "+buf.toString());
        return buf.toString();
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
            
        if (str == null) {
            return null;
        }
        return decodeDateUtil(str, null);
    }

    public static void validateDateStr(String dateStr) throws Exception {
        // TODO how much should we validate
        if (dateStr.length() < 19)
            throw new DeserializationException("xsd.invalid.date", dateStr);
    }

    protected static String getDateFormatPattern(String xsdDateTime) {
        String formatPattern = "yyyy";
        int idx = xsdDateTime.indexOf('-', 4);
        for (int i = 4; i < idx; i++)
            formatPattern += "y";
        formatPattern += "-MM-dd'T'HH:mm:ss";
        idx = xsdDateTime.indexOf('.');
        for (int i = idx; i < xsdDateTime.length() - 1 && i < idx + 3; i++) {
            if (Character.isDigit(xsdDateTime.charAt(i + 1))) {
                if (i == idx) {
                    formatPattern += ".";
                }
                formatPattern += "S";
            } else {
                break;
            }
        }
        return formatPattern;
    }

    protected static Date decodeDateUtil(String str, StringBuffer zone)
        throws Exception {
            
        // this code is synchronized because SimpleDateFormat is not multi-thread safe
        if (str == null) {
            return null;
        } 
        str = EncoderUtils.collapseWhitespace(str);
        Calendar cal = Calendar.getInstance();
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
        StringBuffer strBuf = new StringBuffer(30);
        int dateLen = getDateFormatPattern(str, strBuf);
        String pattern = strBuf.toString();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(gmtTimeZone);
        String tmp = str.substring(0, dateLen);
        Date date = df.parse(str.substring(0, dateLen));

        // handle fractional milliseconds and timezone
        if (dateLen < str.length()) {
            int start = dateLen;
            if (Character.isDigit(str.charAt(start))) {
                int end = start;
                String fraction;

                while (end < str.length()
                    && Character.isDigit(str.charAt(end)))
                    end++;

                String tmp2 = str.substring(start, start + 1);
                int fractmilli =
                    Integer.parseInt(str.substring(start, start + 1));
                if (fractmilli >= 5)
                    date.setTime(date.getTime() + 1);
                start = end;
            }
            // do timezone
            if (start < str.length()) {
                if (str.charAt(start) != 'Z') {
                    if (zone != null)
                        zone.append(str.substring(start));
                    tmp = str.substring(start + 1);
                    Date tzOffset;
                    synchronized (timeZoneFormatter) {
                        tzOffset =
                            timeZoneFormatter.parse(str.substring(start + 1));
                    }
                    long millis =
                        str.charAt(start) == '+'
                            ? - (tzOffset.getTime())
                            : tzOffset.getTime();
                    date.setTime(date.getTime() + millis);
                } else if (str.charAt(start) == 'Z') {
                    cal.setTimeZone(gmtTimeZone);                
                }
            }
        }
        if (isNeg) {
            cal.setTime(date);
            cal.set(Calendar.ERA, GregorianCalendar.BC);
            date = cal.getTime();
        }
        return date;
    }

    protected static int getDateFormatPattern(
        String dateStr,
        StringBuffer strBuf) {
            
        String formatPattern = "yyyy";
        strBuf.append(formatPattern);
        int idx = dateStr.indexOf('-', 4);
        for (int i = 4; i < idx; i++)
            strBuf.append('y');
        strBuf.append("-MM-dd'T'HH:mm:ss");
        idx = dateStr.indexOf('.');
        for (int i = idx;
            idx > 0 && i < dateStr.length() - 1 && i < idx + 3;
            i++) {
            if (Character.isDigit(dateStr.charAt(i + 1))) {
                if (i == idx) {
                    strBuf.append('.');
                }
                strBuf.append('S');
            } else {
                break;
            }
        }
        return strBuf.length() - 2;
    }

    protected static final Locale locale = new Locale("en_US");
    protected static final SimpleDateFormat timeZoneFormatter =
        new SimpleDateFormat("HH:mm", locale);
        
    protected static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
    
    protected static final SimpleDateFormat dateFormatter =
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale);
        
    static {
        dateFormatter.setTimeZone(gmtTimeZone);
        timeZoneFormatter.setTimeZone(gmtTimeZone);
    }

}

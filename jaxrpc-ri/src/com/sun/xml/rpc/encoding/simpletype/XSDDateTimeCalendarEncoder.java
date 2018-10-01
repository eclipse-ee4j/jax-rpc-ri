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
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.util.VersionUtil;

/**
 *
 * @author JAX-RPC Development Team
 */
public class XSDDateTimeCalendarEncoder extends XSDDateTimeDateEncoder {
    private static final SimpleTypeEncoder encoder =
        new XSDDateTimeCalendarEncoder();

    private XSDDateTimeCalendarEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
  /*      if (obj == null) {
            return null;
        }
        Calendar c = (Calendar)obj;
        SimpleDateFormat calFormatter;
        if (c.get(Calendar.ERA) == GregorianCalendar.BC) {   
            calFormatter = calendarFormatterBC;
        } else {
            calFormatter = calendarFormatter;
        }
        synchronized (calendarFormatter) {
            return calFormatter.format(c.getTime());
        }
*/            
        if (obj == null) {
            return null;
        }
        Calendar c = (Calendar)obj;
        String zone;
        String offsetStr;  
        StringBuffer resultBuf = new StringBuffer();
        if (c.get(Calendar.ERA) == GregorianCalendar.BC) {   
            resultBuf.append('-');     
        }
		SimpleDateFormat calendarFormat = getCalendarFormat();
		SimpleDateFormat zoneFormat = getZoneFormat();
        zoneFormat.setTimeZone(c.getTimeZone());
        zone = zoneFormat.format(c.getTime());
        calendarFormat.setTimeZone(c.getTimeZone());
        resultBuf.append(calendarFormat.format(c.getTime()));
        offsetStr = zone.substring(0, 3)+":"+zone.substring(3,5);
        resultBuf.append(offsetStr);
        return resultBuf.toString();
    }

	public SimpleDateFormat getCalendarFormat() {
		SimpleDateFormat format = (SimpleDateFormat)calendarFormatter.get();
		if (format == null) {
			format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", locale);
        	format.setTimeZone(gmtTimeZone);
			calendarFormatter.set(format);
		}
		return format;
	}

	public SimpleDateFormat getZoneFormat() {
		SimpleDateFormat format = (SimpleDateFormat)zoneFormatter.get();
		if (format == null) {
			format = new SimpleDateFormat("Z", locale);
			zoneFormatter.set(format);
		}
		return format;
	}

    public Object stringToObject(String str, XMLReader reader)
        throws Exception { 
            
        if (str == null) {
            return null;
        }
        Calendar cal;
        boolean isNeg = str.charAt(0) == '-';

        StringBuffer zone = new StringBuffer(10);
        Date date = decodeDateUtil(str, zone);
        String zoneStr = zone.toString();
        cal = Calendar.getInstance(gmtTimeZone);
        cal.setTime(date);
        // UTC time
        if (zoneStr.length() != 0) {
            TimeZone tz = TimeZone.getTimeZone("GMT"+zoneStr);
//            System.out.println("caltimetime: " + cal.getTime().getTime());            
            if (isNeg) {
                cal.set(Calendar.ERA, GregorianCalendar.BC);
                cal.setTime(date);
            }     
            cal.setTimeZone(tz);
        }  else {

        if (isNeg)
            cal.set(Calendar.ERA, GregorianCalendar.BC);
        else
            cal.set(Calendar.ERA, GregorianCalendar.AD);
        }

        return cal;
    }

    private int getDSTSavings(TimeZone tz) {
        if (VersionUtil.isJavaVersionGreaterThan1_3()) {
            //jdk1.4
            return tz.getDSTSavings();
        }
        //this is < 1.4
        return ((SimpleTimeZone) tz).getDSTSavings();
    }

    private static final ThreadLocal zoneFormatter = new ThreadLocal();
    private static final ThreadLocal calendarFormatter = new ThreadLocal();

/*
    private static final SimpleDateFormat zoneFormatter =
        new SimpleDateFormat("Z", locale);
    private static final SimpleDateFormat calendarFormatter =
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", locale);
*/

    public void writeAdditionalNamespaceDeclarations(
        Object obj,
        XMLWriter writer)
        throws Exception {
    }
}

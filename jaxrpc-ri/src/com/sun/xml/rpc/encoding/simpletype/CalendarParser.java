/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.rpc.encoding.simpletype;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Parses XML Schema date/time related types into {@link Calendar}.
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public final class CalendarParser extends AbstractCalendarParser {
    public static GregorianCalendar parse( String format, String value ) throws IllegalArgumentException {
        CalendarParser parser = new CalendarParser(format,value);
        parser.parse();
        return parser.cal;
    }
    
    // this version is faster than new GregorianCalendar()
    // which involves in setting the current time.
    private final GregorianCalendar cal = new GregorianCalendar(0,0,0);
    
    private CalendarParser( String format, String value ) {
        super(format,value);
        // erase all the fields to remove any trace of the current time.
        cal.clear(Calendar.YEAR);
        cal.clear(Calendar.MONTH);
        cal.clear(Calendar.DAY_OF_MONTH);
    }
    
    protected void parseFractionSeconds() {
        cal.set(Calendar.MILLISECOND,parseInt(1,3));
        skipDigits();
    }
    
    protected void setTimeZone( java.util.TimeZone tz ) {
        cal.setTimeZone(tz);
    }

    protected void setSeconds(int i) {
        cal.set(Calendar.SECOND,i);
    }

    protected void setMinutes(int i) {
        cal.set(Calendar.MINUTE,i);
    }

    protected void setHours(int i) {
        cal.set(Calendar.HOUR_OF_DAY,i);
    }

    protected void setDay(int i) {
        cal.set(Calendar.DAY_OF_MONTH,i);
    }

    protected void setMonth(int i) {
        cal.set(Calendar.MONTH,i-1); // month is 0-origin.
    }

    protected void setYear(int i) {
        cal.set(Calendar.YEAR,i);
    }
}

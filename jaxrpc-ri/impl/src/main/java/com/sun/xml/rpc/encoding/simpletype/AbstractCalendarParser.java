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

import com.sun.xml.rpc.encoding.simpletype.TimeZone;

import java.util.SimpleTimeZone;

/**
 * Parses XML Schema date/time related types into a structure.
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
abstract class AbstractCalendarParser {
    private final String format;
    private final String value;
    
    private final int flen;
    private final int vlen;
    
    private int fidx;
    protected int vidx;
    
    protected AbstractCalendarParser( String format, String value ) {
        this.format = format;
        this.value = value;
        this.flen = format.length();
        this.vlen = value.length();
    }
    
    public void parse() throws IllegalArgumentException {
        while(fidx<flen) {
            char fch = format.charAt(fidx++);
            
            if(fch!='%') {  // not a meta character
                skip(fch);
                continue;
            }
            
            // seen meta character. we don't do error check against the format
            switch(format.charAt(fidx++)) {
            case 'Y': // year
                int sign=1;
                if(peek()=='-') {
                    vidx++;
                    sign=-1;
                }
                setYear(sign*parseInt(4,Integer.MAX_VALUE));
                break;
            
            case 'M': // month
                setMonth(parseInt(2,2));
                break;
            
            case 'D': // days
                setDay(parseInt(2,2));
                break;
        
            case 'h': // hours
                setHours(parseInt(2,2));
                break;
    
            case 'm': // minutes
                setMinutes(parseInt(2,2));
                break;

            case 's':   // parse seconds.
                setSeconds(parseInt(2,2));
            
                if(peek()=='.') {
                    // parse fraction of a second
                    vidx++;
                    parseFractionSeconds();
                }
                break;
        
            case 'z': // time zone. missing, 'Z', or [+-]nn:nn
                char vch = peek();
                if(vch=='Z') {
                    vidx++;
                    setTimeZone(TimeZone.ZERO);
                } else
                if(vch=='+' || vch=='-') {
                    vidx++;
                    int h = parseInt(2,2);
                    skip(':');
                    int m = parseInt(2,2);
                    setTimeZone(
                        new SimpleTimeZone((h*60+m)*(vch=='+'?1:-1)*60*1000, ""/*no ID*/) );
                } else {
                    setTimeZone(TimeZone.MISSING);
                }
                break;
                
            default:
                // illegal meta character. impossible.
                throw new InternalError();
            }
        }
        
        if(vidx!=vlen)
            // some tokens are left in the input
            throw new IllegalArgumentException(value);//,vidx);
    }
    
    private char peek() throws IllegalArgumentException {
        if(vidx==vlen)  return (char)-1;
        return value.charAt(vidx);
    }
    
    private char read() throws IllegalArgumentException {
        if(vidx==vlen)  throw new IllegalArgumentException(value);//,vidx);
        return value.charAt(vidx++);
    }
    
    private void skip(char ch) throws IllegalArgumentException {
        if(read()!=ch)  throw new IllegalArgumentException(value);//,vidx-1);
    }
    
    /**
     * Skips the extra digits.
     */
    protected final void skipDigits() {
        while(isDigit(peek()))  vidx++;
    }
    
    protected final int parseInt( int minDigits, int maxDigits ) throws IllegalArgumentException {
        int vstart = vidx;
        while( isDigit(peek()) && (vidx-vstart)<maxDigits )
            vidx++;
        if((vidx-vstart)<minDigits)
            // we are expecting more digits
            throw new IllegalArgumentException(value);//,vidx);

        // NumberFormatException is IllegalArgumentException            
//            try {
            return Integer.parseInt(value.substring(vstart,vidx));
//            } catch( NumberFormatException e ) {
//                // if the value is too long for int, NumberFormatException is thrown
//                throw new IllegalArgumentException(value,vstart);
//            }
    }
    

    private static boolean isDigit(char ch) {
        return '0'<=ch && ch<='9';
    }
    
    

    protected abstract void parseFractionSeconds();
    protected abstract void setTimeZone( java.util.TimeZone tz );
    protected abstract void setSeconds(int i);
    protected abstract void setMinutes(int i);
    protected abstract void setHours(int i);
    protected abstract void setDay(int i);
    protected abstract void setMonth(int i);
    protected abstract void setYear(int i);
}

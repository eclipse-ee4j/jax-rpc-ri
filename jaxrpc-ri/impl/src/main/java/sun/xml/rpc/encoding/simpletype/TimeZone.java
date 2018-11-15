/*
 * Copyright (c) 2001, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.rpc.encoding.simpletype;

import java.io.Serializable;
import java.util.SimpleTimeZone;

/**
 * simple time zone component.
 * 
 * @author Kohsuke KAWAGUCHI
 */
public class TimeZone implements Serializable {
    /**
     * Difference from GMT in terms of minutes.
     * @deprecated here just for the serialization backward compatibility.
     */
    public int minutes;

    private Object readResolve() {
        // use java.util.TimeZone instead
        return new SimpleTimeZone(minutes*60*1000,"");
    }
    
    /**
     * The {@link java.util.TimeZone} representation that corresponds
     * to the ZERO singleton instance. Once again, using a special
     * instance is a hack to make the round-tripping work OK.
     */
    public static final java.util.TimeZone ZERO = new JavaZeroTimeZone();
    
    /**
     * The {@link java.util.TimeZone} representation that corresponds
     * to the missing time zone.
     */
    public static final java.util.TimeZone MISSING = new JavaMissingTimeZone();
    
    
    // serialization support
    private static final long serialVersionUID = 1;    
    
    
//
// nested inner classes
//    
    /**
     * @deprecated
     *      exists just for the backward serialization compatibility.
     */
    static class ZeroTimeZone extends TimeZone {
        ZeroTimeZone() {
        }
        protected Object readResolve() {
            // use the singleton instance
            return ZERO;
        }
        // serialization support
        private static final long serialVersionUID = 1;    
    }
    
    private static class JavaZeroTimeZone extends SimpleTimeZone implements Serializable {
        JavaZeroTimeZone() {
            super(0, "XSD 'Z' timezone");
        } 
        protected Object readResolve() {
            return ZERO;
        }
        // serialization support
        private static final long serialVersionUID = 1;    
    }
    
    private static class JavaMissingTimeZone extends SimpleTimeZone implements Serializable {
        JavaMissingTimeZone() {
            super(0, "XSD missing timezone");
        } 
        protected Object readResolve() {
            return MISSING;
        }
        // serialization support
        private static final long serialVersionUID = 1;    
    }
}

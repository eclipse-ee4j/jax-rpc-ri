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

package com.sun.xml.rpc.streaming;

import java.io.OutputStream;

/**
 * <p> A concrete factory for FI XMLWriter objects. </p>
 *
 * @author Santiago.PericasGeertsen@sun.com
 */
public class FastInfosetWriterFactoryImpl extends XMLWriterFactory {

    static XMLWriterFactory _instance;
    
    static ThreadLocal writerLocal = new ThreadLocal();
    
    public static XMLWriterFactory newInstance() {
        if (_instance == null) {
            _instance = new FastInfosetWriterFactoryImpl();
        }
        
        return _instance;
    }
    
    public FastInfosetWriterFactoryImpl() {
    }

    public final XMLWriter createXMLWriter(OutputStream stream) {
        return createXMLWriter(stream, "UTF-8");
    }

    public final XMLWriter createXMLWriter(OutputStream stream, String encoding) {
        return createXMLWriter(stream, encoding, false);
    }

    public final XMLWriter createXMLWriter(OutputStream stream, String encoding,
        boolean declare) 
    {
        FastInfosetWriter writer = (FastInfosetWriter) writerLocal.get();
        if (writer == null) {
            writerLocal.set(writer = new FastInfosetWriter(stream, encoding));
        }
        else {
            writer.setOutputStream(stream);
            writer.setEncoding(encoding);
        }
        writer.writeStartDocument();
        return writer;    
    }
}

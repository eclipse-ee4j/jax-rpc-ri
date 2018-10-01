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

import java.io.InputStream;

import org.xml.sax.InputSource;
import javax.xml.stream.*;
import com.sun.xml.fastinfoset.stax.*;

/**
 * <p> A concrete factory for FI XMLReader objects. </p>
 *
 * @author Santiago.PericasGeertsen@sun.com
 *
 */
public class FastInfosetReaderFactoryImpl extends XMLReaderFactory {
    
    static ThreadLocal readerLocal = new ThreadLocal();

    static XMLReaderFactory _instance;
    
    public static XMLReaderFactory newInstance() {
        if (_instance == null) {
            _instance = new FastInfosetReaderFactoryImpl();
        }
        
        return _instance;
    }
    
    public FastInfosetReaderFactoryImpl() {
    }

    public final XMLReader createXMLReader(InputStream in) {
        return createXMLReader(in, false);
    }

    public final XMLReader createXMLReader(InputSource source) {
        return createXMLReader(source, false);
    }

    public final XMLReader createXMLReader(InputSource source, boolean rejectDTDs) {
        return createXMLReader(source.getByteStream(), rejectDTDs);        
    }
    
    public final XMLReader createXMLReader(InputStream in, boolean rejectDTDs) {
        FastInfosetReader reader = (FastInfosetReader) readerLocal.get();
        if (reader == null) {
            readerLocal.set(reader = new FastInfosetReader(in));
        }
        else {
            reader.setInputStream(in);
        }
        return reader;
    }

}

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

package com.sun.xml.rpc.processor.util;

import java.io.OutputStream;

import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.streaming.XMLWriterFactory;

/**
 * <p> A concrete factory for XMLWriter objects. </p>
 *
 * <p> By default, writers created by this factory use UTF-8
 * encoding and write the namespace declaration at the top
 * of each document they produce. </p>
 *
 * @author JAX-RPC Development Team
 */
public class PrettyPrintingXMLWriterFactoryImpl extends XMLWriterFactory {
    
    public PrettyPrintingXMLWriterFactoryImpl() {}
    
    public XMLWriter createXMLWriter(OutputStream stream) {
        return createXMLWriter(stream, "UTF-8");
    }
    
    public XMLWriter createXMLWriter(OutputStream stream, String encoding) {
        return createXMLWriter(stream, encoding, true);
    }
    
    public XMLWriter createXMLWriter(OutputStream stream, String encoding,
        boolean declare) {
            
        return new PrettyPrintingXMLWriterImpl(stream, encoding, declare);
    }
}

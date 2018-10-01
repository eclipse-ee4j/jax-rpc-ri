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

/**
 * <p> A concrete factory for XMLReader objects. </p>
 *
 * @author JAX-RPC Development Team
 */
public class XMLReaderFactoryImpl extends XMLReaderFactory {

    public XMLReaderFactoryImpl() {
    }

    public XMLReader createXMLReader(InputStream in) {
        return createXMLReader(in, false);
    }

    public XMLReader createXMLReader(InputSource source) {
        return createXMLReader(source, false);
    }

    public XMLReader createXMLReader(InputStream in, boolean rejectDTDs) {
        return createXMLReader(new InputSource(in), rejectDTDs);
    }

    public XMLReader createXMLReader(InputSource source, boolean rejectDTDs) {
        return new XMLReaderImpl(source, rejectDTDs);
    }
}

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

package com.sun.xml.rpc.sp;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 *
 * @author JAX-RPC RI Development Team
 */
class ExternalEntity extends EntityDecl {
    String systemId; // resolved URI (not relative)
    String publicId; // "-//xyz//....//en"
    String notation;

    public ExternalEntity(Locator l) {
    }

    public InputSource getInputSource(EntityResolver r)
        throws SAXException, IOException {
        InputSource retval;

        retval = r.resolveEntity(publicId, systemId);
        // SAX sez if null is returned, use the URI directly
        if (retval == null)
            retval = Resolver.createInputSource(new URL(systemId), false);
        return retval;
    }
}

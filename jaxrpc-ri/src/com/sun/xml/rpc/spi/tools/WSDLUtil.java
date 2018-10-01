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

package com.sun.xml.rpc.spi.tools;

import java.net.URL;
import java.util.Collection;

/**
 * This interface is implemented by 
 * com.sun.xml.rpc.wsdl.WSDLUtil
 * <p>
 * The implementation of this interface will provide some utilities
 * in retrieving relevant information from a WSDL file.  We expose
 * those functionalities via this utility class instead of directly
 * putting the burden on jaxrpc implementation of WSDLParser.
 * <p>
 * We should be conservative on adding methods to this utility class.
 * Hopefully, we could use JSR 110 (parsing WSDL) implementation soon.
 */
public interface WSDLUtil {

    /**
     * Collect all relative imports from a web service's main wsdl document.
     * [This should be equivalent to WSDLParser.setFollowImports(false)]
     *
     * @param wsdlURL The URL for a wsdl document
     * @param wsdlRelativeImports outupt param in which wsdl relative imports 
     *                            will be added
     * @param schemaRelativeImports outupt param in which schema relative 
     *                              imports will be added
     */
    public void getRelativeImports(
        URL wsdlURL,
        Collection wsdlRelativeImports,
        Collection schemaRelativeImports)
        throws java.io.IOException;
}

/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2002 International Business Machines Corp. 2002. All rights reserved.
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

package com.sun.xml.rpc.processor.modeler.j2ee.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/*
 * Entity resolver to resolve JSR109 related DTDs locally
 * @author Michael Cheng
 */
public class EntityResolverRI implements EntityResolver {

    /* constructor */
    public EntityResolverRI() {
    }

    /*
     * @return InputSrouce for the DTD, or null if this EntityResolver
     *         does not handle the DTD.
     */
    public InputSource resolveEntity(String publicId, String systemId)
        throws IOException {

        //System.out.println("[EntityResolverRI]--> publicID = " + publicId);
        //System.out.println("[EntityResolverRI]--> systemId = " + systemId);

        String resource = null;
        if (publicId == null) {

            // unspecified schema
            if (systemId == null
                || systemId.lastIndexOf('/') == systemId.length()) {
                return null;
            }

            if (systemId.endsWith("j2ee_jaxrpc_mapping_1_1.xsd")) {
                resource =
                    "com/sun/xml/rpc/processor/modeler/j2ee/xml/j2ee_jaxrpc_mapping_1_1.xsd";
            } else if (systemId.endsWith("j2ee_1_4.xsd")) {
                resource =
                    "com/sun/xml/rpc/processor/modeler/j2ee/xml/j2ee_1_4.xsd";
            } else if (systemId.endsWith("j2ee_web_services_client_1_1.xsd")) {
                resource =
                    "com/sun/xml/rpc/processor/modeler/j2ee/xml/j2ee_web_services_client_1_1.xsd";
            } else if (systemId.endsWith("xml.xsd")) {
                resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/xml.xsd";
            }

        } else {

            // unspecified schema
            if (systemId == null
                || systemId.lastIndexOf('/') == systemId.length()) {
                return null;
            }

            if (systemId.endsWith("XMLSchema.dtd")) {
                resource =
                    "com/sun/xml/rpc/processor/modeler/j2ee/xml/XMLSchema.dtd";
            } else if (systemId.endsWith("datatypes.dtd")) {
                resource =
                    "com/sun/xml/rpc/processor/modeler/j2ee/xml/datatypes.dtd";
            }
        }

        if (resource == null) {
            //XXX FIXME  log it better
            System.out.println(systemId + " not resolved");
            return null;
        }

        //System.out.println("[EntityResolverRI]--> resource = " + resource);

        InputStream inStrm = getClassLoader().getResourceAsStream(resource);

        if (inStrm == null) {
            System.out.println("unable to locate resource " + resource);
            throw new java.io.IOException(
                "unable to locate resource " + resource);
        }

        InputSource is = new InputSource(inStrm);
        is.setSystemId(systemId);
        return is;
    }

    private ClassLoader getClassLoader() {
        ClassLoader loader = this.getClass().getClassLoader();

        //In case the bootstrap loader is null, i.e. through launcher
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }

        return loader;
    }

};

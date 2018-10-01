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

package com.sun.xml.rpc.processor.modeler.j2ee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.xml.sax.InputSource;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaWsdlMapping;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaWsdlMappingFactory;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.packageMappingType;

/*
 * Representation of JaxRpcMapping meta data .xml file
 * @author Michael Cheng
 */
public class JaxRpcMappingXml {

    /* Constructor
     * @param systemId location of mapping meta data. Can be URL or file name
     */
    public JaxRpcMappingXml(String systemId) throws java.io.IOException {

        //System.out.println("[JaxRpcMappingXml] ==> systemId = " + systemId);

        factory = new javaWsdlMappingFactory();
        InputSource src = new InputSource(systemId);
        factory.setPackageName("com.sun.xml.rpc.processor.modeler.j2ee.xml");

        javaWsdlMap =
            (javaWsdlMapping) factory.loadDocument("javaWsdlMapping", src);
        if (javaWsdlMap == null) {
            throw new java.io.IOException(
                "Unable to load mapping meta data at: " + systemId);
        }
    }

    /*
     * @return JavaBean that represents &lt;java-wsdl-mapping&gt; element 
     */
    public javaWsdlMapping getJavaWsdlMapping() {
        return javaWsdlMap;
    }

    /**
     * @return Hashmap of namespace to package name mappings
     */
    public HashMap getNSToPkgMapping() {
        if (nsMap == null) {
            nsMap = new HashMap();
            int numPkgMap = javaWsdlMap.getPackageMappingCount();
            for (int i = 0; i < numPkgMap; i++) {
                packageMappingType pkgMap = javaWsdlMap.getPackageMapping(i);
                nsMap.put(
                    pkgMap.getNamespaceURI().getElementValue(),
                    pkgMap.getPackageType().getElementValue());
            }
        }
        return nsMap;
    }

    /**
     * unit test
     */
    public static void main(String[] argv) {
        try {
            if (argv.length != 1) {
                System.out.println(
                    "usage: com.ibm.webservices.ri.deploy.JaxRpcMappingXml systemId");
                System.exit(1);
            }
            JaxRpcMappingXml jaxRpcMap = new JaxRpcMappingXml(argv[0]);
            HashMap nsMap = jaxRpcMap.getNSToPkgMapping();
            Set keys = nsMap.keySet();
            System.out.println(nsMap.size() + " namespace to package mapping:");
            for (Iterator it = keys.iterator(); it.hasNext();) {
                String ns = (String) it.next();
                String pkg = (String) nsMap.get(ns);
                System.out.println("'" + ns + "' : '" + pkg + "'");
            }

            javaWsdlMapping javaWsdlMap = jaxRpcMap.getJavaWsdlMapping();
            int numJavaXmlTypeMapping =
                javaWsdlMap.getJavaXmlTypeMappingCount();
            System.out.println(
                "There are "
                    + numJavaXmlTypeMapping
                    + " java-xml-type-mapping");

        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    javaWsdlMappingFactory factory;
    javaWsdlMapping javaWsdlMap;
    HashMap nsMap; // ns to pkg mapping
}

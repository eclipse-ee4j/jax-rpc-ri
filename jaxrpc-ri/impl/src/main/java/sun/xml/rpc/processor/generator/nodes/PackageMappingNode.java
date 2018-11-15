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

package com.sun.xml.rpc.processor.generator.nodes;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author  Qingqing Ouyang
 * @version 
 */
public class PackageMappingNode extends JaxRpcMappingNode {

    /**
     * Default constructor.
     */
    public PackageMappingNode() {
    }

    /**
     * write the appropriate information to a DOM tree and return it
     *
     * @param parent node in the DOM tree 
     * @param nodeName name for the root element for this DOM tree fragment
     * @param packageName fully qualified Java package name
     * @param namespace the URI for target namespace
     * @return the DOM tree top node
     */
    public Node write(
        Node parent,
        String nodeName,
        String packageName,
        String namespace)
        throws Exception {

        debug(MYNAME, "packageName = " + packageName);
        debug(MYNAME, "namespace = " + namespace);

        Element node = appendChild(parent, nodeName);
        if (packageName != null && namespace != null) {
            appendTextChild(
                node,
                JaxRpcMappingTagNames.PACKAGE_TYPE,
                packageName);
            appendTextChild(
                node,
                JaxRpcMappingTagNames.NAMESPACEURI,
                namespace);
        }

        return node;
    }

    private final static String MYNAME = "PackageMappingNode";
}

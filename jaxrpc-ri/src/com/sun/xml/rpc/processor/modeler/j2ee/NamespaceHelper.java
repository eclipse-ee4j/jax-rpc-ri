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

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.ComplexType;

/**
 * Helper class used to convert a QName from a
 * xsd:QName string in an XML schema instance.
 * The xsd:QName string is of the form prefix:localpart, and the
 * mapping of prefix to a real namesapce may be defined at any level
 * in the traversal of the XML instance.  There, this class is
 * intended to be used as follows:
 *<br>
 *  When traversing a new child node BaseType, the push() method must be called
 * to allow the NamespaceHelper to fetch any new prefix and namespace 
 * declaration in the child node.  When done with visiting the child
 * node, the pop() method must be called to revert back to previous
 * settings.
 */
public class NamespaceHelper {
    private HashMap map; // HashMap of local prefix to namespace
    private NamespaceHelper prev; // previous top of stack
    public NamespaceHelper() {
        map = null;
        prev = null;
    }

    private NamespaceHelper(NamespaceHelper prev, ComplexType ct) {
        map = new HashMap();
        this.prev = prev;
        String[] attrNames = ct.getAttributeNames();
        for (int i = 0; i < attrNames.length; i++) {
            String attrName = attrNames[i];
            if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) {
                map.put(attrName, ct.getAttributeValue(attrName));
            }
        }
    }

    /**
     * Push a BaseType during traversal of BaseType.
     * @param bt  The BaseType being visited
     * @return a new instnace of NamespaceHelper to be used to resolve
     * QName string to a QName instance
     */
    public NamespaceHelper push(ComplexType ct) {
        return new NamespaceHelper(this, ct);
    }

    /**
     * Pop the NamespaceHelper to signal that we're done visiting the 
     * Basetype
     * @return NamespaceHelper that can be used to resolve Qname string
     * to a QName instance with the context that was set prior to
     * visiting the BaseType
     */
    public NamespaceHelper pop() {
        return prev;
    }

    /**
     * Convert a QName string with namespace prefix to a QName
     * instance containing real namespace.
     * @param nsString the QName string in the XML instnace.  It can be
     * of the form "name", or "prefix:name". 
     * @return QName derived from the QName string, or null if QName can't be
     * determined.
     */
    public QName getQName(String nsString) {
        String attrName;
        // name of namespace prefix attribute to look up in map
        String local; // local part of QName

        int idx = nsString.indexOf(':');
        if (idx < nsString.length() - 1) {
            /* nsString is of the form "prefix:local" */
            String prefix = nsString.substring(0, idx);
            attrName = "xmlns:" + prefix;
            local = nsString.substring(idx + 1);
        } else {
            attrName = "xmlns";
            local = nsString;
        }
        return getQNameInternal(attrName, local);
    }

    private QName getQNameInternal(String attrName, String local) {
        if (map == null)
            return null;
        String namespace = (String) map.get(attrName);
        if (namespace != null) {
            return new QName(namespace, local);
        } else if (prev != null) {
            return prev.getQNameInternal(attrName, local);
        } else
            return null;
    }
};

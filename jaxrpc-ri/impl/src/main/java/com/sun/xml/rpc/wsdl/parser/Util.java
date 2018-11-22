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

package com.sun.xml.rpc.wsdl.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.sun.xml.rpc.util.xml.XmlUtil;
import com.sun.xml.rpc.wsdl.framework.ParseException;

/**2
 * Defines various utility methods.
 *
 * @author JAX-RPC Development Team
 */
public class Util {

    public static String getRequiredAttribute(Element element, String name) {
        String result = XmlUtil.getAttributeOrNull(element, name);
        if (result == null)
            fail(
                "parsing.missingRequiredAttribute",
                element.getTagName(),
                name);
        return result;
    }

    public static void verifyTag(Element element, String tag) {
        if (!element.getLocalName().equals(tag))
            fail("parsing.invalidTag", element.getTagName(), tag);
    }

    public static void verifyTagNS(Element element, String tag, String nsURI) {
        if (!element.getLocalName().equals(tag)
            || (element.getNamespaceURI() != null
                && !element.getNamespaceURI().equals(nsURI)))
            fail(
                "parsing.invalidTagNS",
                new Object[] {
                    element.getTagName(),
                    element.getNamespaceURI(),
                    tag,
                    nsURI });
    }

    public static void verifyTagNS(Element element, QName name) {
        if (!element.getLocalName().equals(name.getLocalPart())
            || (element.getNamespaceURI() != null
                && !element.getNamespaceURI().equals(name.getNamespaceURI())))
            fail(
                "parsing.invalidTagNS",
                new Object[] {
                    element.getTagName(),
                    element.getNamespaceURI(),
                    name.getLocalPart(),
                    name.getNamespaceURI()});
    }

    public static void verifyTagNSRootElement(Element element, QName name) {
        if (!element.getLocalName().equals(name.getLocalPart())
            || (element.getNamespaceURI() != null
                && !element.getNamespaceURI().equals(name.getNamespaceURI())))
            fail(
                "parsing.incorrectRootElement",
                new Object[] {
                    element.getTagName(),
                    element.getNamespaceURI(),
                    name.getLocalPart(),
                    name.getNamespaceURI()});
    }

    public static Element nextElementIgnoringCharacterContent(Iterator iter) {
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n instanceof Text)
                continue;
            if (n instanceof Comment)
                continue;
            if (!(n instanceof Element))
                fail("parsing.elementExpected");
            return (Element) n;
        }

        return null;
    }

    public static Element nextElement(Iterator iter) {
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n instanceof Text) {
                Text t = (Text) n;
                if (t.getData().trim().length() == 0)
                    continue;
                fail("parsing.nonWhitespaceTextFound", t.getData().trim());
            }
            if (n instanceof Comment)
                continue;
            if (!(n instanceof Element))
                fail("parsing.elementExpected");
            return (Element) n;
        }

        return null;
    }

    public static String processSystemIdWithBase(
        String baseSystemId,
        String systemId) {
        try {
            URL base = null;
            try {
                base = new URL(baseSystemId);
            } catch (MalformedURLException e) {
                base = new File(baseSystemId).toURL();
            }

            try {
                URL url = new URL(base, systemId);
                return url.toString();
            } catch (MalformedURLException e) {
                fail("parsing.invalidURI", systemId);
            }

        } catch (MalformedURLException e) {
            fail("parsing.invalidURI", baseSystemId);
        }

        return null; // keep compiler happy
    }

    public static void fail(String key) {
        throw new ParseException(key);
    }

    public static void fail(String key, String arg) {
        throw new ParseException(key, arg);
    }

    public static void fail(String key, String arg1, String arg2) {
        throw new ParseException(key, new Object[] { arg1, arg2 });
    }

    public static void fail(String key, Object[] args) {
        throw new ParseException(key, args);
    }
}

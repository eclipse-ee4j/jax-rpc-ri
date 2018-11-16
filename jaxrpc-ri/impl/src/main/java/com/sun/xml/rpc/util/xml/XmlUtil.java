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

package com.sun.xml.rpc.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.sun.xml.messaging.saaj.util.ByteInputStream;
import com.sun.xml.rpc.util.WSDLParseException;

/**
 * @author JAX-RPC Development Team
 */
public class XmlUtil {

	public static String getPrefix(String s) {
		int i = s.indexOf(':');
		if (i == -1)
			return null;
		return s.substring(0, i);
	}

	public static String getLocalPart(String s) {
		int i = s.indexOf(':');
		if (i == -1)
			return s;
		return s.substring(i + 1);
	}

	public static String getAttributeOrNull(Element e, String name) {
		Attr a = e.getAttributeNode(name);
		if (a == null)
			return null;
		return a.getValue();
	}

	public static String getAttributeNSOrNull(
		Element e,
		String name,
		String nsURI) {
		Attr a = e.getAttributeNodeNS(nsURI, name);
		if (a == null)
			return null;
		return a.getValue();
	}

	public static boolean matchesTagNS(Element e, String tag, String nsURI) {
		try {
			return e.getLocalName().equals(tag)
				&& e.getNamespaceURI().equals(nsURI);
		} catch (NullPointerException npe) {

			// localname not null since parsing would fail before here
			throw new WSDLParseException(
				"null.namespace.found",
				e.getLocalName());
		}
	}

	public static boolean matchesTagNS(
		Element e,
		javax.xml.namespace.QName name) {
		try {
			return e.getLocalName().equals(name.getLocalPart())
				&& e.getNamespaceURI().equals(name.getNamespaceURI());
		} catch (NullPointerException npe) {

			// localname not null since parsing would fail before here
			throw new WSDLParseException(
				"null.namespace.found",
				e.getLocalName());
		}
	}

	public static Iterator getAllChildren(Element element) {
		return new NodeListIterator(element.getChildNodes());
	}

	public static Iterator getAllAttributes(Element element) {
		return new NamedNodeMapIterator(element.getAttributes());
	}

	public static List parseTokenList(String tokenList) {
		List result = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(tokenList, " ");
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		return result;
	}

	public static String getTextForNode(Node node) {
		StringBuffer sb = new StringBuffer();

		NodeList children = node.getChildNodes();
		if (children.getLength() == 0)
			return null;

		for (int i = 0; i < children.getLength(); ++i) {
			Node n = children.item(i);

			if (n instanceof Text)
				sb.append(n.getNodeValue());
			else if (n instanceof EntityReference) {
				String s = getTextForNode(n);
				if (s == null)
					return null;
				else
					sb.append(s);
			} else
				return null;
		}

		return sb.toString();
	}

	public static InputStream getUTF8Stream(String s) {
		try {
			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			Writer w = new OutputStreamWriter(bas, "utf-8");
			w.write(s);
			w.close();
			byte[] ba = bas.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(ba);
			return bis;
		} catch (IOException e) {
			throw new RuntimeException("should not happen");
		}
	}

	public static ByteInputStream getUTF8ByteInputStream(String s) {
		try {
			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			Writer w = new OutputStreamWriter(bas, "utf-8");
			w.write(s);
			w.close();
			byte[] ba = bas.toByteArray();
			ByteInputStream bis = new ByteInputStream(ba, ba.length);
			return bis;
		} catch (IOException e) {
			throw new RuntimeException("should not happen");
		}
	}

	static TransformerFactory transformerFactory = null;

	public static Transformer newTransformer() {
		Transformer t = null;

		if (transformerFactory == null)
			transformerFactory = TransformerFactory.newInstance();

		try {
			t = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException tex) {
			throw new IllegalStateException("Unable to create a JAXP transformer");
		}
		return t;
	}

}

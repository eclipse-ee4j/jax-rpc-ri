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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

import com.sun.xml.rpc.sp.NamespaceSupport;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
import com.sun.xml.rpc.util.xml.CDATA;
import com.sun.xml.rpc.util.xml.XmlWriter;

/**
 * <p> A concrete XMLWriter implementation class. </p>
 *
 * @author JAX-RPC Development Team
 */
public class XMLWriterImpl extends XMLWriterBase {

    public XMLWriterImpl(OutputStream out, String enc, boolean declare) {
        try {
            _writer = new XmlWriter(out, enc, declare);
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void startElement(String localName, String uri) {
        try {
            _nsSupport.pushContext();

            if (!uri.equals("")) {
                String aPrefix = null;
                boolean mustDeclarePrefix = false;

                String defaultNamespaceURI = _nsSupport.getPrefix("");
                if (defaultNamespaceURI != null) {
                    if (uri.equals(defaultNamespaceURI)) {
                        aPrefix = "";
                    }
                }

                aPrefix = _nsSupport.getPrefix(uri);

                if (aPrefix == null) {
                    mustDeclarePrefix = true;

                    if (_prefixFactory != null) {
                        aPrefix = _prefixFactory.getPrefix(uri);
                    }

                    if (aPrefix == null) {
                        throw new XMLWriterException(
                            "xmlwriter.noPrefixForURI",
                            uri);
                    }
                }

                String rawName =
                    aPrefix.equals("")
                        ? localName
                        : (aPrefix + ":" + localName);

                _writer.start(rawName);
                _elemStack.push(rawName);

                if (mustDeclarePrefix) {
                    writeNamespaceDeclaration(aPrefix, uri);
                }
            } else {
                _writer.start(localName);
                _elemStack.push(localName);
            }
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void startElement(String localName, String uri, String prefix) {
        try {
            _nsSupport.pushContext();

            if (!uri.equals("")) {
                String aPrefix = null;
                boolean mustDeclarePrefix = false;

                String defaultNamespaceURI = _nsSupport.getPrefix("");
                if (defaultNamespaceURI != null) {
                    if (uri.equals(defaultNamespaceURI)) {
                        aPrefix = "";
                    }
                }

                aPrefix = _nsSupport.getPrefix(uri);

                if (aPrefix == null) {
                    mustDeclarePrefix = true;

                    aPrefix = prefix;

                    if (aPrefix == null) {
                        throw new XMLWriterException(
                            "xmlwriter.noPrefixForURI",
                            uri);
                    }
                }

                String rawName =
                    aPrefix.equals("")
                        ? localName
                        : (aPrefix + ":" + localName);

                _writer.start(rawName);
                _elemStack.push(rawName);

                if (mustDeclarePrefix) {
                    writeNamespaceDeclaration(aPrefix, uri);
                }
            } else {
                _writer.start(localName);
                _elemStack.push(localName);
            }
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeNamespaceDeclaration(String prefix, String uri) {
        try {
            _nsSupport.declarePrefix(prefix, uri);

            if ((prefix != null) && !prefix.equals("")) {
                // it's not a default namespace declaration
                _writer.attribute("xmlns", prefix, uri);
            } else {
                _writer.attribute("xmlns", uri);
            }

        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeNamespaceDeclaration(String uri) {

        if (_prefixFactory == null) {
            throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
        }

        String aPrefix = _prefixFactory.getPrefix(uri);
        writeNamespaceDeclaration(aPrefix, uri);
    }

    public void writeAttribute(String localName, String uri, String value) {
        try {
            if (!uri.equals("")) {

                String aPrefix = null;
                boolean mustDeclarePrefix = false;

                String defaultNamespaceURI = _nsSupport.getPrefix("");
                if (defaultNamespaceURI != null) {
                    if (uri.equals(defaultNamespaceURI)) {
                        aPrefix = "";
                    }
                }

                aPrefix = _nsSupport.getPrefix(uri);

                if (aPrefix == null) {
                    mustDeclarePrefix = true;

                    if (_prefixFactory != null) {
                        aPrefix = _prefixFactory.getPrefix(uri);
                    }

                    if (aPrefix == null) {
                        throw new XMLWriterException(
                            "xmlwriter.noPrefixForURI",
                            uri);
                    }
                }

                _writer.attribute(aPrefix, localName, value);

                if (mustDeclarePrefix) {
                    writeNamespaceDeclaration(aPrefix, uri);
                }

            } else {
                _writer.attribute(localName, value);
            }
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeAttributeUnquoted(
        String localName,
        String uri,
        String value) {
        try {
            if (!uri.equals("")) {

                String aPrefix = null;
                boolean mustDeclarePrefix = false;

                String defaultNamespaceURI = _nsSupport.getPrefix("");
                if (defaultNamespaceURI != null) {
                    if (uri.equals(defaultNamespaceURI)) {
                        aPrefix = "";
                    }
                }

                aPrefix = _nsSupport.getPrefix(uri);

                if (aPrefix == null) {
                    mustDeclarePrefix = true;

                    if (_prefixFactory != null) {
                        aPrefix = _prefixFactory.getPrefix(uri);
                    }

                    if (aPrefix == null) {
                        throw new XMLWriterException(
                            "xmlwriter.noPrefixForURI",
                            uri);
                    }
                }

                _writer.attributeUnquoted(aPrefix, localName, value);

                if (mustDeclarePrefix) {
                    writeNamespaceDeclaration(aPrefix, uri);
                }

            } else {
                _writer.attributeUnquoted(localName, value);
            }
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeChars(CDATA chars) {
        try {
            _writer.chars(chars);
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeChars(String chars) {
        try {
            _writer.chars(chars);
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeCharsUnquoted(String chars) {
        try {
            _writer.charsUnquoted(chars);
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void writeCharsUnquoted(char[] buf, int offset, int len) {
        try {
            _writer.charsUnquoted(buf, offset, len);
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void endElement() {
        try {
            // write the end tag
            String rawName = (String) _elemStack.pop();
            _writer.end(rawName);

            _nsSupport.popContext();
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public PrefixFactory getPrefixFactory() {
        return _prefixFactory;
    }

    public void setPrefixFactory(PrefixFactory factory) {
        _prefixFactory = factory;
    }

    public String getURI(String prefix) {
        return _nsSupport.getURI(prefix);
    }

    public String getPrefix(String uri) {
        return _nsSupport.getPrefix(uri);
    }

    public void flush() {
        try {
            _writer.flush();
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    public void close() {
        try {
            _writer.close();
        } catch (IOException e) {
            throw wrapException(e);
        }
    }

    private XMLWriterException wrapException(IOException e) {
        return new XMLWriterException(
            "xmlwriter.ioException",
            new LocalizableExceptionAdapter(e));
    }

    private XmlWriter _writer;
    private NamespaceSupport _nsSupport = new NamespaceSupport();
    private Stack _elemStack = new Stack();
    private PrefixFactory _prefixFactory;
}

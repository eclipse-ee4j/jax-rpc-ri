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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A demand-driven streaming parser implementation.
 *
 * @author Zhenghua Li
 * @author JAX-RPC RI Development Team
 */

public final class StreamingParserImpl extends StreamingParser {

    private Parser parser = null;

    private static final int DOC_END = -1,
        DOC_START = -2,
        EMPTY = -3,
        EXCEPTION = -4;

    private int cur = EMPTY;
    private String curName = null;
    private String curValue = null;
    private String curURI = null;

    private boolean validating;
    private boolean coalescing;
    private boolean namespaceAware;

    private int curLine = -1;
    private int curCol = -1;

    /* Set once by DocHandler at startup */
    private String publicId = null;
    private String systemId = null;

    /* -- Constructors -- */

    private StreamingParserImpl(StreamingParserFactory pf) {
        this.validating = pf.isValidating();
        this.coalescing = pf.isCoalescing();
        this.namespaceAware = pf.isNamespaceAware();
    }

    StreamingParserImpl(StreamingParserFactory pf, InputStream in) {
        this(pf);
        this.parser = new Parser(in, coalescing, namespaceAware);
    }

    StreamingParserImpl(StreamingParserFactory pf, File file)
        throws IOException {
        this(pf);
        this.parser = new Parser(file, coalescing, namespaceAware);
    }

    /* -- Methods -- */

    /**
     * Parses the next component of the document being parsed.
     *
     * @return  The parser's current state, one of {@link #START},
     *          {@link #END}, {@link #ATTR}, {@link #CHARS}, {@link #IWS},
     *          or {@link #PI}, or <tt>-1</tt> if the end of the document has
     *          been reached
     *
     * @throws ParseException
     *         If an XML parsing error occurs
     *
     * @throws IOException
     *         If an I/O error occurs
     */
    public int parse() throws ParseException, IOException {
        if (cur == DOC_END) {
            return -1;
        } else {
            cur = parser.parse();
            curName = parser.getCurName();
            curValue = parser.getCurValue();
            curURI = parser.getCurURI();
            curLine = parser.getLineNumber();
            curCol = parser.getColumnNumber();
            return cur;
        }
    }

    /**
     * Returns the current state of the parser.
     *
     * @return  The parser's current state, one of {@link #START},
     *          {@link #END}, {@link #ATTR}, {@link #CHARS}, {@link #IWS},
     *          or {@link #PI}, or <tt>-1</tt> if the end of the document has
     *          been reached.
     *
     * @throws  IllegalStateException
     *          If the parser has yet not been started by invoking the
     *          {@link #parse} method
     */
    public int state() {
        if (cur == EMPTY)
            throw new IllegalStateException("Parser not started");
        if (cur < DOC_END)
            throw new InternalError();
        return cur;
    }

    /**
     * Returns a name string whose meaning depends upon the current state.
     *
     * @throws  IllegalStateException
     *          If there is no name data for the current parser state
     */
    public String name() {
        if (curName == null)
            throw new IllegalStateException("Name not defined in this state");
        return curName;
    }

    /**
     * Returns a value string whose meaning depends upon the current state.
     *
     * @throws  IllegalStateException
     *          If there is no value data for the current parser state
     */
    public String value() {
        if (curValue == null)
            throw new IllegalStateException("Value not defined in this state");
        return curValue;
    }

    /**
     * Returns the URI string of the current component.
     *
     * @throws  IllegalStateException
     *          If there is no URI for the current component
     */
    public String uriString() {
        if (!namespaceAware) {
            return null;
        } else if (curURI == null) {
            throw new IllegalStateException("Value not defined in this state");
        }
        return curURI;
    }

    /**
     * Returns the line number of the current component,
     * or <tt>-1</tt> if the line number is not known.
     */
    public int line() {
        return curLine;
    }

    /**
     * Returns the column number of the current component,
     * or <tt>-1</tt> if the column number is not known.
     */
    public int column() {
        return curCol;
    }

    /**
     * Returns the public identifer of the document being parsed,
     * or <tt>null</tt> if it has none.
     */
    public String publicId() {
        return publicId;
    }

    /**
     * Returns the system identifer of the document being parsed,
     * or <tt>null</tt> if it has none.
     */
    public String systemId() {
        return systemId;
    }

    /**
     * Returns the <i>validating</i> property of this parser.
     *
     * @return  <tt>true</tt> if, and only if, this parser
     *          will perform validation
     */
    public boolean isValidating() {
        return this.validating;
    }

    /**
     * Returns the <i>coalescing</i> property of this parser.
     *
     * @return  <tt>true</tt> if, and only if, this parser 
     *          will coalesce adjacent runs of character data
     */
    public boolean isCoalescing() {
        return this.coalescing;
    }

    /**
     * Returns the <i>namespaceAware</i> property of this parser.
     *
     * @return  <tt>true</tt> if, and only if, this parser will
     *          support namespace
     */
    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    /**
     * Constructs a string describing the current state of this parser,
     * suitable for use in an error message or an exception detail string.
     *
     * @param   articleNeeded
     *          Whether an appropriate article ("a", "an", "some", or "the") is
     *          to be prepended to the description string
     *
     * @returns  A string describing the given parser state.
     */
    public String describe(boolean articleNeeded) {
        return describe(cur, curName, curValue, articleNeeded);
    }

}

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

import java.io.InputStream;

import javax.xml.stream.*;
import javax.xml.namespace.QName;

import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

import com.sun.xml.fastinfoset.sax.AttributesHolder;
import com.sun.xml.fastinfoset.stax.StAXDocumentParser;

/**
 * <p> XMLReader provides a high-level streaming parser interface
 * for reading XML documents. </p>
 *
 * <p> The {@link #next} method is used to read events from the XML document. </p>
 *
 * <p> Each time it is called, {@link #next} returns the new state of the reader. </p>
 *
 * <p> Possible states are: BOF, the initial state, START, denoting the start
 * tag of an element, END, denoting the end tag of an element, CHARS, denoting
 * the character content of an element, PI, denoting a processing instruction,
 * EOF, denoting the end of the document. </p>
 *
 * <p> Depending on the state the reader is in, one or more of the following
 * query methods will be meaningful: {@link #getName}, {@link #getURI},
 * {@link #getLocalName}, {@link #getAttributes}, {@link #getValue}. </p>
 *
 * <p> Elements visited by a XMLReader are tagged with unique IDs. The ID of the
 * current element can be found by calling {@link #getElementId}. </p>
 *
 * <p> A XMLReader is always namespace-aware, and keeps track of the namespace
 * declarations which are in scope at any time during streaming. The
 * {@link #getURI(java.lang.String)} method can be used to find the URI
 * associated to a given prefix in the current scope. </p>
 *
 * <p> XMLReaders can be created using a {@link XMLReaderFactory}. </p>
 *
 * <p> Some utility methods, {@link #nextContent} and {@link #nextElementContent}
 * make it possible to ignore whitespace and processing instructions with
 * minimum impact on the client code. </p>
 *
 * <p> Similarly, the {@link #skipElement} and {@link #skipElement(int elementId)}
 * methods allow to skip to the end tag of an element ignoring all its content. </p>
 *
 * <p> Finally, the {@link #recordElement} method can be invoked when the XMLReader
 * is positioned on the start tag of an element to record the element's contents
 * so that they can be played back later. </p>
 *
 * @see XMLReaderFactory
 *
 * @author JAX-RPC Development Team
 */
public final class FastInfosetReader extends StAXDocumentParser implements XMLReader {

    /**
     * Current state of the reader.
     */
    int _state;
           
    /**
     * Stack of element ids.
     */
    ElementIdStack _elementIds;
    
    /**
     * Current element id.
     */
    int _elementId;
    
    AttributesAdapter _attrsAdapter;

    /**
     * Initialize a FastInfosetReader instance. Note that reset() is called 
     * by constructor in base class.
     */
    public FastInfosetReader(InputStream is) {
        _attrsAdapter = new AttributesAdapter();
        setInputStream(is);
    }
    
    public void reset() {
        super.reset();
        _state = BOF;
        if (_elementIds == null) {
            _elementIds = new ElementIdStack();
        } else {
            _elementIds.reset();
        }
        _elementId = 0;
    }
    
    /**
     * Return the next state of the XMLReader.
     *
     * The return value is one of: START, END, CHARS, PI, EOF.
     */
    public int next() {
        if (_state == EOF) {
            return EOF;
        }
        
        try {
            int readerEvent = super.next();
            
            while (readerEvent != XMLStreamConstants.END_DOCUMENT) {            
                switch (readerEvent) {
                    case XMLStreamConstants.START_ELEMENT:
                        _elementId = _elementIds.pushNext();
                        return (_state = START);
                    case XMLStreamConstants.END_ELEMENT:
                        _elementId = _elementIds.pop();
                        return (_state = END);
                    case XMLStreamConstants.CDATA:
                    case XMLStreamConstants.CHARACTERS:
                        return (_state = CHARS);
                    case XMLStreamConstants.PROCESSING_INSTRUCTION:
                        return (_state = PI);
                    default:
                        // falls through ignoring event
                }
                readerEvent = super.next();
            }
        }
        catch (XMLStreamException e) {
            throw wrapException(e);
        }
        
        return (_state = EOF);
    }
    
    public int nextElementContent() {
        int state = nextContent();
        if (state == CHARS) {
            throw new XMLReaderException(
                "xmlreader.unexpectedCharacterContent", getValue());
        }
        return state;
    }

    public int nextContent() {
        for (;;) {
            int state = next();
            switch (state) {
                case START :
                case END :
                case EOF :
                    return state;
                case CHARS :
                    if (_characters != null && !isWhiteSpace()) {
                        return CHARS;
                    }
                    continue;
                case PI :
                    continue;
            }
        }
    }
    
    /**
     * Return the current state of the XMLReader.
     *
     */
    public int getState() {
        return _state;
    }

    /**
     * Return the current URI.
     *
     * <p> Meaningful only when the state is one of: START, END. </p>
     */
    public String getURI() {
        return getNamespaceURI();
    }

    /**
     * Return the current attribute list.
     *
     * <p> Meaningful only when the state is one of: START. </p>
     *
     * <p> The returned {@link Attributes} object belong to the XMLReader and is
     * only guaranteed to be valid until the {@link #next} method is called,
     * directly or indirectly.</p>
     */
    public Attributes getAttributes() {
        return _attrsAdapter.setTarget(_attributes);
    }

    /**
     * Return the current value.
     *
     * <p> Meaningful only when the state is one of: CHARS, PI. </p>
     */
    public String getValue() {
        return (_state == PI) ? getPIData() : getText();
    }

    /**
     * Return the current element ID.
     */
    public int getElementId() {
        return _elementId;
    }

    /**
     * Return the current line number.
     *
     * <p> Due to aggressive parsing, this value may be off by a few lines. </p>
     */
    public int getLineNumber() {
        return -1;      // not available
    }

    /**
     * Records the current element and leaves the reader positioned on its end tag.
     *
     * <p> The XMLReader must be positioned on the start tag of the element.
     * The returned reader will play back all events starting with the
     * start tag of the element and ending with its end tag. </p>
     */
    public XMLReader recordElement() {
        throw new UnsupportedOperationException("recordElement()");
    }

    /**
     * Skip all nodes up to the end tag of the element with the current element ID.
     */
    public void skipElement() {
        skipElement(getElementId());
    }

    /**
     * Skip all nodes up to the end tag of the element with the given element ID.
     */
    public void skipElement(int elementId) {
        while (!(_state == EOF ||
                (_state == END && _elementId == elementId))) {
            next();
        }
    }

    /**
     * Close the XMLReader.
     *
     * <p> All subsequent calls to {@link #next} will return EOF. </p>
     */
    public void close() {
        try {
            _state = EOF;
            super.close();
        } 
        catch (XMLStreamException e) {
            throw wrapException(e);
        }
    }
    
    // -- Implementation methods ----------------------------------------------
    
    private XMLReaderException wrapException(XMLStreamException e) {
        return new XMLReaderException(
            "xmlreader.ioException",
            new LocalizableExceptionAdapter(e));
    }
    
    
    // -- AttributesAdapter class ----------------------------------------
    
    static final class AttributesAdapter implements Attributes {       
        AttributesHolder _attr;
        
        public AttributesAdapter() {
        }

        public final AttributesAdapter setTarget(AttributesHolder attr) {
            _attr = attr;
            return this;
        }

        public final int getLength() {
            return _attr.getLength();
        }

        public final boolean isNamespaceDeclaration(int index) {
            // No namespace declarations in underlying structure
            return false;
        }

        public final QName getName(int index) {
            return _attr.getQualifiedName(index).getQName();
        }

        public final String getURI(int index) {
            return _attr.getURI(index);
        }

        public final String getLocalName(int index) {
            return _attr.getLocalName(index);
        }

        public final String getPrefix(int index) {
            return _attr.getPrefix(index);
        }

        public final String getValue(int index) {
            return _attr.getValue(index);
        }

        public final int getIndex(QName name) {
            return _attr.getIndex(name.getNamespaceURI(), name.getLocalPart());
        }

        public final int getIndex(String uri, String localName) {
            return _attr.getIndex(uri, localName);
        }

        public final int getIndex(String localName) {
            return _attr.getIndex(localName);
        }

        public final String getValue(QName name) {
            return _attr.getValue(name.getNamespaceURI(), name.getLocalPart());
        }

        public final String getValue(String uri, String localName) {
            return _attr.getValue(uri, localName);
        }

        public final String getValue(String localName) {
            return _attr.getValue(localName);
        }

        public final String toString() {
            StringBuffer attributes = new StringBuffer();
            for (int i=0; i<getLength(); ++i) {
                if (i != 0) {
                    attributes.append("\n");
                }
                attributes.append(getURI(i)+":"+getLocalName(i)+" = "+getValue(i));
            }
            return attributes.toString();
        }
    }
}

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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.sun.xml.rpc.util.xml.XmlUtil;
import com.sun.xml.rpc.wsdl.document.Definitions;
import com.sun.xml.rpc.wsdl.document.Import;
import com.sun.xml.rpc.wsdl.document.WSDLDocument;
import com.sun.xml.rpc.wsdl.document.schema.Schema;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
import com.sun.xml.rpc.wsdl.framework.Extensible;
import com.sun.xml.rpc.wsdl.framework.Extension;
import com.sun.xml.rpc.wsdl.framework.ParserContext;
import com.sun.xml.rpc.wsdl.framework.WriterContext;

/**
 * WSDL Utilities.
 *
 * @author JAX-RPC Development Team
 */
public class WSDLUtil implements com.sun.xml.rpc.spi.tools.WSDLUtil {
    public WSDLUtil() {
    }

    /**
     * Collect all relative imports from a web service's main wsdl document.
     *
     *@param wsdlRelativeImports outupt param in which wsdl relative imports 
     * will be added
     *
     *@param schemaRelativeImports outupt param in which schema relative 
     * imports will be added
     */
    public void getRelativeImports(
        URL wsdlURL,
        Collection wsdlRelativeImports,
        Collection schemaRelativeImports)
        throws IOException {

        // Parse the wsdl document to find all import statements
        InputStream wsdlInputStream =
            new BufferedInputStream(wsdlURL.openStream());
        InputSource wsdlDocumentSource = new InputSource(wsdlInputStream);
        WSDLParserOverride wsdlParser = new WSDLParserOverride();
        // We only want to grab the import statements in the initial 
        // wsdl document. No need to fully resolve them.
        wsdlParser.setFollowImports(false);
        WSDLDocument wsdlDoc = wsdlParser.parse(wsdlDocumentSource);

        for (Iterator iter = wsdlDoc.getDefinitions().imports();
            iter.hasNext();
            ) {
            Import next = (Import) iter.next();
            String location = next.getLocation();
            // If it's a relative import
            if ((location.indexOf(":") == -1)) {
                wsdlRelativeImports.add(next);
            }
        }

        Collection schemaImports = wsdlParser.getSchemaImports();
        for (Iterator iter = schemaImports.iterator(); iter.hasNext();) {
            Import next = (Import) iter.next();
            String location = next.getLocation();
            // If it's a relative import
            if ((location.indexOf(":") == -1)) {
                schemaRelativeImports.add(next);
            }
        }

        wsdlInputStream.close();

        return;
    }

    /**
     * Subclass of WSDLParser that skips processing of imports.  Only
     * needed temporarily until jaxrpc code uses value of setFollowImports()
     */
    private static class WSDLParserOverride extends WSDLParser {

        private SchemaExtensionHandlerOverride schemaHandler;

        public WSDLParserOverride() {
            super();
            schemaHandler = new SchemaExtensionHandlerOverride();
            // Override the schema handler
            register(schemaHandler);
        }

        public Collection getSchemaImports() {
            return schemaHandler.getImports();
        }

        protected Definitions parseDefinitions(
            ParserContext context,
            InputSource source,
            String expectedTargetNamespaceURI) {
            Definitions definitions =
                parseDefinitionsNoImport(
                    context,
                    source,
                    expectedTargetNamespaceURI);
            return definitions;
        }
    }

    private static class SchemaExtensionHandlerOverride
        extends ExtensionHandler {

        private SchemaParserOverride parser;

        public SchemaExtensionHandlerOverride() {
            parser = new SchemaParserOverride();
        }

        public Collection getImports() {
            return parser.getImports();
        }

        public String getNamespaceURI() {
            return Constants.NS_XSD;
        }

        public boolean doHandleExtension(
            ParserContext context,
            Extensible parent,
            Element e) {
            if (XmlUtil.matchesTagNS(e, SchemaConstants.QNAME_SCHEMA)) {
                parent.addExtension(parser.parseSchema(context, e, null));
                return true;
            } else {
                return false;
            }
        }

        public void doHandleExtension(
            WriterContext context,
            Extension extension)
            throws IOException {
            throw new IllegalArgumentException("unsupported operation");
        }
    }

    private static class SchemaParserOverride extends SchemaParser {
        private Collection imports = new HashSet();

        public Collection getImports() {
            return imports;
        }

        protected void processImports(
            ParserContext context,
            InputSource src,
            Schema schema) {
            for (Iterator iter = schema.getContent().children();
                iter.hasNext();
                ) {
                SchemaElement child = (SchemaElement) iter.next();
                if (child.getQName().equals(SchemaConstants.QNAME_IMPORT)) {
                    String location =
                        child.getValueOfAttributeOrNull(
                            Constants.ATTR_SCHEMA_LOCATION);
                    String namespace =
                        child.getValueOfAttributeOrNull(
                            Constants.ATTR_NAMESPACE);
                    if ((location != null) && (namespace != null)) {
                        Import schemaImport = new Import();
                        schemaImport.setLocation(location);
                        schemaImport.setNamespace(namespace);
                        imports.add(schemaImport);
                    }
                }
            }
        }
    }
}

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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.wsdl.document.schema.Schema;
import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
import com.sun.xml.rpc.wsdl.document.schema.SchemaDocument;
import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
import com.sun.xml.rpc.wsdl.framework.WriterContext;

/**
 * A writer for XML Schema fragments within a WSDL document.
 *
 * @author JAX-RPC Development Team
 */
public class SchemaWriter {

    public SchemaWriter() {
    }

    public void write(SchemaDocument document, OutputStream os)
        throws IOException {
        WriterContext context = new WriterContext(os);
        writeSchema(context, document.getSchema());
        context.flush();
    }

    public void writeSchema(WriterContext context, Schema schema)
        throws IOException {
        context.push();
        try {
            writeTopSchemaElement(context, schema);
        } catch (Exception e) {
        } finally {
            context.pop();
        }
    }

    protected void writeTopSchemaElement(WriterContext context, Schema schema)
        throws IOException {
        SchemaElement schemaElement = schema.getContent();
        QName name = schemaElement.getQName();

        // make sure that all namespaces we expect are actually declared
        for (Iterator iter = schema.prefixes(); iter.hasNext();) {
            String prefix = (String) iter.next();
            String expectedURI = schema.getURIForPrefix(prefix);
            if (!expectedURI.equals(context.getNamespaceURI(prefix))) {
                context.declarePrefix(prefix, expectedURI);
            }
        }

        for (Iterator iter = schemaElement.prefixes(); iter.hasNext();) {
            String prefix = (String) iter.next();
            String uri = schemaElement.getURIForPrefix(prefix);
            context.declarePrefix(prefix, uri);
        }

        context.writeStartTag(name);

        for (Iterator iter = schemaElement.attributes(); iter.hasNext();) {
            SchemaAttribute attribute = (SchemaAttribute) iter.next();
            if (attribute.getNamespaceURI() == null) {
                context.writeAttribute(
                    attribute.getLocalName(),
                    attribute.getValue(context));
            } else {
                context.writeAttribute(
                    context.getQNameString(attribute.getQName()),
                    attribute.getValue(context));
            }
        }

        context.writeAllPendingNamespaceDeclarations();

        for (Iterator iter = schemaElement.children(); iter.hasNext();) {
            SchemaElement child = (SchemaElement) iter.next();
            writeSchemaElement(context, child);
        }

        context.writeEndTag(name);
    }

    protected void writeSchemaElement(
        WriterContext context,
        SchemaElement schemaElement)
        throws IOException {
        QName name = schemaElement.getQName();

        if (schemaElement.declaresPrefixes()) {
            context.push();
        }

        context.writeStartTag(name);

        if (schemaElement.declaresPrefixes()) {
            for (Iterator iter = schemaElement.prefixes(); iter.hasNext();) {
                String prefix = (String) iter.next();
                String uri = schemaElement.getURIForPrefix(prefix);
                context.writeNamespaceDeclaration(prefix, uri);
                context.declarePrefix(prefix, uri);
            }
        }

        for (Iterator iter = schemaElement.attributes(); iter.hasNext();) {
            SchemaAttribute attribute = (SchemaAttribute) iter.next();
            if (attribute.getNamespaceURI() == null) {
                context.writeAttribute(
                    attribute.getLocalName(),
                    attribute.getValue(context));
            } else {
                context.writeAttribute(
                    context.getQNameString(attribute.getQName()),
                    attribute.getValue(context));
            }
        }

        for (Iterator iter = schemaElement.children(); iter.hasNext();) {
            SchemaElement child = (SchemaElement) iter.next();
            writeSchemaElement(context, child);
        }

        context.writeEndTag(name);

        if (schemaElement.declaresPrefixes()) {
            context.pop();
        }
    }
}

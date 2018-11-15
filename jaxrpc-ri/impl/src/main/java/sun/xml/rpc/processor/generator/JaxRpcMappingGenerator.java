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

package com.sun.xml.rpc.processor.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.sun.xml.rpc.processor.ProcessorAction;
import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.generator.nodes.JavaWsdlMappingNode;
import com.sun.xml.rpc.processor.generator.nodes.JaxRpcMappingTagNames;
import com.sun.xml.rpc.processor.model.Model;

/**
 *
 * @author  Qingqing Ouyang
 */
public class JaxRpcMappingGenerator implements ProcessorAction {

    private boolean debug = false;
    private File mappingFile;

    public JaxRpcMappingGenerator(File mappingFile) {
        this.mappingFile = mappingFile;
    }

    public void perform(
        Model model,
        Configuration config,
        Properties options) {

        Document document = buildMapping(model, config);
        write(document, mappingFile);
    }

    private Document buildMapping(Model model, Configuration config) {
        debug("building mapping");

        Document root = newDocument();
        try {
            (new JavaWsdlMappingNode()).write(
                root,
                JaxRpcMappingTagNames.JAVA_WSDL_MAPPING,
                model,
                config);
        } catch (Exception ex) {
            //XXX FIXME. i18n JAXRPC logger ??
            ex.printStackTrace();
            throw new RuntimeException(ex.toString());
        }
        return root;
    }

    /**
     * Creates and returns a new DOM document based on the current 
     * configuration.
     *
     * @return the new DOM Document object
     */
    private Document newDocument() {
        try {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            DOMImplementation domImplementation =
                builder.getDOMImplementation();

            Document document = builder.newDocument();
            return document;
        } catch (Exception e) {
            //XXX FIXME. i18n JAXRPC logger ??
            e.printStackTrace();
        }
        return null;
    }

    private void write(Document document, final File resultFile) {
        try {
            FileOutputStream out = new FileOutputStream(resultFile);
            Result output = new StreamResult(out);

            Source source = new DOMSource(document);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount",
                "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(source, output);

            out.close();

        } catch (Exception e) {
            //XXX FIXME. i18n JAXRPC logger ??
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
    }

    private void debug(String msg) {
        if (debug) {
            System.out.println("[JaxRpcMappingGenerator] --> " + msg);
        }
    }
}

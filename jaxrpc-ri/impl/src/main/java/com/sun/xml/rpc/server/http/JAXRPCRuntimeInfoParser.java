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

package com.sun.xml.rpc.server.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderException;
import com.sun.xml.rpc.streaming.XMLReaderFactory;

/**
 * @author JAX-RPC Development Team
 */
public class JAXRPCRuntimeInfoParser {

    public JAXRPCRuntimeInfoParser(ClassLoader cl) {
        classLoader = cl;
    }

    public JAXRPCRuntimeInfo parse(InputStream is) {
        try {
            XMLReader reader =
                XMLReaderFactory.newInstance().createXMLReader(is);
            reader.next();
            return parseEndpoints(reader);
        } catch (XMLReaderException e) {
            throw new JAXRPCServletException("runtime.parser.xmlReader", e);
        }
    }

    protected JAXRPCRuntimeInfo parseEndpoints(XMLReader reader) {
        if (!reader.getName().equals(QNAME_ENDPOINTS)) {
            failWithFullName("runtime.parser.invalidElement", reader);
        }

        JAXRPCRuntimeInfo info = new JAXRPCRuntimeInfo();
        List endpoints = new ArrayList();

        String version = getMandatoryNonEmptyAttribute(reader, ATTR_VERSION);
        if (!version.equals(ATTRVALUE_VERSION_1_0)) {
            failWithLocalName(
                "runtime.parser.invalidVersionNumber",
                reader,
                version);
        }

        while (reader.nextElementContent() != XMLReader.END) {
            if (reader.getName().equals(QNAME_ENDPOINT)) {
                RuntimeEndpointInfo rei = new RuntimeEndpointInfo();
                rei.setName(getMandatoryNonEmptyAttribute(reader, ATTR_NAME));
                String interfaceName =
                    getMandatoryNonEmptyAttribute(reader, ATTR_INTERFACE);
                rei.setRemoteInterface(loadClass(interfaceName));
                String implementationName =
                    getMandatoryNonEmptyAttribute(reader, ATTR_IMPLEMENTATION);
                rei.setImplementationClass(loadClass(implementationName));
                String tieName =
                    getMandatoryNonEmptyAttribute(reader, ATTR_TIE);
                rei.setTieClass(loadClass(tieName));
                rei.setModelFileName(getAttribute(reader, ATTR_MODEL));
                rei.setWSDLFileName(getAttribute(reader, ATTR_WSDL));
                rei.setServiceName(getQNameAttribute(reader, ATTR_SERVICE));
                rei.setPortName(getQNameAttribute(reader, ATTR_PORT));
                rei.setUrlPattern(
                    getMandatoryNonEmptyAttribute(reader, ATTR_URL_PATTERN));
                ensureNoContent(reader);
                rei.setDeployed(true);
                endpoints.add(rei);
            } else {
                failWithLocalName("runtime.parser.invalidElement", reader);
            }
        }

        reader.close();

        info.setEndpoints(endpoints);
        return info;
    }

    protected String getAttribute(XMLReader reader, String name) {
        Attributes attributes = reader.getAttributes();
        String value = attributes.getValue(name);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    protected QName getQNameAttribute(XMLReader reader, String name) {
        String value = getAttribute(reader, name);
        if (value == null || value.equals("")) {
            return null;
        } else {
            return QName.valueOf(value);
        }
    }

    protected String getNonEmptyAttribute(XMLReader reader, String name) {
        String value = getAttribute(reader, name);
        if (value != null && value.equals("")) {
            failWithLocalName(
                "runtime.parser.invalidAttributeValue",
                reader,
                name);
        }
        return value;
    }

    protected String getMandatoryAttribute(XMLReader reader, String name) {
        String value = getAttribute(reader, name);
        if (value == null) {
            failWithLocalName("runtime.parser.missing.attribute", reader, name);
        }
        return value;
    }

    protected String getMandatoryNonEmptyAttribute(
        XMLReader reader,
        String name) {
        String value = getAttribute(reader, name);
        if (value == null) {
            failWithLocalName("runtime.parser.missing.attribute", reader, name);
        } else if (value.equals("")) {
            failWithLocalName(
                "runtime.parser.invalidAttributeValue",
                reader,
                name);
        }
        return value;
    }

    protected static void ensureNoContent(XMLReader reader) {
        if (reader.nextElementContent() != XMLReader.END) {
            fail("runtime.parser.unexpectedContent", reader);
        }
    }

    protected static void fail(String key, XMLReader reader) {
        logger.log(Level.SEVERE, key + reader.getLineNumber());
        throw new JAXRPCServletException(
            key,
            Integer.toString(reader.getLineNumber()));
    }

    protected static void failWithFullName(String key, XMLReader reader) {
        throw new JAXRPCServletException(
            key,
            new Object[] {
                Integer.toString(reader.getLineNumber()),
                reader.getName().toString()});
    }

    protected static void failWithLocalName(String key, XMLReader reader) {
        throw new JAXRPCServletException(
            key,
            new Object[] {
                Integer.toString(reader.getLineNumber()),
                reader.getLocalName()});
    }

    protected static void failWithLocalName(
        String key,
        XMLReader reader,
        String arg) {
        throw new JAXRPCServletException(
            key,
            new Object[] {
                Integer.toString(reader.getLineNumber()),
                reader.getLocalName(),
                arg });
    }

    protected Class loadClass(String name) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new JAXRPCServletException(
                "runtime.parser.classNotFound",
                name);
        }
    }

    protected ClassLoader classLoader;

    public static final String NS_RUNTIME =
        "http://java.sun.com/xml/ns/jax-rpc/ri/runtime";

    public static final QName QNAME_ENDPOINTS =
        new QName(NS_RUNTIME, "endpoints");
    public static final QName QNAME_ENDPOINT =
        new QName(NS_RUNTIME, "endpoint");

    public static final String ATTR_VERSION = "version";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_INTERFACE = "interface";
    public static final String ATTR_IMPLEMENTATION = "implementation";
    public static final String ATTR_TIE = "tie";
    public static final String ATTR_MODEL = "model";
    public static final String ATTR_WSDL = "wsdl";
    public static final String ATTR_SERVICE = "service";
    public static final String ATTR_PORT = "port";
    public static final String ATTR_URL_PATTERN = "urlpattern";

    public static final String ATTRVALUE_VERSION_1_0 = "1.0";
    private static final Logger logger =
        Logger.getLogger(
            com.sun.xml.rpc.util.Constants.LoggingDomain + ".server.http");
}

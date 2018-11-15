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

package com.sun.xml.rpc.client.dii.webservice.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.rpc.client.dii.webservice.WebService;
import com.sun.xml.rpc.client.dii.webservice.WebServicesClient;
import com.sun.xml.rpc.client.dii.webservice.WebServicesClientException;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderException;
import com.sun.xml.rpc.streaming.XMLReaderFactory;
import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;

/**
 * @author JAX-RPC Development Team
 */
public class WebServicesClientParser {
    private LocalizableMessageFactory messageFactory =
        new LocalizableMessageFactory("com.sun.xml.rpc.resources.client");

    public WebServicesClientParser() {
    }

    public WebServicesClient parse(InputStream is)
        throws WebServicesClientException {
        try {
            XMLReader reader =
                XMLReaderFactory.newInstance().createXMLReader(is);
            reader.next();
            return parseWebServicesClient(reader);
        } catch (XMLReaderException e) {
            throw new WebServicesClientException("client.xmlReader", e);
        }
    }

    protected WebServicesClient parseWebServicesClient(XMLReader reader) {
        if (!reader.getName().equals(Constants.QNAME_CLIENT)) {
            ParserUtil.failWithFullName("client.invalidElement", reader);
        }

        WebServicesClient client = new WebServicesClient();
        if (reader.getState() == XMLReader.START) {
            client.setWebServices(parseWebServices(reader));
        } else {
            ParserUtil.fail("client.missing.service", reader);
        }

        if (reader.nextElementContent() != XMLReader.EOF) {
            ParserUtil.fail("client.unexpectedContent", reader);
        }

        reader.close();
        return client;
    }

    protected List parseWebServices(XMLReader reader) {

        List webServices = new ArrayList();

        while (reader.nextElementContent() == XMLReader.START) {

            if (!reader.getName().equals(Constants.QNAME_SERVICE)) {
                ParserUtil.failWithFullName("service.invalidElement", reader);
            }

            String wsdlLocation =
                ParserUtil.getAttribute(reader, Constants.ATTR_WSDL_LOCATION);
            if (wsdlLocation == null) {
                ParserUtil.failWithLocalName(
                    "client.invalidwsdlLocation",
                    reader);
            }

            String model =
                ParserUtil.getAttribute(reader, Constants.ATTR_MODEL);
            if (model == null) {
                ParserUtil.failWithLocalName("client.invalidModel", reader);
            }

            WebService service = new WebService(wsdlLocation, model);
            webServices.add(service);

            if (reader.nextElementContent() != XMLReader.END) {
                ParserUtil.fail("client.unexpectedContent", reader);
            }
        }
        return webServices;
    }
}

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
import com.sun.xml.rpc.util.localization.Localizer;

/**
 *
 * @author JAX-RPC Development Team
 */
public class WSDLPublisher {

    public WSDLPublisher(
        ServletContext context,
        JAXRPCRuntimeInfo jaxrpcInfo) {
        this.servletContext = context;
        this.jaxrpcInfo = jaxrpcInfo;
        templatesByEndpointInfo = new HashMap();
        localizer = new Localizer();
        messageFactory =
            new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
    }

    public void handle(
        RuntimeEndpointInfo targetEndpoint,
        Map fixedUrlPatternEndpoints,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {
        Iterator urlPatterns = fixedUrlPatternEndpoints.keySet().iterator();
        String urlPattern = (String) urlPatterns.next();

        // need to find correct url pattern in map to create baseAddress
        while (targetEndpoint != fixedUrlPatternEndpoints.get(urlPattern)) {
            urlPattern = (String) urlPatterns.next();
        }
        response.setContentType("text/xml");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream outputStream = response.getOutputStream();
        String actualAddress =
            request.getScheme()
                + "://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getRequestURI();
        String baseAddress =
            actualAddress.substring(0, actualAddress.lastIndexOf(urlPattern));

        Templates templates;
        synchronized (this) {
            templates = (Templates) templatesByEndpointInfo.get(targetEndpoint);
            if (templates == null) {
                templates = createTemplatesFor(fixedUrlPatternEndpoints);
                templatesByEndpointInfo.put(targetEndpoint, templates);
            }
        }
        try {
            Iterator iter = fixedUrlPatternEndpoints.keySet().iterator();
            while (iter.hasNext()) {
                logger.fine(
                    localizer.localize(
                        messageFactory.getMessage(
                            "publisher.info.applyingTransformation",
                            baseAddress + iter.next())));
            }
            Source wsdlDocument =
                new StreamSource(
                    servletContext.getResourceAsStream(
                        targetEndpoint.getWSDLFileName()));
            Transformer transformer = templates.newTransformer();
            transformer.setParameter("baseAddress", baseAddress);
            transformer.transform(wsdlDocument, new StreamResult(outputStream));
        } catch (TransformerConfigurationException e) {
            throw new JAXRPCServletException("exception.cannotCreateTransformer");
        } catch (TransformerException e) {
            throw new JAXRPCServletException(
                "exception.transformationFailed",
                e.getMessageAndLocation());
        }
    }

    protected Templates createTemplatesFor(Map patternToPort) {
        try {
            // create the stylesheet
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");

            writer.write(
                "<xsl:transform version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\">\n");
            writer.write("<xsl:param name=\"baseAddress\"/>\n");

            writer.write(
                "<xsl:template match=\"/\"><xsl:apply-templates mode=\"copy\"/></xsl:template>\n");

            Iterator iter = patternToPort.keySet().iterator();
            while (iter.hasNext()) {
                String pattern = (String) iter.next();
                RuntimeEndpointInfo info =
                    (RuntimeEndpointInfo) patternToPort.get(pattern);
                writer.write(
                    "<xsl:template match=\"wsdl:definitions[@targetNamespace='");
                writer.write(info.getPortName().getNamespaceURI());
                writer.write("']/wsdl:service[@name='");
                writer.write(info.getServiceName().getLocalPart());
                writer.write("']/wsdl:port[@name='");
                writer.write(info.getPortName().getLocalPart());
                writer.write("']/soap:address\" mode=\"copy\">");
                writer.write("<soap:address><xsl:attribute name=\"location\">");
                writer.write(
                    "<xsl:value-of select=\"$baseAddress\"/>" + pattern);
                writer.write("</xsl:attribute></soap:address></xsl:template>");
            }

            writer.write(
                "<xsl:template match=\"@*|node()\" mode=\"copy\"><xsl:copy><xsl:apply-templates select=\"@*\" mode=\"copy\"/><xsl:apply-templates mode=\"copy\"/></xsl:copy></xsl:template>\n");
            writer.write("</xsl:transform>\n");
            writer.close();
            byte[] stylesheet = bos.toByteArray();
            Source stylesheetSource =
                new StreamSource(new ByteArrayInputStream(stylesheet));
            TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
            Templates templates =
                transformerFactory.newTemplates(stylesheetSource);
            return templates;
        } catch (Exception e) {
            throw new JAXRPCServletException("exception.templateCreationFailed");
        }
    }

    protected static void copyStream(InputStream istream, OutputStream ostream)
        throws IOException {
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = istream.read(buf)) != -1) {
            ostream.write(buf, 0, num);
        }
        ostream.flush();
    }

    private ServletContext servletContext;
    private Localizer localizer;
    private LocalizableMessageFactory messageFactory;
    private JAXRPCRuntimeInfo jaxrpcInfo;
    private Map templatesByEndpointInfo;
    private static final Logger logger =
        Logger.getLogger(
            com.sun.xml.rpc.util.Constants.LoggingDomain + ".server.http");
}

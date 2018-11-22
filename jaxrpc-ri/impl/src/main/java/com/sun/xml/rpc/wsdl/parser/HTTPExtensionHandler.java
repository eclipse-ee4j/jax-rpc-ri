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

import org.w3c.dom.Element;

import com.sun.xml.rpc.util.xml.XmlUtil;
import com.sun.xml.rpc.wsdl.document.http.HTTPAddress;
import com.sun.xml.rpc.wsdl.document.http.HTTPBinding;
import com.sun.xml.rpc.wsdl.document.http.HTTPConstants;
import com.sun.xml.rpc.wsdl.document.http.HTTPOperation;
import com.sun.xml.rpc.wsdl.document.http.HTTPUrlEncoded;
import com.sun.xml.rpc.wsdl.document.http.HTTPUrlReplacement;
import com.sun.xml.rpc.wsdl.framework.Extensible;
import com.sun.xml.rpc.wsdl.framework.Extension;
import com.sun.xml.rpc.wsdl.framework.ParserContext;
import com.sun.xml.rpc.wsdl.framework.WriterContext;

/**
 * The HTTP extension handler for WSDL.
 *
 * @author JAX-RPC Development Team
 */
public class HTTPExtensionHandler extends ExtensionHandlerBase {

    public HTTPExtensionHandler() {
    }

    public String getNamespaceURI() {
        return Constants.NS_WSDL_HTTP;
    }

    protected boolean handleDefinitionsExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false; // keep compiler happy
    }

    protected boolean handleTypesExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false; // keep compiler happy
    }

    protected boolean handleBindingExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_BINDING)) {
            context.push();
            context.registerNamespaces(e);

            HTTPBinding binding = new HTTPBinding();

            String verb = Util.getRequiredAttribute(e, Constants.ATTR_VERB);
            binding.setVerb(verb);

            parent.addExtension(binding);
            context.pop();
            context.fireDoneParsingEntity(HTTPConstants.QNAME_BINDING, binding);
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false; // keep compiler happy
        }
    }

    protected boolean handleOperationExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_OPERATION)) {
            context.push();
            context.registerNamespaces(e);

            HTTPOperation operation = new HTTPOperation();

            String location =
                Util.getRequiredAttribute(e, Constants.ATTR_LOCATION);
            operation.setLocation(location);

            parent.addExtension(operation);
            context.pop();
            context.fireDoneParsingEntity(
                HTTPConstants.QNAME_OPERATION,
                operation);
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false; // keep compiler happy
        }
    }

    protected boolean handleInputExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_URL_ENCODED)) {
            parent.addExtension(new HTTPUrlEncoded());
            return true;
        } else if (
            XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_URL_REPLACEMENT)) {
            parent.addExtension(new HTTPUrlReplacement());
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false; // keep compiler happy
        }
    }

    protected boolean handleOutputExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false; // keep compiler happy
    }

    protected boolean handleFaultExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false; // keep compiler happy
    }

    protected boolean handleServiceExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false; // keep compiler happy
    }

    protected boolean handlePortExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_ADDRESS)) {
            context.push();
            context.registerNamespaces(e);

            HTTPAddress address = new HTTPAddress();

            String location =
                Util.getRequiredAttribute(e, Constants.ATTR_LOCATION);
            address.setLocation(location);

            parent.addExtension(address);
            context.pop();
            context.fireDoneParsingEntity(HTTPConstants.QNAME_ADDRESS, address);
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false; // keep compiler happy
        }
    }

    protected boolean handleMIMEPartExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false; // keep compiler happy
    }

    public void doHandleExtension(WriterContext context, Extension extension)
        throws IOException {
        if (extension instanceof HTTPAddress) {
            HTTPAddress address = (HTTPAddress) extension;
            context.writeStartTag(address.getElementName());
            context.writeAttribute(
                Constants.ATTR_LOCATION,
                address.getLocation());
            context.writeEndTag(address.getElementName());
        } else if (extension instanceof HTTPBinding) {
            HTTPBinding binding = (HTTPBinding) extension;
            context.writeStartTag(binding.getElementName());
            context.writeAttribute(Constants.ATTR_VERB, binding.getVerb());
            context.writeEndTag(binding.getElementName());
        } else if (extension instanceof HTTPOperation) {
            HTTPOperation operation = (HTTPOperation) extension;
            context.writeStartTag(operation.getElementName());
            context.writeAttribute(
                Constants.ATTR_LOCATION,
                operation.getLocation());
            context.writeEndTag(operation.getElementName());
        } else if (extension instanceof HTTPUrlEncoded) {
            context.writeStartTag(extension.getElementName());
            context.writeEndTag(extension.getElementName());
        } else if (extension instanceof HTTPUrlReplacement) {
            context.writeStartTag(extension.getElementName());
            context.writeEndTag(extension.getElementName());
        } else {
            throw new IllegalArgumentException();
        }
    }
}

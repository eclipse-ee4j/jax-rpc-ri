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

import org.w3c.dom.Element;

import com.sun.xml.rpc.wsdl.document.WSDLConstants;
import com.sun.xml.rpc.wsdl.document.mime.MIMEConstants;
import com.sun.xml.rpc.wsdl.framework.Extensible;
import com.sun.xml.rpc.wsdl.framework.ParserContext;
/**
 * A base class for WSDL extension handlers.
 *
 * @author JAX-RPC Development Team
 */
public abstract class ExtensionHandlerBase extends ExtensionHandler {

    protected ExtensionHandlerBase() {
    }

    public boolean doHandleExtension(
        ParserContext context,
        Extensible parent,
        Element e) {
        if (parent.getElementName().equals(WSDLConstants.QNAME_DEFINITIONS)) {
            return handleDefinitionsExtension(context, parent, e);
        } else if (parent.getElementName().equals(WSDLConstants.QNAME_TYPES)) {
            return handleTypesExtension(context, parent, e);
        } else if (
            parent.getElementName().equals(WSDLConstants.QNAME_BINDING)) {
            return handleBindingExtension(context, parent, e);
        } else if (
            parent.getElementName().equals(WSDLConstants.QNAME_OPERATION)) {
            return handleOperationExtension(context, parent, e);
        } else if (parent.getElementName().equals(WSDLConstants.QNAME_INPUT)) {
            return handleInputExtension(context, parent, e);
        } else if (
            parent.getElementName().equals(WSDLConstants.QNAME_OUTPUT)) {
            return handleOutputExtension(context, parent, e);
        } else if (parent.getElementName().equals(WSDLConstants.QNAME_FAULT)) {
            return handleFaultExtension(context, parent, e);
        } else if (
            parent.getElementName().equals(WSDLConstants.QNAME_SERVICE)) {
            return handleServiceExtension(context, parent, e);
        } else if (parent.getElementName().equals(WSDLConstants.QNAME_PORT)) {
            return handlePortExtension(context, parent, e);
        } else if (parent.getElementName().equals(MIMEConstants.QNAME_PART)) {
            return handleMIMEPartExtension(context, parent, e);
        } else {
            return false;
        }
    }

    protected abstract boolean handleDefinitionsExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleTypesExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleBindingExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleOperationExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleInputExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleOutputExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleFaultExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleServiceExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handlePortExtension(
        ParserContext context,
        Extensible parent,
        Element e);
    protected abstract boolean handleMIMEPartExtension(
        ParserContext context,
        Extensible parent,
        Element e);
}

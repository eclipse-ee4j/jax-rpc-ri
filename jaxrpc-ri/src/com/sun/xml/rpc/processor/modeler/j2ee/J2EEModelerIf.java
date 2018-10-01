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

package com.sun.xml.rpc.processor.modeler.j2ee;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.Operation;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.wsdl.document.Message;
import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase.ProcessSOAPOperationInfo;


public interface J2EEModelerIf {
    public LiteralType getElementTypeToLiteralType(QName elementType);
    public boolean useSuperExplicitServiceContextForDocLit(Message inputMessage);
    public boolean useSuperExplicitServiceContextForRpcLit(Message inputMessage);
    public boolean useSuperExplicitServiceContextForRpcEncoded(Message inputMessage);
    public boolean isSuperUnwrappable();
    public LiteralType getSuperElementTypeToLiteralType(QName elementType);
    public String getSuperJavaNameForOperation(Operation operation);
    public ProcessSOAPOperationInfo getInfo();
    public Message getSuperOutputMessage();
    public Message getSuperInputMessage();
    public SOAPBody getSuperSOAPRequestBody();
    public SOAPBody getSuperSOAPResponseBody();
    public JavaSimpleTypeCreator getJavaTypes();
}

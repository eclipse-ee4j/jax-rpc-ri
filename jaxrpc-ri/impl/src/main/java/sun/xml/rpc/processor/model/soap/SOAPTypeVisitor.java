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

package com.sun.xml.rpc.processor.model.soap;

/**
 *
 * @author JAX-RPC Development Team
 */
public interface SOAPTypeVisitor {
    
    public void visit(SOAPArrayType type) throws Exception;
    public void visit(SOAPCustomType type) throws Exception;
    public void visit(SOAPEnumerationType type) throws Exception;
    public void visit(SOAPSimpleType type) throws Exception;
    public void visit(SOAPAnyType type) throws Exception;
    public void visit(SOAPOrderedStructureType type) throws Exception;
    public void visit(SOAPUnorderedStructureType type) throws Exception;
    public void visit(RPCRequestOrderedStructureType type) throws Exception;
    public void visit(RPCRequestUnorderedStructureType type) throws Exception;
    public void visit(RPCResponseStructureType type) throws Exception;
    public void visit(SOAPListType type) throws Exception;
}

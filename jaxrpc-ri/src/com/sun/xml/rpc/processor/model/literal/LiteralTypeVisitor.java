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

package com.sun.xml.rpc.processor.model.literal;

/**
 *
 * @author JAX-RPC Development Team
 */
public interface LiteralTypeVisitor {
    
    public void visit(LiteralSimpleType type) throws Exception;
    public void visit(LiteralSequenceType type) throws Exception;
    public void visit(LiteralArrayType type) throws Exception;
    public void visit(LiteralAllType type) throws Exception;
    public void visit(LiteralFragmentType type) throws Exception;
    public void visit(LiteralEnumerationType type) throws Exception;
    
    //xsd:list
    public void visit(LiteralListType type) throws Exception;
    public void visit(LiteralIDType type) throws Exception;
    public void visit(LiteralArrayWrapperType type) throws Exception;
    public void visit(LiteralAttachmentType type) throws Exception;
}

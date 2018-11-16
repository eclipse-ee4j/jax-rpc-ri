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

package com.sun.xml.rpc.processor.schema;


/**
 *
 * @author JAX-RPC Development Team
 */
public interface ComponentVisitor {
    public void visit(AnnotationComponent component) throws Exception;
    public void visit(AttributeDeclarationComponent component) throws Exception;
    public void visit(AttributeGroupDefinitionComponent component)
        throws Exception;
    public void visit(AttributeUseComponent component) throws Exception;
    public void visit(ComplexTypeDefinitionComponent component)
        throws Exception;
    public void visit(ElementDeclarationComponent component) throws Exception;
    public void visit(IdentityConstraintDefinitionComponent component)
        throws Exception;
    public void visit(ModelGroupComponent component) throws Exception;
    public void visit(ModelGroupDefinitionComponent component) throws Exception;
    public void visit(NotationDeclarationComponent component) throws Exception;
    public void visit(ParticleComponent component) throws Exception;
    public void visit(SimpleTypeDefinitionComponent component) throws Exception;
    public void visit(WildcardComponent component) throws Exception;
}

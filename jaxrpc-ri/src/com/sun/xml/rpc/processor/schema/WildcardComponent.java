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

import java.util.Set;

/**
 *
 * @author JAX-RPC Development Team
 */
public class WildcardComponent extends Component {
    
    public static final int NAMESPACE_CONSTRAINT_ANY = 1;
    public static final int NAMESPACE_CONSTRAINT_NOT = 2;
    public static final int NAMESPACE_CONSTRAINT_NOT_ABSENT = 3;
    public static final int NAMESPACE_CONSTRAINT_SET = 4;
    public static final int NAMESPACE_CONSTRAINT_SET_OR_ABSENT = 5;
    
    public WildcardComponent() {}
    
    public void setProcessContents(Symbol s) {
        _processContents = s;
    }
    
    public int getNamespaceConstraintTag() {
        return _namespaceConstraintTag;
    }
    
    public void setNamespaceConstraintTag(int i) {
        _namespaceConstraintTag = i;
    }
    
    public String getNamespaceName() {
        return _namespaceName;
    }
    
    public void setNamespaceName(String s) {
        _namespaceName = s;
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private Symbol _processContents;
    private int _namespaceConstraintTag;
    private String _namespaceName;
    private Set _namespaceSet;
    private AnnotationComponent _annotation;
}

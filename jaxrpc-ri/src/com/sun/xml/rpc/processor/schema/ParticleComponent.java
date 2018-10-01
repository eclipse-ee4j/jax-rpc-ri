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
public class ParticleComponent extends Component {
    
    public static final int TERM_MODEL_GROUP = 1;
    public static final int TERM_WILDCARD = 2;
    public static final int TERM_ELEMENT = 3;
    
    public ParticleComponent() {}
    
    public int getMinOccurs() {
        return _minOccurs;
    }
    
    public void setMinOccurs(int i) {
        _minOccurs = i;
    }
    
    public int getMaxOccurs() {
        if (_maxOccurs == UNBOUNDED) {
            throw new IllegalStateException();
        }
        return _maxOccurs;
    }
    
    public void setMaxOccurs(int i) {
        _maxOccurs = i;
    }
    
    public boolean isMaxOccursUnbounded() {
        return _maxOccurs == UNBOUNDED;
    }
    
    public void setMaxOccursUnbounded() {
        _maxOccurs = UNBOUNDED;
    }
    
    public boolean doesNotOccur() {
        return _minOccurs == 0 && _maxOccurs == 0;
    }
    
    public boolean occursOnce() {
        return _minOccurs == 1 && _maxOccurs == 1;
    }
    
    public boolean occursAtMostOnce() {
        return _minOccurs <= 1 && _maxOccurs == 1;
    }
    
    public boolean occursAtLeastOnce() {
        return _minOccurs >= 1;
    }
    
    public boolean occursZeroOrMore() {
        return _minOccurs == 0 && _maxOccurs == UNBOUNDED;
    }
    
    public boolean occursOnceOrMore() {
        return _minOccurs == 1 && _maxOccurs == UNBOUNDED;
    }
    
    public boolean mayOccurMoreThanOnce() {
        return _maxOccurs > 1 || _maxOccurs == UNBOUNDED;
    }
    
    public int getTermTag() {
        return _termTag;
    }
    
    public void setTermTag(int i) {
        _termTag = i;
    }
    
    public ModelGroupComponent getModelGroupTerm() {
        return _modelGroupTerm;
    }
    
    public void setModelGroupTerm(ModelGroupComponent c) {
        _modelGroupTerm = c;
    }
    
    public ElementDeclarationComponent getElementTerm() {
        return _elementTerm;
    }
    
    public void setElementTerm(ElementDeclarationComponent c) {
        _elementTerm = c;
    }
    
    public WildcardComponent getWildcardTerm() {
        return _wildcardTerm;
    }
    
    public void setWildcardTerm(WildcardComponent c) {
        _wildcardTerm = c;
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    public boolean occursZeroOrOne() {
        return _minOccurs == 0 && _maxOccurs == 1;
    }
    
    private int _minOccurs;
    private int _maxOccurs;
    private int _termTag;
    private ModelGroupComponent _modelGroupTerm;
    private WildcardComponent _wildcardTerm;
    private ElementDeclarationComponent _elementTerm;
    
    private static final int UNBOUNDED = -1;
}

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ModelGroupComponent extends Component {
    
    public ModelGroupComponent() {
        _particles = new ArrayList();
    }
    
    public Symbol getCompositor() {
        return _compositor;
    }
    
    public void setCompositor(Symbol s) {
        _compositor = s;
    }
    
    public Iterator particles() {
        return _particles.iterator();
    }
    
    public void addParticle(ParticleComponent c) {
        _particles.add(c);
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private Symbol _compositor;
    private List _particles;
    private AnnotationComponent _annotation;
}

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

import com.sun.xml.rpc.util.NullIterator;
import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;

/**
 *
 * @author JAX-RPC Development Team
 */
public class AnnotationComponent extends Component {
    
    public AnnotationComponent() {}
    
    public void addApplicationInformation(SchemaElement element) {
        if (_applicationInformationElements == null) {
            _applicationInformationElements = new ArrayList();
        }
        
        _applicationInformationElements.add(element);
    }
    
    public void addUserInformation(SchemaElement element) {
        if (_userInformationElements == null) {
            _userInformationElements = new ArrayList();
        }
        
        _userInformationElements.add(element);
    }
    
    public Iterator attributes() {
        if (_attributes == null) {
            return NullIterator.getInstance();
        } else {
            return _attributes.iterator();
        }
    }
    
    public void addAttribute(SchemaAttribute attribute) {
        if (_attributes == null) {
            _attributes = new ArrayList();
        }
        
        _attributes.add(attribute);
    }
    
    public void accept(ComponentVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private List _applicationInformationElements;
    private List _userInformationElements;
    private List _attributes;
}

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

package com.sun.xml.rpc.processor.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaMethod;
import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;

/**
 *
 * @author JAX-RPC Development Team
 */
public class Operation extends ModelObject {
    
    public Operation() {}
    
    public Operation(QName name) {
        _name = name;
        _uniqueName = name.getLocalPart();
        _faultNames = new HashSet();
        _faults = new HashSet();
    }
    
    public QName getName() {
        return _name;
    }
    
    public void setName(QName n) {
        _name = n;
    }
    
    public String getUniqueName() {
        return _uniqueName;
    }
    
    public void setUniqueName(String s) {
        _uniqueName = s;
    }
    
    public Request getRequest() {
        return _request;
    }
    
    public void setRequest(Request r) {
        _request = r;
    }
    
    public Response getResponse() {
        return _response;
    }
    
    public void setResponse(Response r) {
        _response = r;
    }
    
    public boolean isOverloaded() {
        return !_name.getLocalPart().equals(_uniqueName);
    }
    
    public void addFault(Fault f) {
        if (_faultNames.contains(f.getName())) {
            throw new ModelException("model.uniqueness");
        }
        _faultNames.add(f.getName());
        _faults.add(f);
    }
    
    public Iterator getFaults() {
        return _faults.iterator();
    }
    
    public Set getFaultsSet() {
        return _faults;
    }
    
    /* serialization */
    public void setFaultsSet(Set s) {
        _faults = s;
        initializeFaultNames();
    }
    
    private void initializeFaultNames() {
        _faultNames = new HashSet();
        if (_faults != null) {
            for (Iterator iter = _faults.iterator(); iter.hasNext();) {
                Fault f = (Fault) iter.next();
                if (f.getName() != null && _faultNames.contains(f.getName())) {
                    throw new ModelException("model.uniqueness");
                }
                _faultNames.add(f.getName());
            }
        }
    }
    
    public Iterator getAllFaults() {
        Set allFaults = getAllFaultsSet();
        if (allFaults.size() == 0) {
            return null;
        }
        return allFaults.iterator();
    }
    
    public Set getAllFaultsSet() {
        Set transSet = new HashSet();
        transSet.addAll(_faults);
        Iterator iter = _faults.iterator();
        Fault fault;
        Set tmpSet;
        while (iter.hasNext()) {
            tmpSet = ((Fault)iter.next()).getAllFaultsSet();
            transSet.addAll(tmpSet);
        }
        return transSet;
    }
    
    public int getFaultCount() {
        return _faults.size();
    }
    
    public JavaMethod getJavaMethod() {
        return _javaMethod;
    }
    
    public void setJavaMethod(JavaMethod i) {
        _javaMethod = i;
    }
    
    public String getSOAPAction() {
        return _soapAction;
    }
    
    public void setSOAPAction(String s) {
        _soapAction = s;
    }
    
    public SOAPStyle getStyle() {
        return _style;
    }
    
    public void setStyle(SOAPStyle s) {
        _style = s;
    }
    
    public SOAPUse getUse() {
        return _use;
    }
    
    public void setUse(SOAPUse u) {
        _use = u;
    }
    
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
    
    private QName _name;
    private String _uniqueName;
    private Request _request;
    private Response _response;
    private JavaMethod _javaMethod;
    private String _soapAction;
    private SOAPStyle _style;
    private SOAPUse _use;
    private Set _faultNames;
    private Set _faults;
}

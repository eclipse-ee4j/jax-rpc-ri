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

import java.util.Iterator;

/**
 *
 * A model visitor incorporating all the logic required to walk through the model.
 *
 * @author JAX-RPC Development Team
 */
public class ExtendedModelVisitor {
    
    public ExtendedModelVisitor() {}
    
    public void visit(Model model) throws Exception {
        preVisit(model);
        for (Iterator iter = model.getServices(); iter.hasNext();) {
            Service service = (Service) iter.next();
            preVisit(service);
            for (Iterator iter2 = service.getPorts(); iter2.hasNext();) {
                Port port = (Port) iter2.next();
                preVisit(port);
                if (shouldVisit(port)) {
                    for (Iterator iter3 = port.getOperations();
                        iter3.hasNext();) {
                            
                        Operation operation = (Operation) iter3.next();
                        preVisit(operation);
                        Request request = operation.getRequest();
                        if (request != null) {
                            preVisit(request);
                            for (Iterator iter4 = request.getHeaderBlocks();
                                iter4.hasNext();) {
                                    
                                Block block = (Block) iter4.next();
                                visitHeaderBlock(block);
                            }
                            for (Iterator iter4 = request.getBodyBlocks();
                                iter4.hasNext();) {
                                    
                                Block block = (Block) iter4.next();
                                visitBodyBlock(block);
                            }
                            for (Iterator iter4 = request.getParameters();
                                iter4.hasNext();) {
                                    
                                Parameter parameter = (Parameter) iter4.next();
                                visit(parameter);
                            }
                            postVisit(request);
                        }
                        
                        Response response = operation.getResponse();
                        if (response != null) {
                            preVisit(response);
                            for (Iterator iter4 = response.getHeaderBlocks();
                                iter4.hasNext();) {
                                    
                                Block block = (Block) iter4.next();
                                visitHeaderBlock(block);
                            }
                            for (Iterator iter4 = response.getBodyBlocks();
                                iter4.hasNext();) {
                                    
                                Block block = (Block) iter4.next();
                                visitBodyBlock(block);
                            }
                            for (Iterator iter4 = response.getParameters();
                                iter4.hasNext();) {
                                    
                                Parameter parameter = (Parameter) iter4.next();
                                visit(parameter);
                            }
                            postVisit(response);
                        }
                        
                        for (Iterator iter4 = operation.getFaults();
                            iter4.hasNext();) {
                                
                            Fault fault = (Fault) iter4.next();
                            preVisit(fault);
                            visitFaultBlock(fault.getBlock());
                            postVisit(fault);
                        }
                        postVisit(operation);
                    }
                }
                postVisit(port);
            }
            postVisit(service);
        }
        postVisit(model);
    }
    
    protected boolean shouldVisit(Port port) {
        return true;
    }
    
    // these methods are intended for subclasses
    protected void preVisit(Model model) throws Exception {}
    protected void postVisit(Model model) throws Exception {}
    protected void preVisit(Service service) throws Exception {}
    protected void postVisit(Service service) throws Exception {}
    protected void preVisit(Port port) throws Exception {}
    protected void postVisit(Port port) throws Exception {}
    protected void preVisit(Operation operation) throws Exception {}
    protected void postVisit(Operation operation) throws Exception {}
    protected void preVisit(Request request) throws Exception {}
    protected void postVisit(Request request) throws Exception {}
    protected void preVisit(Response response) throws Exception {}
    protected void postVisit(Response response) throws Exception {}
    protected void preVisit(Fault fault) throws Exception {}
    protected void postVisit(Fault fault) throws Exception {}
    protected void visitBodyBlock(Block block) throws Exception {}
    protected void visitHeaderBlock(Block block) throws Exception {}
    protected void visitFaultBlock(Block block) throws Exception {}
    protected void visit(Parameter parameter) throws Exception {}
}

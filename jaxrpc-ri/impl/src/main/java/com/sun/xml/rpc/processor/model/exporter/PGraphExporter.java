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

package com.sun.xml.rpc.processor.model.exporter;

import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.ModelException;

/**
 * @author JAX-RPC Development Team
 */
public class PGraphExporter extends ExporterBase {
    
    public PGraphExporter(OutputStream s) {
        super(s);
    }
    
    public void doExport(PGraph g) {
        internalDoExport(g);
    }
    
    protected void internalDoExport(Object root) {
        initialize();
        PGraph graph = (PGraph) root;
        writer.startElement(graph.getName());
        if (graph.getVersion() != null) {
            writer.writeAttribute(ATTR_VERSION, graph.getVersion());
        }
        int id = getId(graph.getRoot());
        while (!obj2serializeStack.empty()) {
            Object obj = obj2serializeStack.pop();
            obj2serialize.remove(obj);
            visit(obj);
        }
        writer.endElement();
        writer.close();
    }
    
    protected void define(Object obj, Integer id) {
        if (obj instanceof PObject) {
            PObject anObject = (PObject) obj;
            writer.startElement(getDefineObjectName());
            writer.writeAttribute(ATTR_ID, id.toString());
            writer.writeAttribute(ATTR_TYPE, anObject.getType());
            writer.endElement();
            obj2serialize.add(obj);
            obj2serializeStack.push(obj);
        } else {
            super.define(obj, id);
        }
    }
    
    protected void failUnsupportedClass(Class klass) {
        throw new ModelException("model.exporter.unsupportedClass",
            klass.getName());
    }
    
    protected QName getContainerName() {
        
        // unused
        return null;
    }
    
    protected void visit(Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof PObject) {
            PObject anObject = (PObject) obj;
            for (Iterator iter = anObject.getPropertyNames(); iter.hasNext();) {
                String name = (String) iter.next();
                Object value = anObject.getProperty(name);
                property(name, obj, value);
            }
        } else {
            super.visit(obj);
        }
    }
    
}

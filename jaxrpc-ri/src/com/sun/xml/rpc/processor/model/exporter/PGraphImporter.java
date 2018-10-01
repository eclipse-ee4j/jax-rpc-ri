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

import java.io.InputStream;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.ModelException;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;

/**
 * @author JAX-RPC Development Team
 */
public class PGraphImporter extends ImporterBase {
    
    public PGraphImporter(InputStream s) {
        super(s);
    }
    
    public PGraph doImport() {
        return (PGraph) internalDoImport();
    }
    
    protected Object internalDoImport() {
        initialize();
        PGraph graph = new PGraph();
        
        reader.nextElementContent();
        if (reader.getState() != XMLReader.START) {
            failInvalidSyntax(reader);
        }
        graph.setName(reader.getName());
        String versionAttr = getRequiredAttribute(reader, ATTR_VERSION);
        graph.setVersion(versionAttr);
        while (reader.nextElementContent() != XMLReader.END) {
            if (reader.getName().equals(getDefineImmediateObjectName())) {
                parseDefineImmediateObject(reader);
            } else if (reader.getName().equals(getDefineObjectName())) {
                parseDefineObject(reader);
            } else if (reader.getName().equals(getPropertyName())) {
                parseProperty(reader);
            } else {
                failInvalidSyntax(reader);
            }
        }
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        
        // the root object is invariably the first
        graph.setRoot((PObject) id2obj.get(new Integer(1)));
        return graph;
    }
    
    protected void parseDefineObject(XMLReader reader) {
        String idAttr = getRequiredAttribute(reader, ATTR_ID);
        String typeAttr = getRequiredAttribute(reader, ATTR_TYPE);
        Integer id = parseId(reader, idAttr);
        if (getObjectForId(id) != null) {
            failInvalidId(reader, id);
        }
        PObject obj = createPObject();
        obj.setType(typeAttr);
        id2obj.put(id, obj);
        verifyNoContent(reader);
    }
    
    protected PObject createPObject() {
        return new PObject();
    }
    
    protected QName getContainerName() {
        
        // unused
        return null;
    }
    
    protected void property(XMLReader reader, Object subject,
        String name, Object value) {
            
        if (subject instanceof PObject) {
            PObject obj = (PObject) subject;
            obj.setProperty(name, value);
        } else {
            super.property(reader, subject, name, value);
        }
    }
    
    protected void failInvalidSyntax(XMLReader reader) {
        throw new ModelException("model.importer.syntaxError",
            Integer.toString(reader.getLineNumber()));
    }
    
    protected void failInvalidVersion(XMLReader reader, String version) {
        throw new ModelException("model.importer.invalidVersion",
            new Object[] { Integer.toString(reader.getLineNumber()), version });
    }
    
	protected void failInvalidMinorMinorOrPatchVersion(
		XMLReader reader,
		String targetVersion,
		String currentVersion) {
		throw new ModelException(
			"model.importer.invalidMinorMinorOrPatchVersion",
			new Object[] {
				Integer.toString(reader.getLineNumber()),
				targetVersion,
				currentVersion });
	}
    
    protected void failInvalidClass(XMLReader reader, String className) {
        throw new ModelException("model.importer.invalidClass",
            new Object[] { Integer.toString(reader.getLineNumber()),
                className });
    }
    
    protected void failInvalidId(XMLReader reader, Integer id) {
        throw new ModelException("model.importer.invalidId",
            new Object[] { Integer.toString(reader.getLineNumber()),
                id.toString()});
    }
    
    protected void failInvalidLiteral(XMLReader reader,
        String type, String value) {
            
        throw new ModelException("model.importer.invalidLiteral",
            Integer.toString(reader.getLineNumber()));
    }
    
    protected void failInvalidProperty(XMLReader reader, Object subject,
        String name, Object value) {
            
        throw new ModelException("model.importer.invalidProperty",
            Integer.toString(reader.getLineNumber()));
    }
    
}


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

package com.sun.xml.rpc.processor.config.parser;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.config.ConfigurationException;
import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.util.xml.XmlUtil;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ParserUtil {
    
    public static String getAttribute(XMLReader reader, String name) {
        Attributes attributes = reader.getAttributes();
        return attributes.getValue(name);
    }
    
    public static String getNonEmptyAttribute(XMLReader reader, String name) {
        String value = getAttribute(reader, name);
        if (value != null && value.equals("")) {
            failWithLocalName("configuration.invalidAttributeValue",
                reader, name);
        }
        return value;
    }
    
    public static String getMandatoryAttribute(XMLReader reader, String name) {
        String value = getAttribute(reader, name);
        if (value == null) {
            failWithLocalName("configuration.missing.attribute", reader, name);
        }
        return value;
    }
    
    public static String getMandatoryNonEmptyAttribute(XMLReader reader,
        String name) {
            
        String value = getAttribute(reader, name);
        if (value == null) {
            failWithLocalName("configuration.missing.attribute", reader, name);
        }
        else if (value.equals("")) {
            failWithLocalName("configuration.invalidAttributeValue",
                reader, name);
        }
        return value;
    }
    
    public static QName getQNameAttribute(XMLReader reader, String name) {
        String value = getAttribute(reader, name);
        
        if (value == null) {
            return null;
        }
        
        String prefix = XmlUtil.getPrefix(value);
        String uri = "";
        if (prefix != null) {
            uri = reader.getURI(prefix);
            if (uri == null) {
                failWithLocalName("configuration.invalidAttributeValue",
                    reader, name);
            }
        }
        String localPart = XmlUtil.getLocalPart(value);
        return new QName(uri, localPart);
    }
    
    public static void ensureNoContent(XMLReader reader) {
        if (reader.nextElementContent() != XMLReader.END) {
            fail("configuration.unexpectedContent", reader);
        }
    }
    
    public static void fail(String key, XMLReader reader) {
        throw new ConfigurationException(key,
            Integer.toString(reader.getLineNumber()));
    }
    
    public static void failWithFullName(String key, XMLReader reader) {
        throw new ConfigurationException(key, new Object[] { Integer.toString(
            reader.getLineNumber()), reader.getName().toString() });
    }
    
    public static void failWithLocalName(String key, XMLReader reader) {
        throw new ConfigurationException(key, new Object[] { Integer.toString(
            reader.getLineNumber()), reader.getLocalName() });
    }
    
    public static void failWithLocalName(String key, XMLReader reader,
        String arg) {
            
        throw new ConfigurationException(key, new Object[] { Integer.toString(
            reader.getLineNumber()), reader.getLocalName(), arg });
    }
}

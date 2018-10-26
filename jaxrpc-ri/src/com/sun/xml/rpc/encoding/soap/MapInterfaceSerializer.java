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

// Helper class generated by wscompile, do not edit.
// Contents subject to change without notice.

package com.sun.xml.rpc.encoding.soap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InterfaceSerializerBase;
import com.sun.xml.rpc.encoding.InternalEncodingConstants;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.encoding.SerializationException;
import com.sun.xml.rpc.encoding.SerializerCallback;
import com.sun.xml.rpc.soap.SOAPConstantsFactory;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

public class MapInterfaceSerializer
    extends InterfaceSerializerBase
    implements Initializable, InternalEncodingConstants {
    private CombinedSerializer hashMapSerializer;
    private CombinedSerializer treeMapSerializer;
    private CombinedSerializer hashtableSerializer;
    private CombinedSerializer propertiesSerializer;

    private com.sun.xml.rpc.soap.SOAPEncodingConstants soapEncodingConstants =
        null;

    private void init(SOAPVersion ver) {
        this.soapEncodingConstants =
            SOAPConstantsFactory.getSOAPEncodingConstants(ver);
    }

    public MapInterfaceSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle) {
            
        this(type, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
    }

    public MapInterfaceSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        SOAPVersion ver) {
            
        super(type, encodeType, isNullable, encodingStyle);
        init(ver); //Initialize SOAP version
    }

    public void initialize(InternalTypeMappingRegistry registry)
        throws Exception {
            
        hashMapSerializer =
            (CombinedSerializer) registry.getSerializer(
                encodingStyle,
                HashMap.class,
                QNAME_TYPE_HASH_MAP);
        hashMapSerializer = hashMapSerializer.getInnermostSerializer();
        treeMapSerializer =
            (CombinedSerializer) registry.getSerializer(
                encodingStyle,
                TreeMap.class,
                QNAME_TYPE_TREE_MAP);
        treeMapSerializer = treeMapSerializer.getInnermostSerializer();
        hashtableSerializer =
            (CombinedSerializer) registry.getSerializer(
                encodingStyle,
                Hashtable.class,
                QNAME_TYPE_HASHTABLE);
        hashtableSerializer = hashtableSerializer.getInnermostSerializer();
        propertiesSerializer =
            (CombinedSerializer) registry.getSerializer(
                encodingStyle,
                Properties.class,
                QNAME_TYPE_PROPERTIES);
        propertiesSerializer = propertiesSerializer.getInnermostSerializer();
    }

    public Object doDeserialize(
        QName name,
        XMLReader reader,
        SOAPDeserializationContext context)
        throws Exception {
            
        QName elementType = getType(reader);
        if (elementType.equals(QNAME_TYPE_MAP)
            || elementType.equals(QNAME_TYPE_HASH_MAP)) {
            return hashMapSerializer.deserialize(name, reader, context);
        } else if (elementType.equals(QNAME_TYPE_TREE_MAP)) {
            return treeMapSerializer.deserialize(name, reader, context);
        } else if (elementType.equals(QNAME_TYPE_HASHTABLE)) {
            return hashtableSerializer.deserialize(name, reader, context);
        } else if (elementType.equals(QNAME_TYPE_PROPERTIES)) {
            return propertiesSerializer.deserialize(name, reader, context);
        }
        throw new DeserializationException(
            "soap.unexpectedElementType",
            new Object[] { "", elementType.toString()});
    }

    public void doSerializeInstance(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context)
        throws Exception {

        if (obj instanceof HashMap) {
            hashMapSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof Properties) {
            propertiesSerializer.serialize(
                obj,
                name,
                callback,
                writer,
                context);
        } else if (obj instanceof Hashtable) {
            hashtableSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof TreeMap) {
            treeMapSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof Map) {
            hashMapSerializer.serialize(obj, name, callback, writer, context);
        } else {
            throw new SerializationException(
                "soap.cannot.serialize.type",
                obj.getClass().getName());
        }
    }
}
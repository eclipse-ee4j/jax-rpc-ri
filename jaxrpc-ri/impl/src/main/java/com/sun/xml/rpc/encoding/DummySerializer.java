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

package com.sun.xml.rpc.encoding;

import com.sun.xml.rpc.streaming.*;
import javax.xml.namespace.QName;
import javax.activation.DataHandler;

/**
 * @author JAX-RPC Development Team
 */
public class DummySerializer implements CombinedSerializer {

    private static final DummySerializer _instance = new DummySerializer();

    public static DummySerializer getInstance() {
        return _instance;
    }

    private DummySerializer() {
    }

    public void serialize(
        Object obj,
        QName name,
        SerializerCallback callback,
        XMLWriter writer,
        SOAPSerializationContext context) {
    }

    public Object deserialize(
        QName name,
        XMLReader element,
        SOAPDeserializationContext context) {

        return null;
    }

    public Object deserialize(
        DataHandler dataHandler,
        SOAPDeserializationContext context) {

        return null;
    }

    public QName getXmlType() {
        throw new UnsupportedOperationException();
    }

    public boolean getEncodeType() {
        throw new UnsupportedOperationException();
    }

    public boolean isNullable() {
        throw new UnsupportedOperationException();
    }

    public String getEncodingStyle() {
        throw new UnsupportedOperationException();
    }

    public CombinedSerializer getInnermostSerializer() {
        return this;
    }

    public String getMechanismType() {
        return com.sun.xml.rpc.encoding.EncodingConstants.JAX_RPC_RI_MECHANISM;
    }
}

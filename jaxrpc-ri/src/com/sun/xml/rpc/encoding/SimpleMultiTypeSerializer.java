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

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;

/**
 *
 * @author JAX-RPC Development Team
 */
public class SimpleMultiTypeSerializer extends SimpleTypeSerializer {

    private Set supportedTypes;

    public SimpleMultiTypeSerializer(
        QName type,
        boolean encodeType,
        boolean isNullable,
        String encodingStyle,
        SimpleTypeEncoder encoder,
        QName[] types) {
            
        super(type, encodeType, isNullable, encodingStyle, encoder);
        supportedTypes = new HashSet();
        for (int i = 0; i < types.length; i++)
            supportedTypes.add(types[i]);
    }

    protected boolean isAcceptableType(QName actualType) {
        return supportedTypes.contains(actualType);
    }
}

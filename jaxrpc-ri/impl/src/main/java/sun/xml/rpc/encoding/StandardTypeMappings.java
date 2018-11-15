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

/**
 * <p> Singletons for TypeMappings that contain standard (de)serializers for
 * SOAP and Literal encoding. </p>
 *
 * @author JAX-RPC Development Team
 */

package com.sun.xml.rpc.encoding;

import com.sun.xml.rpc.encoding.literal.StandardLiteralTypeMappings;
import com.sun.xml.rpc.encoding.soap.StandardSOAPTypeMappings;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class StandardTypeMappings {

    public static ExtendedTypeMapping getSoap() {
        return getSoap(SOAPVersion.SOAP_11);
    }

    public static ExtendedTypeMapping getSoap(SOAPVersion ver) {
        try {
            return new StandardSOAPTypeMappings(ver);
        } catch (Exception e) {
            throw new TypeMappingException(
                "typemapping.nested.exception.static.initialization",
                new LocalizableExceptionAdapter(e));
        }
    }

    public static ExtendedTypeMapping getLiteral() {
        try {
            return new TypeMappingImpl();
        } catch (Exception e) {
            throw new TypeMappingException(
                "typemapping.nested.exception.static.initialization",
                new LocalizableExceptionAdapter(e));
        }
    }

    public static ExtendedTypeMapping getRPCLiteral() {
        try {
            return new StandardLiteralTypeMappings();
        } catch (Exception e) {
            throw new TypeMappingException(
                "typemapping.nested.exception.static.initialization",
                new LocalizableExceptionAdapter(e));
        }
    }

}

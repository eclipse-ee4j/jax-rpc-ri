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

package com.sun.xml.rpc.spi.model;

/**
 * This class is implemented by 
 * com.sun.xml.rpc.processor.model.ModelProperties
 */
public interface ModelProperties {
    public static final String PROPERTY_WSDL_PORT_NAME =
        "com.sun.xml.rpc.processor.model.WSDLPortName";
    public static final String PROPERTY_WSDL_PORT_TYPE_NAME =
        "com.sun.xml.rpc.processor.model.WSDLPortTypeName";
    public static final String PROPERTY_WSDL_BINDING_NAME =
        "com.sun.xml.rpc.processor.model.WSDLBindingName";
    public static final String PROPERTY_WSDL_MESSAGE_NAME =
        "com.sun.xml.rpc.processor.model.WSDLMessageName";
    public static final String PROPERTY_MODELER_NAME =
        "com.sun.xml.rpc.processor.model.ModelerName";
    public static final String PROPERTY_STUB_CLASS_NAME =
        "com.sun.xml.rpc.processor.model.StubClassName";
    public static final String PROPERTY_TIE_CLASS_NAME =
        "com.sun.xml.rpc.processor.model.TieClassName";
    public static final String PROPERTY_JAVA_PORT_NAME =
        "com.sun.xml.rpc.processor.model.JavaPortName";
}

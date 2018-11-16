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

/**
 *
 * @author JAX-RPC Development Team
 */
public interface ModelProperties
    extends com.sun.xml.rpc.spi.model.ModelProperties {
    
    //to set WSDL_MODELER_NAME from inside WSDLModeler
    public static final String WSDL_MODELER_NAME =
        "com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler";
    public static final String PROPERTY_PARAM_MESSAGE_PART_NAME =
        "com.sun.xml.rpc.processor.model.ParamMessagePartName";
    public static final String PROPERTY_ANONYMOUS_TYPE_NAME =
        "com.sun.xml.rpc.processor.model.AnonymousTypeName";
    public static final String PROPERTY_ANONYMOUS_ARRAY_TYPE_NAME =
        "com.sun.xml.rpc.processor.model.AnonymousArrayTypeName";
    public static final String PROPERTY_ANONYMOUS_ARRAY_JAVA_TYPE =
        "com.sun.xml.rpc.processor.model.AnonymousArrayJavaType";

}

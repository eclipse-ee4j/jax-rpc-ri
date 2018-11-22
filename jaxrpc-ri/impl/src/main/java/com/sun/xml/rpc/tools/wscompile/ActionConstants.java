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

package com.sun.xml.rpc.tools.wscompile;

/**
 *
 * @author JAX-RPC Development Team
 */
public interface ActionConstants {
    
    public static final String ACTION_REMOTE_INTERFACE_GENERATOR          = "remote.interface.generator";
    public static final String ACTION_REMOTE_INTERFACE_IMPL_GENERATOR     = "remote.interface.impl.generator";
    public static final String ACTION_CUSTOM_CLASS_GENERATOR              = "custom.class.generator";
    public static final String ACTION_SOAP_OBJECT_SERIALIZER_GENERATOR    = "soap.object.serializer.generator";
    public static final String ACTION_INTERFACE_SERIALIZER_GENERATOR      = "interface.serializer.generator";
    public static final String ACTION_SOAP_OBJECT_BUILDER_GENERATOR       = "soap.object.builder.generator";
    public static final String ACTION_LITERAL_OBJECT_SERIALIZER_GENERATOR = "literal.object.serializer.generator";
    public static final String ACTION_STUB_GENERATOR                      = "stub.generator";
    public static final String ACTION_TIE_GENERATOR                       = "tie.generator";
    public static final String ACTION_SERVLET_CONFIG_GENERATOR            = "servlet.config.generator";
    public static final String ACTION_WSDL_GENERATOR                      = "wsdl.generator";
    public static final String ACTION_HOLDER_GENERATOR                    = "holder.generator";
    public static final String ACTION_SERVICE_INTERFACE_GENERATOR         = "service.interface.generator";
    public static final String ACTION_SERVICE_GENERATOR                   = "service.generator";
    public static final String ACTION_SERIALIZER_REGISTRY_GENERATOR       = "serializer.registry.generator";
    public static final String ACTION_CUSTOM_EXCEPTION_GENERATOR          = "custom.exception.generator";
    public static final String ACTION_SOAP_FAULT_SERIALIZER_GENERATOR     = "soap.fault.serializer.generator";
    public static final String ACTION_FAULT_EXCEPTION_BUILDER_GENERATOR   = "fault.exception.builder.generator";
    public static final String ACTION_ENUMERATION_GENERATOR               = "enumeration.generator";
    public static final String ACTION_ENUMERATION_ENCODER_GENERATOR       = "enumeration.encoder.generator";
}

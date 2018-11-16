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

package com.sun.xml.rpc.processor;

/**
 * Property names used by ProcessorActions
 *
 * @author JAX-RPC Development Team
 */
public class ProcessorOptions {
    
    public final static String SOURCE_DIRECTORY_PROPERTY = "sourceDirectory";
    public final static String DESTINATION_DIRECTORY_PROPERTY =
        "destinationDirectory";
    public final static String NONCLASS_DESTINATION_DIRECTORY_PROPERTY =
        "nonclassDestinationDirectory";
    public final static String ENCODE_TYPES_PROPERTY = "encodeTypes";
    public final static String MULTI_REF_ENCODING_PROPERTY = "multiRefEncoding";
    public final static String VALIDATE_WSDL_PROPERTY = "validationWSDL";
    public final static String EXPLICIT_SERVICE_CONTEXT_PROPERTY =
        "explicitServiceContext";
    public final static String PRINT_STACK_TRACE_PROPERTY = "printStackTrace";
    public final static String GENERATE_SERIALIZABLE_IF = "serializable";
    public final static String DONOT_OVERRIDE_CLASSES = "donotOverride";
    public final static String NO_DATA_BINDING_PROPERTY = "noDataBinding";
    public final static String SERIALIZE_INTERFACES_PROPERTY =
        "serializerInterfaces";
    public final static String USE_DATA_HANDLER_ONLY = "useDataHandlerOnly";
    public final static String SEARCH_SCHEMA_FOR_SUBTYPES =
        "searchSchemaForSubtypes";
    public final static String DONT_GENERATE_RPC_STRUCTURES =
        "dontGenerateRPCStructures";
    public final static String USE_DOCUMENT_LITERAL_ENCODING =
        "useDocumentLiteralEncoding";
    public final static String USE_RPC_LITERAL_ENCODING =
        "useRPCLiteralEncoding";
    public final static String USE_WSI_BASIC_PROFILE = "useWSIBasicProfile";
    public final static String GENERATE_ONE_WAY_OPERATIONS =
        "generateOneWayOperations";
    public final static String ENABLE_IDREF = "resolveIDREF";
    public final static String STRICT_COMPLIANCE = "strictCompliance";
    public final static String JAXB_ENUMTYPE = "jaxbenum";
    public final static String JAXRPC_SOURCE_VERSION = "sourceVersion";
    public final static String UNWRAP_DOC_LITERAL_WRAPPERS =
        "unwrapDocLitWrappers";
    public final static String DONT_GENERATE_WRAPPER_CLASSES =
        "dontGenerateWrapperClasses";
}

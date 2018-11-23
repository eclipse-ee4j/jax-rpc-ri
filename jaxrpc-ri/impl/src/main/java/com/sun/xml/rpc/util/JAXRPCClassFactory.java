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

package com.sun.xml.rpc.util;

import java.util.Properties;
import java.util.Set;

import com.sun.xml.rpc.processor.config.ModelInfo;
import com.sun.xml.rpc.processor.config.WSDLModelInfo;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.processor.generator.Names101;
import com.sun.xml.rpc.processor.generator.Names103;
import com.sun.xml.rpc.processor.generator.Names11;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreator101;
import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreator103;
import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreator11;
import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreatorBase;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer101;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer103;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer11;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer111;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler101;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler103;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler11;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler111;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler112;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase;
import com.sun.xml.rpc.processor.schema.InternalSchemaBuilder101;
import com.sun.xml.rpc.processor.schema.InternalSchemaBuilder103;
import com.sun.xml.rpc.processor.schema.InternalSchemaBuilder11;
import com.sun.xml.rpc.processor.schema.InternalSchemaBuilderBase;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.wsdl.framework.AbstractDocument;

/**
 * Singleton factory class to instantiate concrete classes based on the jaxrpc version 
 * to be used to generate the code.
 * 
 * @author JAX-RPC Development Team
 */
public class JAXRPCClassFactory {
    private static final JAXRPCClassFactory factory = new JAXRPCClassFactory();

    private static String classVersion = VersionUtil.JAXRPC_VERSION_DEFAULT;

    private JAXRPCClassFactory() {
    }

    /**
     * Get the factory instance for the default version.
     * @return        JAXRPCClassFactory instance
     */
    public static JAXRPCClassFactory newInstance() {
        return factory;
    }

    /**
     * Sets the version to a static classVersion
     * @param version
     */
    public void setSourceVersion(String version) {
        if (version == null)
            version = VersionUtil.JAXRPC_VERSION_DEFAULT;

        if (!VersionUtil.isValidVersion(version)) {
            // TODO: throw exception
        } else
            classVersion = version;
    }

    /**
     * Returns the instance of SchemaAnalyzer for specific target version set for the factory.
     * 
     * @param document
     * @param modelInfo
     * @param options
     * @param conflictingClassNames
     * @param javaTypes
     * @return the appropriate SchemaAnalyzer for the JAX-RPC version
     */
    public SchemaAnalyzerBase createSchemaAnalyzer(
        AbstractDocument document,
        ModelInfo modelInfo,
        Properties options,
        Set conflictingClassNames,
        JavaSimpleTypeCreator javaTypes) {
        SchemaAnalyzerBase schemaAnalyzer = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            schemaAnalyzer =
                (SchemaAnalyzerBase) new SchemaAnalyzer101(document,
                    modelInfo,
                    options,
                    conflictingClassNames,
                    javaTypes);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            schemaAnalyzer =
                (SchemaAnalyzerBase) new SchemaAnalyzer103(document,
                    modelInfo,
                    options,
                    conflictingClassNames,
                    javaTypes);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_11))
            schemaAnalyzer =
                (SchemaAnalyzerBase) new SchemaAnalyzer11(document,
                    modelInfo,
                    options,
                    conflictingClassNames,
                    javaTypes);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_111))
            schemaAnalyzer =
                (SchemaAnalyzerBase) new SchemaAnalyzer111(document,
                    modelInfo,
                    options,
                    conflictingClassNames,
                    javaTypes);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_112))
            schemaAnalyzer =
                (SchemaAnalyzerBase) new SchemaAnalyzer111(document,
                    modelInfo,
                    options,
                    conflictingClassNames,
                    javaTypes);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            schemaAnalyzer =
                (SchemaAnalyzerBase) new SchemaAnalyzer111(document,
                    modelInfo,
                    options,
                    conflictingClassNames,
                    javaTypes);
        else {
            // TODO: throw exception                                
        }
        return schemaAnalyzer;
    }

    /**
     * Returns the instance of InternalSchemaBuilderBase for specific target version set for the factory.
     * 
     * @param document
     * @param options
     * @return the appropriate InternalSchemaBuilderBase for the JAX-RPC version
     */
    public InternalSchemaBuilderBase createInternalSchemaBuilder(
        AbstractDocument document,
        Properties options) {
        InternalSchemaBuilderBase internalSchemaBuilder = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            internalSchemaBuilder =
                (InternalSchemaBuilderBase) new InternalSchemaBuilder101(document,
                    options);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            internalSchemaBuilder =
                (InternalSchemaBuilderBase) new InternalSchemaBuilder103(document,
                    options);
        else if (
            classVersion.equals(VersionUtil.JAXRPC_VERSION_11)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_111)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_112)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            internalSchemaBuilder =
                (InternalSchemaBuilderBase) new InternalSchemaBuilder11(document,
                    options);
        else {
            // TODO: throw exception                                
        }
        return internalSchemaBuilder;
    }

    /**
     * Returns the WSDLModeler for specific target version.
     * 
     * @param modelInfo
     * @param options
     * @return the appropriate WSDLModeler for the JAX-RPC version
     */
    public WSDLModelerBase createWSDLModeler(
        WSDLModelInfo modelInfo,
        Properties options) {
        WSDLModelerBase wsdlModeler = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            wsdlModeler =
                (WSDLModelerBase) new WSDLModeler101(modelInfo, options);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            wsdlModeler =
                (WSDLModelerBase) new WSDLModeler103(modelInfo, options);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_11))
            wsdlModeler =
                (WSDLModelerBase) new WSDLModeler11(modelInfo, options);
        else if (
            classVersion.equals(VersionUtil.JAXRPC_VERSION_111))
            wsdlModeler =
                (WSDLModelerBase) new WSDLModeler111(modelInfo, options);
        else if (
            classVersion.equals(VersionUtil.JAXRPC_VERSION_112) ||
            classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            wsdlModeler =
                (WSDLModelerBase) new WSDLModeler112(modelInfo, options);
        else {
            // TODO: throw exception                                
        }
        return wsdlModeler;
    }

    /**
     * Returns the Names for specific target version.
     * //bug fix:4904604     
     * @return the appropriate {@link Names} for the JAX-RPC version
     */
    public Names createNames() {
        Names names = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            names = (Names) new Names101();
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            names = (Names) new Names103();
        else if (
            classVersion.equals(VersionUtil.JAXRPC_VERSION_11)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_111)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_112)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            names = (Names) new Names11();
        else {
            // TODO: throw exception                                
        }
        return names;
    }

    /**
     * Returns the SOAPSimpleTypeCreatorBase for specific target version.
     * 
     * @return the appropriate SOAPSimpleTypeCreatorBase for the JAX-RPC version
     */
    public SOAPSimpleTypeCreatorBase createSOAPSimpleTypeCreator() {
        SOAPSimpleTypeCreatorBase soapType = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator101();
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator103();
        else if (
            classVersion.equals(VersionUtil.JAXRPC_VERSION_11)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_111)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_112)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator11();
        else {
            // TODO: throw exception                                
        }
        return soapType;
    }

    /**
     * Returns the SOAPSimpleTypeCreatorBase for specific target version.
     * 
     * @return the appropriate SOAPSimpleTypeCreatorBase for the JAX-RPC version
     */
    public SOAPSimpleTypeCreatorBase createSOAPSimpleTypeCreator(boolean useStrictMode) {
        SOAPSimpleTypeCreatorBase soapType = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator101(useStrictMode);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator103(useStrictMode);
        else if (
            classVersion.equals(VersionUtil.JAXRPC_VERSION_11)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_111)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_112)
                || classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator11(useStrictMode);
        else {
            // TODO: throw exception                                
        }
        return soapType;
    }

    /**
     * Returns the SOAPSimpleTypeCreatorBase for specific target version.
     * 
     * @return the appropriate SOAPSimpleTypeCreatorBase for the JAX-RPC version
     */
    public SOAPSimpleTypeCreatorBase createSOAPSimpleTypeCreator(
        boolean useStrictMode,
        SOAPVersion version) {
        SOAPSimpleTypeCreatorBase soapType = null;
        if (classVersion.equals(VersionUtil.JAXRPC_VERSION_101))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator101(useStrictMode,
                    version);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_103))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator103(useStrictMode,
                    version);
        else if (classVersion.equals(VersionUtil.JAXRPC_VERSION_11) ||
                    classVersion.equals(VersionUtil.JAXRPC_VERSION_111) || 
                    classVersion.equals(VersionUtil.JAXRPC_VERSION_112) ||
                    classVersion.equals(VersionUtil.JAXRPC_VERSION_113))
            soapType =
                (SOAPSimpleTypeCreatorBase) new SOAPSimpleTypeCreator11(useStrictMode,
                    version);
        else {
            // TODO: throw exception                                
        }
        return soapType;
    }

    public String getVersion() {
        return classVersion;
    }
}

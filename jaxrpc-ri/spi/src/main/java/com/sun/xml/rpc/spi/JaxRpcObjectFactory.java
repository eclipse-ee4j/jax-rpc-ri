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

package com.sun.xml.rpc.spi;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.sun.xml.rpc.spi.runtime.ClientTransportFactory;
import com.sun.xml.rpc.spi.runtime.Implementor;
import com.sun.xml.rpc.spi.runtime.ImplementorCache;
import com.sun.xml.rpc.spi.runtime.RuntimeEndpointInfo;
import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;
import com.sun.xml.rpc.spi.runtime.ServletDelegate;
import com.sun.xml.rpc.spi.runtime.Tie;
import com.sun.xml.rpc.spi.tools.CompileTool;
import com.sun.xml.rpc.spi.tools.Configuration;
import com.sun.xml.rpc.spi.tools.HandlerInfo;
import com.sun.xml.rpc.spi.tools.J2EEModelInfo;
import com.sun.xml.rpc.spi.tools.ModelFileModelInfo;
import com.sun.xml.rpc.spi.tools.Names;
import com.sun.xml.rpc.spi.tools.NamespaceMappingInfo;
import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
import com.sun.xml.rpc.spi.tools.NoMetadataModelInfo;
import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
import com.sun.xml.rpc.spi.tools.WSDLParser;
import com.sun.xml.rpc.spi.tools.WSDLUtil;
import com.sun.xml.rpc.spi.tools.XMLModelFileFilter;

/**
 * Singleton abstract factory used to produce jaxrpc related objects.
 */
public abstract class JaxRpcObjectFactory {

    private static JaxRpcObjectFactory factory;

    private static String DEFAULT_JAXRPC_OBJECT_FACTORY =
        "com.sun.xml.rpc.util.JaxRpcObjectFactoryImpl";
    private static String JAXRPC_FACTORY_PROPERTY = "javax.xml.rpc.spi.JaxRpcObjectFactory";

    public JaxRpcObjectFactory () {}
    /**
     * Creates an instance of the specified class using the specified 
     * <code>ClassLoader</code> object.
     *
     * @exception SOAPException if the given class could not be found
     *            or could not be instantiated
     */
    private static Object newInstance(String className,
                                      ClassLoader classLoader)
        
    {
        try {
            Class spiClass;
            if (classLoader == null) {
                spiClass = Class.forName(className);
            } else {
                spiClass = classLoader.loadClass(className);
            }
            return spiClass.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    /**
     * Obtain an instance of a factory.
     *
     * <p> The implementation class to be used can be overridden by setting a
     * system property (name TBD). </p>
     *
     */
    public static JaxRpcObjectFactory newInstance() {
        /*
        if (factory == null) {
            //XXX FIXME Make it configurable by property
            try {
                Class klass = Class.forName(DEFAULT_JAXRPC_OBJECT_FACTORY);
                factory = (JaxRpcObjectFactory) klass.newInstance();
            } catch (Exception e) {
                //XXX FIXME  i18n.  Better Handling of the Error
                e.printStackTrace();
            }
        }
        return factory;
         */ 
        ClassLoader classLoader;
            classLoader = Thread.currentThread().getContextClassLoader();

        // Use the system property first
        try {
            String systemProp =
                System.getProperty(JAXRPC_FACTORY_PROPERTY);
            if( systemProp!=null) {
                return (JaxRpcObjectFactory) newInstance(systemProp, classLoader);
            }
        } catch (Exception e) {
        }


        String serviceId = "META-INF/services/" + JAXRPC_FACTORY_PROPERTY;
        // try to find services in CLASSPATH
        try {
            InputStream is=null;
            if (classLoader == null) {
                is=ClassLoader.getSystemResourceAsStream(serviceId);
            } else {
                is=classLoader.getResourceAsStream(serviceId);
            }
        
            if( is!=null ) {
                BufferedReader rd =
                    new BufferedReader(new InputStreamReader(is, "UTF-8"));
        
                String factoryClassName = rd.readLine();
                rd.close();

                if (factoryClassName != null &&
                    ! "".equals(factoryClassName)) {
                    return (JaxRpcObjectFactory) newInstance(factoryClassName, classLoader);
                }
            }
        } catch( Exception ex ) {
        }

        return (JaxRpcObjectFactory) newInstance(DEFAULT_JAXRPC_OBJECT_FACTORY, classLoader);
    }
    public abstract ModelFileModelInfo createModelFileModelInfo();

    public abstract NoMetadataModelInfo createNoMetadataModelInfo();

    public abstract J2EEModelInfo createJ2EEModelInfo(URL mapping)
        throws Exception;

    public abstract HandlerInfo createHandlerInfo();

    public abstract NamespaceMappingRegistryInfo createNamespaceMappingRegistryInfo();

    public abstract NamespaceMappingInfo createNamespaceMappingInfo(
        String namespaceURI,
        String javaPackageName);

    public abstract Configuration createConfiguration(ProcessorEnvironment env);

    public abstract SOAPMessageContext createSOAPMessageContext();

    public abstract Implementor createImplementor(
        ServletContext servletContext,
        Tie tie);

    public abstract RuntimeEndpointInfo createRuntimeEndpointInfo();

    /**
     * @param type The type of ClientTransportFactory
     * @see com.sun.xml.rpc.spi.runtime.ClientTransportFactoryTypes
     */
    public abstract ClientTransportFactory createClientTransportFactory(
        int type,
        OutputStream logStream);

    public abstract CompileTool createCompileTool(
        OutputStream out,
        String program);

    public abstract XMLModelFileFilter createXMLModelFileFilter();

    public abstract ImplementorCache createImplementorCache(ServletConfig config);

    public abstract ServletDelegate createServletDelegate();

    /**
     * Names provides utility methods used by other wscompile classes
     * for dealing with identifiers.  This is not the most obvious/intuitive
     * method name.  Any suggestion is welcome.
     */
    public abstract Names createNames();

    public abstract WSDLUtil createWSDLUtil();

    public abstract WSDLParser createWSDLParser();
}

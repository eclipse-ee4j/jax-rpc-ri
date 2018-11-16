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

package com.sun.xml.rpc.streaming;

import java.io.OutputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * <p> Define a factory API to enable pluggable XMLWriter implementations. </p>
 *
 * @see XMLWriter
 *
 * @author JAX-RPC Development Team
 */
public abstract class XMLWriterFactory {

    protected XMLWriterFactory() {
    }

    /**
     * Obtain an instance of a factory.
     * Since factories are stateless, only one copy of a factory exists and is
     * returned to the application each time this method is called.
     *
     * The implementation class to be used can be overridden by setting the
     * com.sun.xml.rpc.streaming.XMLWriterFactory system property.
     *
     */
    public static XMLWriterFactory newInstance() {
        if (_instance == null) {
            String factoryImplName = getFactoryImplName();
            XMLWriterFactory factoryImpl;
            try {
                Class clazz = Class.forName(factoryImplName);
                _instance = (XMLWriterFactory) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                throw new FactoryConfigurationError(e);
            } catch (IllegalAccessException e) {
                throw new FactoryConfigurationError(e);
            } catch (InstantiationException e) {
                throw new FactoryConfigurationError(e);
            }
        }
        return _instance;
    }

    private static String getFactoryImplName() {
        String factoryImplName;
        try {
            factoryImplName =
                (String) AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    // AccessControll problem
                    return System.getProperty(
                        "com.sun.xml.rpc.streaming.XMLWriterFactory",
                        "com.sun.xml.rpc.streaming.XMLWriterFactoryImpl");
                }
            });
        } catch (AccessControlException e) {
            factoryImplName = "com.sun.xml.rpc.streaming.XMLWriterFactoryImpl";
        }
        return factoryImplName;
    }

    /**
     * Obtain an XMLWriter on the given OutputStream using the default encoding
     * and XML declaration settings.
     *
     */
    public abstract XMLWriter createXMLWriter(OutputStream stream);

    /**
     * Obtain an XMLWriter on the given OutputStream using the given encoding
     * and the default XML declaration settings.
     *
     */
    public abstract XMLWriter createXMLWriter(
        OutputStream stream,
        String encoding);

    /**
     * Obtain an XMLWriter on the given OutputStream using the given encoding
     * and the given XML declaration settings.
     *
     */
    public abstract XMLWriter createXMLWriter(
        OutputStream stream,
        String encoding,
        boolean declare);

    private static XMLWriterFactory _instance;
}

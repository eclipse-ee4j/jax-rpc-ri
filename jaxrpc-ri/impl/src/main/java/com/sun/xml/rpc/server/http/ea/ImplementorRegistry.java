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

package com.sun.xml.rpc.server.http.ea;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.sun.xml.rpc.server.http.JAXRPCServletException;

/**
 * A registry mapping port names to ImplementorInfo objects.
 *
 * @author JAX-RPC Development Team
 */
public class ImplementorRegistry {

    public ImplementorRegistry() {
    }

    public ImplementorInfo getImplementorInfo(String name) {
        ImplementorInfo info = (ImplementorInfo) _implementors.get(name);

        if (info == null) {
            throw new JAXRPCServletException(
                "error.implementorRegistry.unknownName",
                name);
        }

        return info;
    }

    public boolean containsName(String name) {
        return _implementors.containsKey(name);
    }

    public Iterator names() {
        return _implementors.keySet().iterator();
    }

    public void readFrom(String filename) {
        try {
            readFrom(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            throw new JAXRPCServletException(
                "error.implementorRegistry.fileNotFound",
                filename);
        }
    }

    public void readFrom(InputStream inputStream) {

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();

            int portCount =
                Integer.parseInt(properties.getProperty(PROPERTY_PORT_COUNT));
            for (int i = 0; i < portCount; ++i) {
                String portPrefix = PROPERTY_PORT + Integer.toString(i) + ".";
                String name =
                    properties.getProperty(portPrefix + PROPERTY_NAME);
                String tieClassName =
                    properties.getProperty(portPrefix + PROPERTY_TIE);
                String servantClassName =
                    properties.getProperty(portPrefix + PROPERTY_SERVANT);
                if (name == null
                    || tieClassName == null
                    || servantClassName == null) {
                    throw new JAXRPCServletException("error.implementorRegistry.incompleteInformation");
                }
                register(name, tieClassName, servantClassName);
            }
        } catch (IOException e) {
            throw new JAXRPCServletException("error.implementorRegistry.cannotReadConfiguration");
        }
    }

    public void register(
        String name,
        String tieClassName,
        String servantClassName) {
        Class tieClass = null;
        Class servantClass = null;
        try {
            tieClass =
                Thread.currentThread().getContextClassLoader().loadClass(
                    tieClassName);
        } catch (ClassNotFoundException e) {
            throw new JAXRPCServletException(
                "error.implementorRegistry.classNotFound",
                tieClassName);
        }

        try {
            servantClass =
                Thread.currentThread().getContextClassLoader().loadClass(
                    servantClassName);
        } catch (ClassNotFoundException e) {
            throw new JAXRPCServletException(
                "error.implementorRegistry.classNotFound",
                servantClassName);
        }

        register(name, new ImplementorInfo(tieClass, servantClass));
    }

    public void register(String name, ImplementorInfo info) {
        if (_implementors.containsKey(name)) {
            throw new JAXRPCServletException(
                "error.implementorRegistry.duplicateName",
                name);
        } else {
            _implementors.put(name, info);
        }
    }

    private Map _implementors = new HashMap();

    private final static String PROPERTY_PORT_COUNT = "portcount";
    private final static String PROPERTY_PORT = "port";
    private final static String PROPERTY_NAME = "name";
    private final static String PROPERTY_TIE = "tie";
    private final static String PROPERTY_SERVANT = "servant";
}

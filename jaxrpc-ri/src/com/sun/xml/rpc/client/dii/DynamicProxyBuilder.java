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

package com.sun.xml.rpc.client.dii;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.rmi.Remote;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.HandlerRegistry;

import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

/**
 * @author JAX-RPC Development Team
 */
public class DynamicProxyBuilder {
    protected InternalTypeMappingRegistry internalTypeRegistry;
    protected HandlerRegistry handlerRegistry;
    protected ServiceInfo configuration;

    public DynamicProxyBuilder(
        InternalTypeMappingRegistry internalTypeRegistry,
        HandlerRegistry handlerRegistry,
        ServiceInfo configuration) {

        this.internalTypeRegistry = internalTypeRegistry;
        this.handlerRegistry = handlerRegistry;
        this.configuration = configuration;

    }

    public Remote buildDynamicProxyFor(PortInfo portInfo, Class portInterface)
        throws ServiceException {

        CallInvocationHandler handler =
            new CallInvocationHandler(portInterface);
        handler._setProperty(
            Stub.ENDPOINT_ADDRESS_PROPERTY,
            portInfo.getTargetEndpoint());

        Method[] interfaceMethods = portInterface.getMethods();
        for (int i = 0; i < interfaceMethods.length; ++i) {
            Method currentMethod = interfaceMethods[i];

            if (Modifier.isPublic(currentMethod.getModifiers())) {
                ConfiguredCall methodCall =
                    new ConfiguredCall(
                        internalTypeRegistry,
                        handlerRegistry,
                        configuration);

                String methodName = currentMethod.getName();

                methodCall.setPortName(portInfo.getName());
                methodCall.setOperationMethod(currentMethod);
                methodCall.setMethodName(methodName);
                methodCall.setIsProxy(true);

                handler.addCall(currentMethod, methodCall);
            }
        }

        return (Remote) Proxy.newProxyInstance(
            portInterface.getClassLoader(),
            new Class[] { portInterface, Stub.class, Remote.class },
            handler);
    }
}

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

package com.sun.xml.rpc.server.http;

import javax.xml.namespace.QName;

/**
 * @author JAX-RPC Development Team
 */
public class RuntimeEndpointInfo
    implements com.sun.xml.rpc.spi.runtime.RuntimeEndpointInfo {

    public RuntimeEndpointInfo() {
    }

    public Class getRemoteInterface() {
        return remoteInterface;
    }

    public void setRemoteInterface(Class klass) {
        remoteInterface = klass;
    }

    public Class getImplementationClass() {
        return implementationClass;
    }

    public void setImplementationClass(Class klass) {
        implementationClass = klass;
    }

    public Class getTieClass() {
        return tieClass;
    }

    public void setTieClass(Class klass) {
        tieClass = klass;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception e) {
        exception = e;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public String getModelFileName() {
        return modelFileName;
    }

    public void setModelFileName(String s) {
        modelFileName = s;
    }

    public String getWSDLFileName() {
        return wsdlFileName;
    }

    public void setWSDLFileName(String s) {
        wsdlFileName = s;
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean b) {
        deployed = b;
    }

    public QName getPortName() {
        return portName;
    }

    public void setPortName(QName n) {
        portName = n;
    }

    public QName getServiceName() {
        return serviceName;
    }

    public void setServiceName(QName n) {
        serviceName = n;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String s) {
        urlPattern = s;
    }

    private Class remoteInterface;
    private Class implementationClass;
    private Class tieClass;
    private String name;
    private Exception exception;
    private QName portName;
    private QName serviceName;
    private String modelFileName;
    private String wsdlFileName;
    private boolean deployed;
    private String urlPattern;
}

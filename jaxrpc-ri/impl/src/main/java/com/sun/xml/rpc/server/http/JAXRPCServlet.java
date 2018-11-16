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

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
import com.sun.xml.rpc.util.localization.Localizer;

/**
 * The JAX-RPC dispatcher servlet.
 *
 * @author JAX-RPC Development Team
 */
public class JAXRPCServlet extends HttpServlet {
    
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        localizer = new Localizer();
        messageFactory =
            new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");

        // workaround for servlet v 2.3 not requiring listeners before servlet
        ServletContext context = servletConfig.getServletContext();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }

        try {
            JAXRPCRuntimeInfoParser parser =
                new JAXRPCRuntimeInfoParser(classLoader);
            InputStream is = context.getResourceAsStream(JAXRPC_RI_RUNTIME);
            JAXRPCRuntimeInfo info = parser.parse(is);
            context.setAttribute(JAXRPC_RI_RUNTIME_INFO, info);
            // end workaround
            
            String delegateClassName =
                servletConfig.getInitParameter(DELEGATE_PROPERTY);

            if (delegateClassName == null
                && servletConfig.getInitParameter(EA_CONFIG_FILE_PROPERTY)
                    != null) {
                // use EA backward compatibility mode
                delegateClassName = EA_DELEGATE_CLASS_NAME;
            }

            if (delegateClassName == null) {
                delegateClassName = DEFAULT_DELEGATE_CLASS_NAME;
            }

            Class delegateClass =
                Class.forName(
                    delegateClassName,
                    true,
                    Thread.currentThread().getContextClassLoader());
            delegate = (ServletDelegate) delegateClass.newInstance();
            delegate.init(servletConfig);

        } catch (ServletException e) {
            logger.log(Level.SEVERE,e.getMessage(), e);
            throw e;
        } catch (Throwable e) {
            String message =
                localizer.localize(
                    messageFactory.getMessage(
                        "error.servlet.caughtThrowableInInit",
                        new Object[] { e }));
            logger.log(Level.SEVERE, message, e);
            throw new ServletException(message);
        }
    }

    public void destroy() {
        if (delegate != null) {
            delegate.destroy();
        }
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doPost(request, response);
        }
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doGet(request, response);
        }
    }

    protected ServletDelegate delegate = null;
    private LocalizableMessageFactory messageFactory;
    private Localizer localizer;

    private static final String DELEGATE_PROPERTY = "delegate";
    private static final String DEFAULT_DELEGATE_CLASS_NAME =
        "com.sun.xml.rpc.server.http.JAXRPCServletDelegate";

    private static final String EA_CONFIG_FILE_PROPERTY = "configuration.file";
    private static final String EA_DELEGATE_CLASS_NAME =
        "com.sun.xml.rpc.server.http.ea.JAXRPCServletDelegate";
    private static final String JAXRPC_RI_RUNTIME =
        "/WEB-INF/jaxrpc-ri-runtime.xml";

    public static final String JAXRPC_RI_RUNTIME_INFO =
        "com.sun.xml.rpc.server.http.info";
    public static final String JAXRPC_RI_PROPERTY_PUBLISH_WSDL =
        "com.sun.xml.rpc.server.http.publishWSDL";
    public static final String JAXRPC_RI_PROPERTY_PUBLISH_MODEL =
        "com.sun.xml.rpc.server.http.publishModel";
    public static final String JAXRPC_RI_PROPERTY_PUBLISH_STATUS_PAGE =
        "com.sun.xml.rpc.server.http.publishStatusPage";

    private static final Logger logger =
        Logger.getLogger(
            com.sun.xml.rpc.util.Constants.LoggingDomain + ".server.http");
}

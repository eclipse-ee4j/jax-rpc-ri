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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
import com.sun.xml.rpc.util.localization.Localizer;

/**
 * @author JAX-RPC Development Team
 */
public class JAXRPCContextListener
    implements ServletContextAttributeListener, ServletContextListener {

    public JAXRPCContextListener() {
        localizer = new Localizer();
        messageFactory =
            new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
    }

    public void attributeAdded(ServletContextAttributeEvent event) {
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = null;
        if (logger.isLoggable(Level.INFO)) {
            logger.info(
                localizer.localize(
                    messageFactory.getMessage("listener.info.destroy")));
        }
    }

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        if (logger.isLoggable(Level.INFO)) {
            logger.info(
                localizer.localize(
                    messageFactory.getMessage("listener.info.initialize")));
        }
    }

    private Localizer localizer;
    private LocalizableMessageFactory messageFactory;
    private ServletContext context;

    private static final Logger logger =
        Logger.getLogger(
            com.sun.xml.rpc.util.Constants.LoggingDomain + ".server.http");
}

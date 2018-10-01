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

package com.sun.xml.rpc.spi.tools;

/**
 * This delegate is used by the implemenation of 
 * com.sun.xml.rpc.spi.tools.wscompile.CompileTool
 * so that customized implementation could be provided to
 * override jaxrpc specific implementation
 */
public abstract class CompileToolDelegate {

    /**
     * Default constructor.  Do nothing.
     */
    public CompileToolDelegate() {
    }

    /**
     * Assuming the jaxrpc implementation of createConfiguration()
     * will not overwrite if the delegates does return a non-null
     * Configuration object.
     */
    public Configuration createConfiguration() {
        //no op
        return null;
    }

    public void preOnError() {
        //no op
    }

    /**
     * Called right after CompileTool.registerProcessorActions.
     * We probably should also expose registerProcessorAction() in
     * case someone else would like to register more processor actions.
     * But minimum set for now until the need rises.
     */
    public void postRegisterProcessorActions() {
        // no op
    }

    /**
     */
    public void postRun() {
        //no op
    }

    /**
     * Subclass of the CompileToolDelegate is responsible to set
     * its association to a CompileTool implementation that will
     * callback for any customized implementation.  
     * <p>
     * The association between the CompileToolDelegate and CompileTool
     * is bi-directional to ensure that the delegate could also
     * access environment known to CompileTool.
     * @see CompileTool
     */
    public void setCompileTool(CompileTool wscompile) {
        //no op 
    }
}

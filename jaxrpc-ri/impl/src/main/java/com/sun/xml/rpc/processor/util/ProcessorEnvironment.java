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

package com.sun.xml.rpc.processor.util;

import java.net.URLClassLoader;

import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.util.localization.Localizable;

/**
 *
 * @author JAX-RPC Development Team
 */
public interface ProcessorEnvironment
    extends com.sun.xml.rpc.spi.tools.ProcessorEnvironment {
        
    /*
     * Flags
     */
    int F_VERBOSE		= 1 << 0;
    int F_WARNINGS		= 1 << 1;
    
    /**
     * Set the environment flags
     */
    public void setFlags(int flags);
    
    /**
     * Get the environment flags
     */
    public int getFlags();
    
    /**
     * Get the ClassPath.
     */
    public String getClassPath();
    
    /**
     * Is verbose turned on
     */
    public boolean verbose();
    
    /**
     * Remember a generated file and its type so that it
     * can be removed later, if appropriate.
     */
    public void addGeneratedFile(GeneratedFileInfo file);
    
    
    /**
     * Delete all the generated files made during the execution of this
     * environment (those that have been registered with the "addGeneratedFile"
     * method)
     */
    public void deleteGeneratedFiles();
    
    /**
     * Get a URLClassLoader from using the classpath
     */
    public URLClassLoader getClassLoader();
    
    /**
     * Release resources, if any.
     */
    public void shutdown();
    
    public void error(Localizable msg);
    
    public void warn(Localizable msg);
    
    public void info(Localizable msg);
    
    public void printStackTrace(Throwable t);
    
    public Names getNames();
    
    public int getErrorCount();
    public int getWarningCount();
}

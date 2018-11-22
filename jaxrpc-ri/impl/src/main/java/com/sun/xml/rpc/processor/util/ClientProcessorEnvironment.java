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

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.xml.rpc.processor.ProcessorNotificationListener;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.util.JAXRPCClassFactory;
import com.sun.xml.rpc.util.localization.Localizable;

/**
 *
 * @author JAX-RPC Development Team
 */
public class ClientProcessorEnvironment extends ProcessorEnvironmentBase
    implements ProcessorEnvironment {
    
    /**
     * The stream where error message are printed.
     */
    private OutputStream out;
    
    /**
     * A printwriter created lazily in case there are exceptions to report.
     */
    private PrintStream outprintstream;
    
    /**
     * listener for error/warning/info notifications
     */
    private ProcessorNotificationListener listener;
    
    /**
     * The classpath to use
     */
    private String classPath;
    
    /**
     * list of generated source files created in this environment and
     * its type
     */
    private List generatedFiles = new ArrayList();
    
    /**
     * The number of errors and warnings
     */
    private int nwarnings;
    private int nerrors;
    
    /**
     * flags
     */
    private int flags;
    
    private Names names;
    
    /**
     * Create a ClientProcessorEnvironment with the given class path,
     * stream for messages and ProcessorNotificationListener.
     */
    public ClientProcessorEnvironment(
        OutputStream out,
        String classPath,
        ProcessorNotificationListener listener) {
            
        this.out = out;
        this.classPath = classPath;
        this.listener = listener;
        flags = 0;
        
        //bug fix:4904604
        names = JAXRPCClassFactory.newInstance().createNames();
    }
    
    /**
     * Set the environment flags
     */
    public void setFlags(int flags) {
        this.flags = flags;
    }
    
    /**
     * Get the environment flags
     */
    public int getFlags() {
        return flags;
    }
    
    /**
     * Get the ClassPath.
     */
    public String getClassPath() {
        return classPath;
    }
    
    /**
     * Is verbose turned on
     */
    public boolean verbose() {
        return (flags & F_VERBOSE) != 0;
    }
    
    /**
     * Remember info on  generated source file generated so that it
     * can be removed later, if appropriate.
     */
    public void addGeneratedFile(GeneratedFileInfo file) {
        generatedFiles.add(file);
    }
    
    /**
     * Return all the generated files and its types.
     */
    public Iterator getGeneratedFiles() {
        return generatedFiles.iterator();
    }
    
    /**
     * Delete all the generated source files made during the execution
     * of this environment (those that have been registered with the
     * "addGeneratedFile" method).
     */
    public void deleteGeneratedFiles() {
        synchronized (generatedFiles) {
            Iterator iter = generatedFiles.iterator();
            while (iter.hasNext()) {
                File file = ((GeneratedFileInfo)iter.next()).getFile();
                if (file.getName().endsWith(".java")) {
                    file.delete();
                }
            }
            generatedFiles.clear();
        }
    }
    
    /**
     * Release resources, if any.
     */
    public void shutdown() {
        listener = null;
        generatedFiles = null;
    }
    
    public void error(Localizable msg) {
        if (listener != null) {
            listener.onError(msg);
        }
        nerrors++;
    }
    
    public void warn(Localizable msg) {
        if (warnings()) {
            nwarnings++;
            if (listener != null) {
                listener.onWarning(msg);
            }
        }
    }
    
    public void info(Localizable msg) {
        if (listener != null) {
            listener.onInfo(msg);
        }
    }
    
    public void printStackTrace(Throwable t) {
        if (outprintstream == null) {
            outprintstream = new PrintStream(out);
        }
        t.printStackTrace(outprintstream);
    }
    
    public Names getNames() {
        return names;
    }
    
    public int getErrorCount() {
        return nerrors;
    }
    
    public int getWarningCount() {
        return nwarnings;
    }
    
    private boolean warnings() {
        return (flags & F_WARNINGS) != 0;
    }
    
    //bug fix:4904604
    //to called in compileTool after env is
    public void setNames(Names names) {
        this.names = names;
    }
    
}

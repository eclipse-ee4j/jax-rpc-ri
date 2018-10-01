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

import com.sun.xml.rpc.processor.generator.GeneratorException;
import com.sun.xml.rpc.util.ClassNameInfo;

/**
 * Util provides static utility methods used by other wscompile classes.
 *
 * @author JAX-RPC Development Team
 */
public class DirectoryUtil  {
    
    public static File getOutputDirectoryFor(String theClass,
        File rootDir, ProcessorEnvironment env) throws GeneratorException {
            
        File outputDir = null;
        String qualifiedClassName = theClass;
        String packagePath = null;
        String packageName = ClassNameInfo.getQualifier(qualifiedClassName);
        if (packageName == null) {
            packageName = "";
        } else if (packageName.length() > 0) {
            packagePath = packageName.replace('.', File.separatorChar);
        }
        
        // Do we have a root directory?
        if (rootDir != null) {
            
            // Yes, do we have a package name?
            if (packagePath != null) {
                
                // Yes, so use it as the root. Open the directory...
                outputDir = new File(rootDir, packagePath);
                
                // Make sure the directory exists...
                ensureDirectory(outputDir,env);
            } else {
                
                // Default package, so use root as output dir...
                outputDir = rootDir;
            }
        } else {
            
            // No root directory. Get the current working directory...
            String workingDirPath = System.getProperty("user.dir");
            File workingDir = new File(workingDirPath);
            
            // Do we have a package name?
            if (packagePath == null) {
                
                // No, so use working directory...
                outputDir = workingDir;
            } else {
                
                // Yes, so use working directory as the root...
                outputDir = new File(workingDir, packagePath);
                
                // Make sure the directory exists...
                ensureDirectory(outputDir,env);
            }
        }
        
        // Finally, return the directory...
        return outputDir;
    }
    
    private static void ensureDirectory(File dir, ProcessorEnvironment env)
        throws GeneratorException {
            
        if (!dir.exists()) {
            dir.mkdirs();
            if (!dir.exists()) {
                throw new GeneratorException("generator.cannot.create.dir",
                    dir.getAbsolutePath());
            }
        }
    }
}


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

/**
 * A container to hold info on the files that get
 * generated.
 *
 * @author JAX-RPC Development Team
 */
public class GeneratedFileInfo
    implements com.sun.xml.rpc.spi.tools.GeneratedFileInfo {
    
    /**
     * local variables
     */
    private File file = null;
    private String type = null;
    
    /* constructor */
    public GeneratedFileInfo() {}
    
    /**
     * Adds the file object to the container
     *
     * @param instance of the file to be added
     * @return void
     */
    public void setFile( File file ) {
        this.file = file;
    }
    
    /**
     * Adds the type of file it is the container
     *
     * @param Type string which specifices the type
     * @return void
     */
    public void setType( String type ) {
        this.type = type;
    }
    
    /**
     * Gets the file that got added
     *
     * @param none
     * @return File instance
     */
    public File getFile() {
        return( file );
    }
    
    /**
     * Get the file type that got added
     *
     * @param none
     * @return File type of datatype String
     */
    public String getType() {
        return ( type );
    }
}

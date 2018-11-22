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

package com.sun.xml.rpc.installer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class Main {
    public static void main(String[] args) throws IOException {
    	Reader in = new InputStreamReader(Main.class.getResourceAsStream("/license.txt"));
        new LicenseForm(in) {
            protected void install() {
                try {
                    Main.install();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.show();
    }
    
    /**
     * Does the actual installation.
     */
    private static void install() throws IOException {
        ZipInputStream zip = new ZipInputStream(Main.class.getResourceAsStream("/package.zip"));
        ZipEntry e;
        while((e=zip.getNextEntry())!=null) {
            File name = new File(e.getName());
            System.out.println(name);
            if( e.isDirectory() ) {
                name.mkdirs();
            } else {
                if( !name.exists() )
                    copyStream( zip,  new FileOutputStream(name) );
            }
        }
        zip.close();
        System.out.println("installation complete");
    }

    public static void copyStream( InputStream in, OutputStream out ) throws IOException {
        byte[] buf = new byte[256];
        int len;
        while((len=in.read(buf))>=0) {
            out.write(buf,0,len);
        }
        out.close();
    }
}

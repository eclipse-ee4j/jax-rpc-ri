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

package com.sun.xml.rpc.sp;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class ParseException extends Exception {

    private String publicId;
    private String systemId;
    private int line;
    private int col;

    public ParseException(
        String message,
        String publicId,
        String systemId,
        int line,
        int col) {
        super(message);
        this.publicId = publicId;
        this.systemId = systemId;
        this.line = line;
        this.col = col;
    }

    public ParseException(String message, StreamingParser parser) {
        this(
            message,
            parser.publicId(),
            parser.systemId(),
            parser.line(),
            parser.column());
    }

    public ParseException(String message, String publicId, String systemId) {
        this(message, publicId, systemId, -1, -1);
    }

    public ParseException(String message) {
        this(message, null, null, -1, -1);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getName());
        if (publicId != null)
            sb.append(": " + publicId);
        if (systemId != null)
            sb.append(": " + systemId);
        if (line != -1) {
            sb.append(":" + line);
            if (col != -1)
                sb.append("," + col);
        }
        if (getMessage() != null)
            sb.append(": " + getMessage());
        return sb.toString();
    }

}

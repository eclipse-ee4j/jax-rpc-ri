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

package com.sun.xml.rpc.tools.wscompile;

import java.io.IOException;
import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.util.IndentingWriter;

/**
 * @author JAX-RPC Development Team
 *
 */
public interface TieHooksIf {
    public void writeTieStatic(Model model, Port port, IndentingWriter p) throws IOException;
    public void writeTieStatic(Model model, IndentingWriter p) throws IOException;
    public void preHandlingHook(Model model, IndentingWriter p,
        TieHooksState state) throws IOException;
    public void postResponseWritingHook(Model model, IndentingWriter p,
        TieHooksState state) throws IOException;
    public class TieHooksState {
        public boolean superDone;
    }
}
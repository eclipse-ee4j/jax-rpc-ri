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

public interface CompileTool {

    /**
     * Which one should we use? run() or run(String args[]).
     * We have to define setters for each arguments if we were to
     * use run();
     */
    public void run() throws Exception;

    /**
     * run(String[] args) for now.
     */
    public boolean run(String[] args);

    public void setDelegate(CompileToolDelegate delegate);

    public Processor getProcessor();
    public ProcessorEnvironment getEnvironment();
}

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

package com.sun.xml.rpc.processor.modeler.rmi;

import com.sun.xml.rpc.util.ClassNameInfo;

/**
 * @author JAX-RPC Development Team
 */
public final class ClassType extends RmiType {
    String className;

    ClassType(String typeSig, String className) {
        super(TC_CLASS, typeSig);
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String typeString(boolean abbrev) {
        String tmp = className;
        if (abbrev)
            tmp = ClassNameInfo.getName(tmp);
        return tmp;
    }

    public boolean isNillable() {
        return true;
    }
}

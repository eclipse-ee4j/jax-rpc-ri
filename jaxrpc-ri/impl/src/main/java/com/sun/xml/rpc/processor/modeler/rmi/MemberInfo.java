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

/**
 *
 * @author JAX-RPC Development Team
 */
public class MemberInfo {
    private RmiType type;
    private boolean isPublic = false;
    private String readMethod;
    private String writeMethod;
    private String name;
    private Class declaringClass;
    private Class sortingClass;

    private MemberInfo() {
    }

    public MemberInfo(String name, RmiType type, boolean isPublic) {

        this.type = type;
        this.isPublic = isPublic;
        this.name = name;
    }
    public MemberInfo(RmiType type, boolean isPublic) {
        this.type = type;
        this.isPublic = isPublic;
    }
    public RmiType getType() {
        return type;
    }
    public boolean isPublic() {
        return isPublic;
    }
    public String getReadMethod() {
        return readMethod;
    }
    public void setReadMethod(String readMethod) {
        this.readMethod = readMethod;
    }
    public String getWriteMethod() {
        return writeMethod;
    }
    public void setWriteMethod(String writeMethod) {
        this.writeMethod = writeMethod;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Class getDeclaringClass() {
        return declaringClass;
    }
    public void setDeclaringClass(Class declaringClass) {
        this.declaringClass = declaringClass;
    }

    public Class getSortingClass() {
        return sortingClass;
    }
    public void setSortingClass(Class sortingClass) {
        this.sortingClass = sortingClass;
    }
}

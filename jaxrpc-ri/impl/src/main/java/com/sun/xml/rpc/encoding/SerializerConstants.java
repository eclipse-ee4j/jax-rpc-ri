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

package com.sun.xml.rpc.encoding;

/**
 *
 * @author JAX-RPC Development Team
 */
public interface SerializerConstants {
    public static final boolean ENCODE_TYPE             = true;
    public static final boolean DONT_ENCODE_TYPE        = false;
    public static final boolean SERIALIZE_AS_REF        = true;
    public static final boolean DONT_SERIALIZE_AS_REF   = false;
    public static final boolean REFERENCEABLE           = true;
    public static final boolean NOT_REFERENCEABLE       = false;
    public static final boolean NULLABLE                = true;
    public static final boolean NOT_NULLABLE            = false;
    public static final boolean REFERENCED_INSTANCE     = true;
    public static final boolean UNREFERENCED_INSTANCE   = false;
}

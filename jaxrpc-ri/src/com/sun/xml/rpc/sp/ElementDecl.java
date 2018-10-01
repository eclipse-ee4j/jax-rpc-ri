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
 * Represents all of the DTD information about an element.  That
 * includes:  <UL>
 *
 *	<LI> Element name
 *
 *	<LI> Content model ... either ANY, EMPTY, or a parenthesized
 *	regular expression matching the content model in the DTD
 *	(but with whitespace removed)
 *
 *	<LI> A hashtable mapping attribute names to the attribute
 *	metadata.
 *
 *	</UL>
 *
 * <P> This also records whether the element was declared in the
 * internal subset, for use in validating standalone declarations.
 *
 * @author David Brownell
 * @author JAX-RPC RI Development Team
 */
class ElementDecl {
    /** The element type name. */
    String name;

    /** The name of the element's ID attribute, if any */
    String id;

    // EMPTY
    // ANY
    // (#PCDATA) or (#PCDATA|name|...)
    // (name,(name|name|...)+,...) etc

    /** The compressed content model for the element */
    String contentType;

    // non-null only when validating; holds a data structure
    // representing (name,(name|name|...)+,...) style models
    ContentModel model;

    /** True for EMPTY and CHILDREN content models */
    boolean ignoreWhitespace;

    /** Used to validate standalone declarations */
    boolean isFromInternalSubset;

    SimpleHashtable attributes = new SimpleHashtable();

    ElementDecl(String s) {
        name = s;
    }
}

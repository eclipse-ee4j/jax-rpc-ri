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

package com.sun.xml.rpc.encoding.simpletype;


/**
 *
 * @author JAX-RPC Development Team
 */
public abstract class XSDBase64EncoderBase extends SimpleTypeEncoderBase {
    
    protected XSDBase64EncoderBase() {
    }
    
    protected static final char encodeBase64[] = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    protected static final int decodeBase64[] = {
        /*'+'*/ 62,
        -1, -1, -1,
        /*'/'*/ 63,
        /*'0'*/ 52,
        /*'1'*/ 53,
        /*'2'*/ 54,
        /*'3'*/ 55,
        /*'4'*/ 56,
        /*'5'*/ 57,
        /*'6'*/ 58,
        /*'7'*/ 59,
        /*'8'*/ 60,
        /*'9'*/ 61,
        -1, -1, -1, -1, -1, -1, -1,
        /*'A'*/ 0,
        /*'B'*/ 1,
        /*'C'*/ 2,
        /*'D'*/ 3,
        /*'E'*/ 4,
        /*'F'*/ 5,
        /*'G'*/ 6,
        /*'H'*/ 7,
        /*'I'*/ 8,
        /*'J'*/ 9,
        /*'K'*/ 10,
        /*'L'*/ 11,
        /*'M'*/ 12,
        /*'N'*/ 13,
        /*'O'*/ 14,
        /*'P'*/ 15,
        /*'Q'*/ 16,
        /*'R'*/ 17,
        /*'S'*/ 18,
        /*'T'*/ 19,
        /*'U'*/ 20,
        /*'V'*/ 21,
        /*'W'*/ 22,
        /*'X'*/ 23,
        /*'Y'*/ 24,
        /*'Z'*/ 25,
        -1, -1, -1, -1, -1, -1,
        /*'a'*/ 26,
        /*'b'*/ 27,
        /*'c'*/ 28,
        /*'d'*/ 29,
        /*'e'*/ 30,
        /*'f'*/ 31,
        /*'g'*/ 32,
        /*'h'*/ 33,
        /*'i'*/ 34,
        /*'j'*/ 35,
        /*'k'*/ 36,
        /*'l'*/ 37,
        /*'m'*/ 38,
        /*'n'*/ 39,
        /*'o'*/ 40,
        /*'p'*/ 41,
        /*'q'*/ 42,
        /*'r'*/ 43,
        /*'s'*/ 44,
        /*'t'*/ 45,
        /*'u'*/ 46,
        /*'v'*/ 47,
        /*'w'*/ 48,
        /*'x'*/ 49,
        /*'y'*/ 50,
        /*'z'*/ 51
    };
}

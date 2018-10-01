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
public class EncoderUtils {
    public static boolean needsCollapsing(String str) {
        int len = str.length();
        int spanLen = 0;

        for (int idx = 0; idx < len; ++idx) {
            if (Character.isWhitespace(str.charAt(idx))) {
                ++spanLen;
            } else if (spanLen > 0) {
                if (spanLen == idx) {
                    // leading whitespace
                    return true;
                } else {
                    // non-leading, non-trailing whitespace
                    if (str.charAt(idx - spanLen) != ' ') {
                        // first whitespace character is not a space
                        return true;
                    }
                    if (spanLen > 1) {
                        // there is a span of multiple whitespace characters
                        return true;
                    }
                }

                spanLen = 0;
            }
        }

        if (spanLen > 0) {
            // trailing whitespace
            return true;
        }

        return false;
    }

    public static String collapseWhitespace(String str) {
        if (!needsCollapsing(str)) {
            return str;
        }

        // the assumption is that most strings will not need to be collapsed,
        // so the code below will usually not be reached

        int len = str.length();
        char[] buf = new char[len];
        str.getChars(0, len, buf, 0);

        int leadingWSLen = 0;
        int trailingWSLen = 0;
        int spanLen = 0;

        for (int idx = 0; idx < len; ++idx) {
            if (Character.isWhitespace(buf[idx])) {
                ++spanLen;
            } else if (spanLen > 0) {
                if (spanLen == idx) {
                    // leading whitespace
                    leadingWSLen = spanLen;
                } else {
                    // non-leading, non-trailing whitespace

                    // ensure that the first whitespace character is a space
                    int firstWSIdx = idx - spanLen;
                    buf[firstWSIdx] = ' ';

                    if (spanLen > 1) {
                        // remove all but the first whitespace character
                        System.arraycopy(
                            buf,
                            idx,
                            buf,
                            firstWSIdx + 1,
                            len - idx);
                        len -= (spanLen - 1);
                        idx = firstWSIdx + 1;
                    }
                }

                spanLen = 0;
            }
        }

        if (spanLen > 0) {
            // trailing whitespace
            trailingWSLen = spanLen;
        }

        return new String(
            buf,
            leadingWSLen,
            len - leadingWSLen - trailingWSLen);
    }

    public static String removeWhitespace(String str) {
        int len = str.length();
        StringBuffer buf = new StringBuffer();
        int firstNonWS = 0;
        int idx = 0;
        for (; idx < len; ++idx) {
            if (Character.isWhitespace(str.charAt(idx))) {
                if (firstNonWS < idx)
                    buf.append(str.substring(firstNonWS, idx));
                firstNonWS = idx + 1;
            }
        }
        if (firstNonWS < idx)
            buf.append(str.substring(firstNonWS, idx));
        return buf.toString();
    }
}

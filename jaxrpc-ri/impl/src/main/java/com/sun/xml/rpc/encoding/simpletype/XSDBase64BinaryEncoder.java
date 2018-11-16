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

import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.streaming.FastInfosetWriter;
import com.sun.xml.rpc.streaming.FastInfosetReader;
import com.sun.xml.rpc.streaming.XmlTreeWriter;

/**
 *
 * @author JAX-RPC Development Team
 */
public class XSDBase64BinaryEncoder extends XSDBase64EncoderBase {
    private static final SimpleTypeEncoder encoder =
        new XSDBase64BinaryEncoder();

    private XSDBase64BinaryEncoder() {
    }

    public static SimpleTypeEncoder getInstance() {
        return encoder;
    }

    public String objectToString(Object obj, XMLWriter writer)
        throws Exception {
            
        if (obj == null) {
            return null;
        }
        byte[] value = (byte[]) obj;
        if (value.length == 0) {
            return "";
        }
        
        int blockCount = value.length / 3;
        int partialBlockLength = value.length % 3;

        if (partialBlockLength != 0) {
            ++blockCount;
        }

        int encodedLength = blockCount * 4;
        StringBuffer encodedValue = new StringBuffer(encodedLength);

        int idx = 0;
        for (int i = 0; i < blockCount; ++i) {
            int b1 = value[idx++];
            int b2 = (idx < value.length) ? value[idx++] : 0;
            int b3 = (idx < value.length) ? value[idx++] : 0;

            if (b1 < 0) {
                b1 += 256;
            }
            if (b2 < 0) {
                b2 += 256;
            }
            if (b3 < 0) {
                b3 += 256;
            }

            char encodedChar;

            encodedChar = encodeBase64[b1 >> 2];
            encodedValue.append(encodedChar);

            encodedChar = encodeBase64[((b1 & 0x03) << 4) | (b2 >> 4)];
            encodedValue.append(encodedChar);

            encodedChar = encodeBase64[((b2 & 0x0f) << 2) | (b3 >> 6)];
            encodedValue.append(encodedChar);

            encodedChar = encodeBase64[b3 & 0x3f];
            encodedValue.append(encodedChar);
        }

        switch (partialBlockLength) {
            case 0 :
                // do nothing
                break;
            case 1 :
                encodedValue.setCharAt(encodedLength - 1, '=');
                encodedValue.setCharAt(encodedLength - 2, '=');
                break;
            case 2 :
                encodedValue.setCharAt(encodedLength - 1, '=');
                break;
        }

        return encodedValue.toString();
    }

    public Object stringToObject(String str, XMLReader reader)
        throws Exception {
            
        if (str == null) {
            return null;
        }

        String uri = "";
        String encodedValue = EncoderUtils.removeWhitespace(str);
        int encodedLength = encodedValue.length();
        if (encodedLength == 0) {
            return new byte[0];
        }
        /*      String encodedValue = EncoderUtils.collapseWhitespace(str);
              int encodedLength = encodedValue.length();
              if (encodedLength == 0) {
                  return new byte[0];
              }*/
        int blockCount = encodedLength / 4;
        int partialBlockLength = 3;

        if (encodedValue.charAt(encodedLength - 1) == '=') {
            --partialBlockLength;
            if (encodedValue.charAt(encodedLength - 2) == '=') {
                --partialBlockLength;
            }
        }

        int valueLength = (blockCount - 1) * 3 + partialBlockLength;
        byte[] value = new byte[valueLength];

        int idx = 0;
        int encodedIdx = 0;
        for (int i = 0; i < blockCount; ++i) {
            int x1 = decodeBase64[encodedValue.charAt(encodedIdx++) - '+'];
            int x2 = decodeBase64[encodedValue.charAt(encodedIdx++) - '+'];
            int x3 = decodeBase64[encodedValue.charAt(encodedIdx++) - '+'];
            int x4 = decodeBase64[encodedValue.charAt(encodedIdx++) - '+'];

            value[idx++] = (byte) ((x1 << 2) | (x2 >> 4));
            if (idx < valueLength) {
                value[idx++] = (byte) (((x2 & 0x0f) << 4) | (x3 >> 2));
            }
            if (idx < valueLength) {
                value[idx++] = (byte) (((x3 & 0x03) << 6) | x4);
            }
        }

        return value;
    }

    public void writeValue(Object obj, XMLWriter writer) throws Exception {
        if (obj == null) {
            return;
        }
        byte[] value = (byte[]) obj;
        if (value.length == 0) {
            return;
        }
       
        // Use the Base64 encoding algorithm to avoid base64 encoding with FI
        if (writer instanceof FastInfosetWriter) {
            ((FastInfosetWriter) writer).writeBytes(value, 0, value.length);
            return;
        }
        
        /*
          * Use a single write event when writing to an XmlTreeWriter to avoid creating
          * multiple DOM nodes. This is also necessary to ensure that the resulting DOM
          * is propertly serialized as an FI document (containing a single char token).
          */
        if (writer instanceof XmlTreeWriter) {
            writer.writeCharsUnquoted(objectToString(obj, null));
            return;
        }
        
        int blockCount = value.length / 3;
        int partialBlockLength = value.length % 3;

        if (partialBlockLength != 0) {
            ++blockCount;
        }

        int encodedLength = blockCount * 4;
        char[] encodedValue = new char[4];

        int idx = 0;
        for (int i = 0; i < blockCount; ++i) {
            int b1 = value[idx++];
            int b2 = (idx < value.length) ? value[idx++] : 0;
            int b3 = (idx < value.length) ? value[idx++] : 0;

            if (b1 < 0) {
                b1 += 256;
            }
            if (b2 < 0) {
                b2 += 256;
            }
            if (b3 < 0) {
                b3 += 256;
            }

            char encodedChar;

            encodedChar = encodeBase64[b1 >> 2];
            encodedValue[0] = encodedChar;

            encodedChar = encodeBase64[((b1 & 0x03) << 4) | (b2 >> 4)];
            encodedValue[1] = encodedChar;

            encodedChar = encodeBase64[((b2 & 0x0f) << 2) | (b3 >> 6)];
            encodedValue[2] = encodedChar;

            encodedChar = encodeBase64[b3 & 0x3f];
            encodedValue[3] = encodedChar;

            if (i == blockCount - 1) {
                switch (partialBlockLength) {
                    case 0 :
                        // do nothing
                        break;
                    case 1 :
                        encodedValue[2] = '=';
                        encodedValue[3] = '=';
                        break;
                    case 2 :
                        encodedValue[3] = '=';
                        break;
                }
            }

            writer.writeCharsUnquoted(encodedValue, 0, 4);
        }
    }
}

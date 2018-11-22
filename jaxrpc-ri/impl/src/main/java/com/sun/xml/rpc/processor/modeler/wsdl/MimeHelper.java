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

package com.sun.xml.rpc.processor.modeler.wsdl;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;

/**
 * @author Vivek Pandey
 *  
 */
public class MimeHelper {
    /**
     * @param newMimePart
     * @return
     */
    protected static String getAttachmentUniqueID(String mimePart) {
        //return "uuid@" + mimePart;
        return mimePart;
    }

    /**
     * @param mimeType
     * @return
     */
    protected static boolean isMimeTypeBinary(String mimeType) {
        if (mimeType.equals(JPEG_IMAGE_MIME_TYPE)
            || mimeType.equals(GIF_IMAGE_MIME_TYPE)
        ) {
            return true;
        } else if (
            mimeType.equals(TEXT_XML_MIME_TYPE)
                || mimeType.equals(TEXT_HTML_MIME_TYPE)
                || mimeType.equals(TEXT_PLAIN_MIME_TYPE)
                || mimeType.equals(APPLICATION_XML_MIME_TYPE)
                || mimeType.equals(MULTIPART_MIME_TYPE)) {
            return false;
        }
        //some unknown mime type, will be mapped to DataHandler java type so
        // return true
        return true;
    }

    protected static void initMimeTypeToJavaType() {        
        mimeTypeToJavaType.put(JPEG_IMAGE_MIME_TYPE, javaType.IMAGE_JAVATYPE);
        //mimeTypeToJavaType.put(PNG_IMAGE_MIME_TYPE, javaType.IMAGE_JAVATYPE);
        mimeTypeToJavaType.put(GIF_IMAGE_MIME_TYPE,
         javaType.IMAGE_JAVATYPE);
        mimeTypeToJavaType.put(TEXT_XML_MIME_TYPE, javaType.SOURCE_JAVATYPE);
        //mimeTypeToJavaType.put(TEXT_HTML_MIME_TYPE, javaType.SOURCE_JAVATYPE);
        mimeTypeToJavaType.put(
            APPLICATION_XML_MIME_TYPE,
            javaType.SOURCE_JAVATYPE);
        mimeTypeToJavaType.put(TEXT_PLAIN_MIME_TYPE, javaType.STRING_JAVATYPE);
        mimeTypeToJavaType.put(
            MULTIPART_MIME_TYPE,
            javaType.MIME_MULTIPART_JAVATYPE);

    }

    protected static Map mimeTypeToJavaType;
    protected static JavaSimpleTypeCreator javaType;

    public static final String JPEG_IMAGE_MIME_TYPE = "image/jpeg";
    //public static final String PNG_IMAGE_MIME_TYPE = "image/png";
    public static final String GIF_IMAGE_MIME_TYPE = "image/gif";
    public static final String TEXT_XML_MIME_TYPE = "text/xml";
    public static final String TEXT_HTML_MIME_TYPE = "text/html";
    public static final String TEXT_PLAIN_MIME_TYPE = "text/plain";
    public static final String APPLICATION_XML_MIME_TYPE = "application/xml";
    public static final String MULTIPART_MIME_TYPE = "multipart/*";

    /**
     * 
     */
    public MimeHelper() {
        mimeTypeToJavaType = new HashMap();
        javaType = new JavaSimpleTypeCreator();
        initMimeTypeToJavaType();
    }

}

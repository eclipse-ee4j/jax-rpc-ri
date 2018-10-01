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

package com.sun.xml.rpc.processor.model.literal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.processor.model.java.JavaType;

/**
 * @author Vivek Pandey
 *
 */
public class LiteralAttachmentType extends LiteralType {

    public LiteralAttachmentType() {
    }

    public LiteralAttachmentType(QName name, JavaType javaType) {
        super(name, javaType);
    }
    
    /* (non-Javadoc)
     * @see com.sun.xml.rpc.processor.model.literal.LiteralType#accept(com.sun.xml.rpc.processor.model.literal.LiteralTypeVisitor)
     */
    public void accept(LiteralTypeVisitor visitor) throws Exception {
        visitor.visit(this);

    }
    
    public void setMIMEType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public String getMIMEType() {
        return mimeType;
    }
    
    // a part in mime:content can have alternative mimeTypes
    public void addAlternateMIMEType(String mimeType) {
        if(alternateMIMETypes == null){
            alternateMIMETypes = new ArrayList();
        }
        alternateMIMETypes.add(mimeType);
    }   
    
    public void addAlternateMIMEType(Iterator mimeTypes) {
        if(alternateMIMETypes == null){
            alternateMIMETypes = new ArrayList();
        }
        while(mimeTypes.hasNext()) {
            alternateMIMETypes.add((String)mimeTypes.next());
        }
    }
    
    public List getAlternateMIMETypes() {
        if(alternateMIMETypes == null) {
            List mimeTypes = new ArrayList();
            mimeTypes.add(mimeType);
            return mimeTypes;
        }
        return alternateMIMETypes;
    }
    
    public void setAlternateMIMETypes(List l) {        
        alternateMIMETypes = l;
    }
    
    public void setSwaRef(boolean isSwaRef){
        this.isSwaRef = isSwaRef;  
    }
    
    public boolean isSwaRef() {
        return isSwaRef;
    }
    
    public String getContentID() {
        return contentId;
    }
    
    public void setContentID(String id) {
        contentId = id;
    }
    
    private String mimeType;
    private List alternateMIMETypes;
    private String contentId;
    private boolean isSwaRef = false;
}

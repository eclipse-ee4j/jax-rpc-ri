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

import javax.xml.namespace.QName;

/**
 * All serializers in the JAX-RPC SI extend this interface.
 * 
 * @author JAX-RPC Development Team
 */
public interface CombinedSerializer
    extends JAXRPCSerializer, JAXRPCDeserializer {
    /** Returns the XML schema type processed by this serializer. 
     * 
     *  @return Returns the XML schema type processed by this serializer.
    **/
    public QName getXmlType();

    /** Returns whether xsi:type information will be encoded 
     * 
     * @return  Returns whether xsi:type information will be encoded
    **/
    public boolean getEncodeType();

    /** Returns whether serializer allows null values
     * 
     * @return Returns whether serializer allows null values
     */
    public boolean isNullable();

    /** Returns the encodingStyle of this serializer
     * 
     * @return Returns the encodingStyle of this serializer
     */
    public String getEncodingStyle();

    /** Returns the serializer that actually does the serialization
     * 
     * @return Returns the serializer that actually does the serialization
     */
    public CombinedSerializer getInnermostSerializer();
}

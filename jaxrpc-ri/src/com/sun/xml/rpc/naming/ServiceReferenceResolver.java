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

/**
*
* @author JAX-RPC Development Team
*/
package com.sun.xml.rpc.naming;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import javax.xml.rpc.Service;

public class ServiceReferenceResolver implements ObjectFactory {
    protected static final Map registeredServices =
        Collections.synchronizedMap(new HashMap());
    
    public Object getObjectInstance(Object obj, Name name,
        Context nameCtx, Hashtable environment) throws Exception {
        
        if (obj instanceof StringRefAddr) {
            StringRefAddr ref = (StringRefAddr) obj;
            if (ref.getType() == "ServiceName") {
                return registeredServices.get(ref.getContent());
            } else if (ref.getType() == "ServiceClassName") {
                Object serviceKey = ref.getContent();
                Object service = registeredServices.get(serviceKey);
                if (service == null) {
                    ClassLoader ctxLoader =
                        Thread.currentThread().getContextClassLoader();
                    service = Class.forName((String) ref.getContent(),
                        true, ctxLoader).newInstance();
                    registeredServices.put(serviceKey, service);
                }
                return service;
            }
        }
        return null;
    }
    
    public static String registerService(Service service) {
        String serviceName = getQualifiedServiceNameString(service);
        registeredServices.put(serviceName, service);
        return serviceName;
    }
    
    protected static String getQualifiedServiceNameString(Service service) {
        String serviceName = "";
        URL wsdlLocation = service.getWSDLDocumentLocation();
        if (wsdlLocation != null) {
            serviceName += wsdlLocation.toExternalForm() + ":";
        }
        serviceName += service.getServiceName().toString();
        return serviceName;
    }
    
    public Reference getServiceClassReference(Class serviceClass) {
        return getServiceClassReference(serviceClass.getName());
    }
    
    public Reference getServiceClassReference(String serviceClassName) {
        Reference reference = new Reference(serviceClassName,
            "com.sun.xml.rpc.naming.ServiceReferenceResolver", null);
        reference.add(new StringRefAddr("ServiceClassName", serviceClassName));
        return reference;
    }
}

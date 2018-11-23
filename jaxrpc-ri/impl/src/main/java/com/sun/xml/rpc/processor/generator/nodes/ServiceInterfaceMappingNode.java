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

package com.sun.xml.rpc.processor.generator.nodes;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.processor.model.ModelProperties;
import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.model.Service;
import com.sun.xml.rpc.processor.util.ProcessorEnvironment;

/**
 * @author  Qingqing Ouyang
 * @version 1.1.5
 */
public class ServiceInterfaceMappingNode extends JaxRpcMappingNode {

    /**
     * Default constructor.
     */
    public ServiceInterfaceMappingNode() {
    }

    /**
     * write the appropriate information to a DOM tree and return it
     *
     * @param parent node in the DOM tree 
     * @param nodeName name for the root element for this DOM tree fragment
     * @param config jaxrpc configuration
     * @param service service details to write
     * @return the DOM tree top node
     */
    public Node write(
        Node parent,
        String nodeName,
        Configuration config,
        Service service)
        throws Exception {

        Element node = appendChild(parent, nodeName);

        ProcessorEnvironment env =
            (com.sun.xml.rpc.processor.util.ProcessorEnvironment) config
                .getEnvironment();
        QName serviceQName = service.getName();
        String serviceNS = serviceQName.getNamespaceURI();
        String serviceJavaName =
            env.getNames().customJavaTypeClassName(service.getJavaInterface());

        //service-interface
        appendTextChild(
            node,
            JaxRpcMappingTagNames.SERVICE_INTERFACE,
            serviceJavaName);

        //wsdl-service-name
        //XXX FIXME  Need to handle QName better
        Element wsdlServiceName =
            (Element) appendTextChild(node,
                JaxRpcMappingTagNames.WSDL_SERVICE_NAME,
                "serviceNS:" + serviceQName.getLocalPart());
        wsdlServiceName.setAttributeNS(
            "http://www.w3.org/2000/xmlns/",
            "xmlns:serviceNS",
            serviceNS);

        //port-mapping*
        for (Iterator portIter = service.getPorts(); portIter.hasNext();) {
            Port port = (Port) portIter.next();
            QName portQName =
                (QName) port.getProperty(
                    ModelProperties.PROPERTY_WSDL_PORT_NAME);
            String portName = portQName.getLocalPart();
            String portJavaName = Names.getPortName(port);

            //port-mapping
            Node portMappingNode =
                appendChild(node, JaxRpcMappingTagNames.PORT_MAPPING);

            //port-name
            appendTextChild(
                portMappingNode,
                JaxRpcMappingTagNames.PORT_NAME,
                portName);

            //java-port-name
            appendTextChild(
                portMappingNode,
                JaxRpcMappingTagNames.JAVA_PORT_NAME,
                portJavaName);
        }

        return node;
    }

    private final static String MYNAME = "ServiceInterfaceMappingNode";
}

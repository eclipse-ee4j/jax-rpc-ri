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

package com.sun.xml.rpc.soap;

/**
 * @author JAX-RPC Development Team
 */

public class SOAPConstantsFactory {
	private static final SOAPConstantsFactory factory =
		new SOAPConstantsFactory();

	private SOAPConstantsFactory() {
		namespaceConstants11 =
			new SOAPNamespaceConstantsImpl(SOAPVersion.SOAP_11);
		wsdlConstants11 = new SOAPWSDLConstantsImpl(SOAPVersion.SOAP_11);
		encodingConstants11 =
			new SOAPEncodingConstantsImpl(SOAPVersion.SOAP_11);

		namespaceConstants12 =
			new SOAPNamespaceConstantsImpl(SOAPVersion.SOAP_12);
		wsdlConstants12 = new SOAPWSDLConstantsImpl(SOAPVersion.SOAP_12);
		encodingConstants12 =
			new SOAPEncodingConstantsImpl(SOAPVersion.SOAP_12);
	}

	public static SOAPNamespaceConstants getSOAPNamespaceConstants(SOAPVersion ver) {
		if (ver == SOAPVersion.SOAP_11)
			return namespaceConstants11;
		else if (ver == SOAPVersion.SOAP_12)
			return namespaceConstants12;
		return null;
	}

	public static SOAPWSDLConstants getSOAPWSDLConstants(SOAPVersion ver) {
		if (ver == SOAPVersion.SOAP_11)
			return wsdlConstants11;
		else if (ver == SOAPVersion.SOAP_12)
			return wsdlConstants12;
		return null;
	}

	public static SOAPEncodingConstants getSOAPEncodingConstants(SOAPVersion ver) {
		if (ver == SOAPVersion.SOAP_11)
			return encodingConstants11;
		else if (ver == SOAPVersion.SOAP_12)
			return encodingConstants12;
		return null;
	}

	private static SOAPNamespaceConstants namespaceConstants11;
	private static SOAPWSDLConstants wsdlConstants11;
	private static SOAPEncodingConstants encodingConstants11;

	private static SOAPNamespaceConstants namespaceConstants12;
	private static SOAPWSDLConstants wsdlConstants12;
	private static SOAPEncodingConstants encodingConstants12;
}

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

import javax.xml.namespace.QName;

/**
 * @author JAX-RPC Development Team
 */

public interface SOAPWSDLConstants {
	/** NS_WSDL_SOAP */
	public String getWSDLSOAPNamespace();

	/** NS_SOAP_ENCODING */
	public String getSOAPEncodingNamespace();

	/** URI_SOAP_TRANSPORT_HTTP */
	public String getSOAPTransportHttpURI();

	/** QNames */
	public QName getQNameAddress();
	public QName getQNameBinding();
	public QName getQNameBody();
	public QName getQNameFault();
	public QName getQNameHeader();
	public QName getQNameHeaderFault();
	public QName getQNameOperation();

	/** SOAP encoding QNames */
	public QName getQNameTypeArray();
	public QName getQNameAttrGroupCommonAttributes();
	public QName getQNameAttrArrayType();
	//soap12
	public QName getQNameAttrItemType();
	public QName getQNameAttrArraySize();

	public QName getQNameAttrOffset();
	public QName getQNameAttrPosition();

	public QName getQNameTypeBase64();

	public QName getQNameElementString();
	public QName getQNameElementNormalizedString();
	public QName getQNameElementToken();
	public QName getQNameElementByte();
	public QName getQNameElementUnsignedByte();
	public QName getQNameElementBase64Binary();
	public QName getQNameElementHexBinary();
	public QName getQNameElementInteger();
	public QName getQNameElementPositiveInteger();
	public QName getQNameElementNegativeInteger();
	public QName getQNameElementNonNegativeInteger();
	public QName getQNameElementNonPositiveInteger();
	public QName getQNameElementInt();
	public QName getQNameElementUnsignedInt();
	public QName getQNameElementLong();
	public QName getQNameElementUnsignedLong();
	public QName getQNameElementShort();
	public QName getQNameElementUnsignedShort();
	public QName getQNameElementDecimal();
	public QName getQNameElementFloat();
	public QName getQNameElementDouble();
	public QName getQNameElementBoolean();
	public QName getQNameElementTime();
	public QName getQNameElementDateTime();
	public QName getQNameElementDuration();
	public QName getQNameElementDate();
	public QName getQNameElementGMonth();
	public QName getQNameElementGYear();
	public QName getQNameElementGYearMonth();
	public QName getQNameElementGDay();
	public QName getQNameElementGMonthDay();
	public QName getQNameElementName();
	public QName getQNameElementQName();
	public QName getQNameElementNCNAME();
	public QName getQNameElementAnyURI();
	public QName getQNameElementID();
	public QName getQNameElementIDREF();
	public QName getQNameElementIDREFS();
	public QName getQNameElementEntity();
	public QName getQNameElementEntities();
	public QName getQNameElementNotation();
	public QName getQNameElementNMTOKEN();
	public QName getQNameElementNMTOKENS();

	public QName getQNameTypeString();
	public QName getQNameTypeNormalizedString();
	public QName getQNameTypeToken();
	public QName getQNameTypeByte();
	public QName getQNameTypeUnsignedByte();
	public QName getQNameTypeBase64Binary();
	public QName getQNameTypeHexBinary();
	public QName getQNameTypeInteger();
	public QName getQNameTypePositiveInteger();
	public QName getQNameTypeNegativeInteger();
	public QName getQNameTypeNonNegativeInteger();
	public QName getQNameTypeNonPositiveInteger();
	public QName getQNameTypeInt();
	public QName getQNameTypeUnsignedInt();
	public QName getQNameTypeLong();
	public QName getQNameTypeUnsignedLong();
	public QName getQNameTypeShort();
	public QName getQNameTypeUnsignedShort();
	public QName getQNameTypeDecimal();
	public QName getQNameTypeFloat();
	public QName getQNameTypeDouble();
	public QName getQNameTypeBoolean();
	public QName getQNameTypeTime();
	public QName getQNameTypeDateTime();
	public QName getQNameTypeDuration();
	public QName getQNameTypeDate();
	public QName getQNameTypeGMonth();
	public QName getQNameTypeGYear();
	public QName getQNameTypeGYearMonth();
	public QName getQNameTypeGDay();
	public QName getQNameTypeGMonthDay();
	public QName getQNameTypeName();
	public QName getQNameTypeQName();
	public QName getQNameTypeNCNAME();
	public QName getQNameTypeAnyURI();
	public QName getQNameTypeID();
	public QName getQNameTypeIDREF();
	public QName getQNameTypeIDREFS();
	public QName getQNameTypeENTITY();
	public QName getQNameTypeENTITIES();
	public QName getQNameTypeNotation();
	public QName getQNameTypeNMTOKEN();
	public QName getQNameTypeNMTOKENS();
	public QName getQNameTypeLanguage();

	// SOAP attributes with non-colonized names
	public QName getQNameAttrID();
	public QName getQNameAttrHREF();

	/** SOAP Version used */
	public SOAPVersion getSOAPVersion();
}

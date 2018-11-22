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

package com.sun.xml.rpc.soap.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.stream.StreamSource;

import com.sun.xml.messaging.saaj.util.ByteInputStream;
import com.sun.xml.rpc.util.NullIterator;
import com.sun.xml.rpc.util.xml.XmlUtil;
import com.sun.xml.rpc.client.StubPropertyConstants;

// Dependency with our SAAJ to support FI
import com.sun.xml.messaging.saaj.soap.MessageImpl;
import com.sun.xml.messaging.saaj.soap.MessageFactoryImpl;

/**
 * A MessageContext holds a SOAP message as well as a set
 * (possibly transport-specific) properties.
 *
 * @author JAX-RPC Development Team
 */
public class SOAPMessageContext
	implements com.sun.xml.rpc.spi.runtime.SOAPMessageContext {
                    
	public SOAPMessageContext() {
	}

	public String[] getRoles() {
		return roles;
	}

	/*
	 * Method called internally to set the roles
	 */
	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public SOAPMessage getMessage() {
		return _message;
	}

	public void setMessage(SOAPMessage message) {
		_message = message;
	}

	public boolean isFailure() {
		return _failure;
	}

	public void setFailure(boolean b) {
		_failure = b;
	}

	int currentHandler = -1;

	public void setCurrentHandler(int i) {
		currentHandler = i;
	}

	public int getCurrentHandler() {
		return currentHandler;
	}

	public Object getProperty(String name) {
		if (_properties == null) {
			return null;
		} else {
			return _properties.get(name);
		}
	}

	public void setProperty(String name, Object value) {
		if (_properties == null) {
			_properties = new HashMap();
		}

		_properties.put(name, value);
	}

	public void removeProperty(String name) {
		if (_properties != null) {
			_properties.remove(name);
		}
	}

	public boolean containsProperty(String name) {
		if (_properties == null) {
			return false;
		} else {
			return _properties.containsKey(name);
		}
	}

	public java.util.Iterator getPropertyNames() {
		if (_properties == null) {
			return NullIterator.getInstance();
		} else {
			return _properties.keySet().iterator();
		}
	}

        public SOAPMessage createMessage() {
            return createMessage(false, true);   // XML request - FI accept
        }
        
	public SOAPMessage createMessage(boolean useFastInfoset, 
            boolean acceptFastInfoset) 
        {
            try {
                MessageFactory msgFactory = getMessageFactory();
                if (useFastInfoset || acceptFastInfoset) {
                    // Must be our own message factory for FI!
                    MessageFactoryImpl msgFactoryImpl = (MessageFactoryImpl) msgFactory;
                    return msgFactoryImpl.createMessage(useFastInfoset, acceptFastInfoset);                    
                }
                return msgFactory.createMessage();
            } 
            catch (SOAPException e) {
                    throw new SOAPMsgCreateException(
                            "soap.msg.create.err",
                            new Object[] { e });
            }
	}
        
	public SOAPMessage createMessage(MimeHeaders headers, InputStream in)
		throws IOException 
        {
		try {
                        return getMessageFactory().createMessage(headers, in);
		} catch (SOAPException e) {
			throw new SOAPMsgCreateException(
				"soap.msg.create.err",
				new Object[] { e });
		}
	}

	public void writeInternalServerErrorResponse() {
		try {
			setFailure(true);
			SOAPMessage message = createMessage();
			message.getSOAPPart().setContent(
				new StreamSource(
					XmlUtil.getUTF8ByteInputStream(
						DEFAULT_SERVER_ERROR_ENVELOPE)));
			setMessage(message);
		} catch (SOAPException e) {
			// this method is called as a last resort, so it fails we cannot possibly recover
		}
	}

	public void writeSimpleErrorResponse(QName faultCode, String faultString) {
		try {
			setFailure(true);
			SOAPMessage message = createMessage();
			ByteArrayOutputStream bufferedStream = new ByteArrayOutputStream();
			Writer writer = new OutputStreamWriter(bufferedStream, "UTF-8");
			writer.write(
				"<?xml version='1.0' encoding='UTF-8'?>\n"
					+ "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "<env:Body><env:Fault><faultcode>env:");
			writer.write(faultCode.getLocalPart());
			writer.write("</faultcode>" + "<faultstring>");
			writer.write(faultString);
			writer.write(
				"</faultstring>" + "</env:Fault></env:Body></env:Envelope>");
			writer.close();
			byte[] data = bufferedStream.toByteArray();
			message.getSOAPPart().setContent(
				new StreamSource(new ByteInputStream(data, data.length)));
			setMessage(message);
		} catch (Exception e) {
			writeInternalServerErrorResponse();
		}
	}
    
    private static MessageFactory getMessageFactory() {
        try {
            if (_messageFactory == null) {
                _messageFactory = MessageFactory.newInstance();
            }
        } catch(SOAPException e) {
            throw new SOAPMsgFactoryCreateException(
                "soap.msg.factory.create.err",
                new Object[] { e });
        }        
        return _messageFactory;
    }

    public boolean isFastInfoset() {
        try {
            return ((MessageImpl) _message).isFastInfoset();
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean acceptFastInfoset() {
        try {
            return ((MessageImpl) _message).acceptFastInfoset();
        }
        catch (Exception e) {
            return false;
        }
    }
        
    private SOAPMessage _message;
    private boolean _failure;
    private Map _properties;
    private String[] roles;
    private static MessageFactory _messageFactory;
    private final static String DEFAULT_SERVER_ERROR_ENVELOPE =
            "<?xml version='1.0' encoding='UTF-8'?>"
                    + "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                    + "<env:Body>"
                    + "<env:Fault>"
                    + "<faultcode>env:Server</faultcode>"
                    + "<faultstring>Internal server error</faultstring>"
                    + "</env:Fault>"
                    + "</env:Body>"
                    + "</env:Envelope>";
}

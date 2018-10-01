/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package hello;

public class HelloImpl implements hello.HelloIF, java.rmi.Remote {

    public void sayHelloBackOneWay(java.lang.String str) {
    }

    public String sayHelloBack(java.lang.String str) {
	     String result = " Hi " + str + " From Server for wsdl client";
	     System.out.println("In sayHello for WSDL : " + str);
        return result;
    }
}

#!/bin/sh
#
# Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#

if [ -z "$JAVA_HOME" ]; then
	echo "ERROR: Set JAVA_HOME to the path where the J2SE (JDK) is installed (e.g., /usr/java/jdk1.3)"
	exit 1
fi

if [ -z "$JAXRPC_HOME" ]; then
	echo "ERROR: Set JAXRPC_HOME to the root of a JAXRPC-RI distribution (e.g., the directory above this bin directory)"
	exit 1
fi


CLASSPATH=.:$JAXRPC_HOME/build:$JAXRPC_HOME/src:$JAXRPC_HOME/lib/jaxrpc-api.jar:$JAXRPC_HOME/lib/jaxrpc-spi.jar:$JAXRPC_HOME/lib/saaj-api.jar:$JAXRPC_HOME/lib/saaj-impl.jar:$JAXRPC_HOME/lib/mail.jar:$JAXRPC_HOME/lib/jcert.jar:$JAXRPC_HOME/lib/jnet.jar:$JAXRPC_HOME/lib/jsse.jar:$JAXRPC_HOME/lib/relaxngDatatype.jar:$JAVA_HOME/lib/tools.jar

$JAVA_HOME/bin/java -cp "$CLASSPATH" com.sun.xml.rpc.tools.wsdeploy.Main "$@"



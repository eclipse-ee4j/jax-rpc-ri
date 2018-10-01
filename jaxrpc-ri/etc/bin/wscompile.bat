@echo off


REM
REM  Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
REM
REM  This program and the accompanying materials are made available under the
REM  terms of the Eclipse Public License v. 2.0, which is available at
REM  http://www.eclipse.org/legal/epl-2.0.
REM
REM  This Source Code may also be made available under the following Secondary
REM  Licenses when the conditions for such availability set forth in the
REM  Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
REM  version 2 with the GNU Classpath Exception, which is available at
REM  https://www.gnu.org/software/classpath/license.html.
REM
REM  SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
REM


if defined JAVA_HOME goto CONTA
echo ERROR: Set JAVA_HOME to the path where the J2SE (JDK) is installed (e.g., D:\jdk1.3)
goto END
:CONTA

if defined JAXRPC_HOME goto CONTB
echo ERROR: Set JAXRPC_HOME to the root of a JAXRPC-RI distribution (e.g., D:\ws\jaxrpc-ri\build)
goto END
:CONTB

rem Get command line arguments and save them
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

setlocal

set CLASSPATH=.;%JAXRPC_HOME%\lib\jaxrpc-impl.jar;%JAXRPC_HOME%\lib\jaxrpc-api.jar;%JAXRPC_HOME%\lib\jaxrpc-spi.jar;%JAXRPC_HOME%\lib\saaj-api.jar;%JAXRPC_HOME%\lib\saaj-impl.jar;%JAXRPC_HOME%\lib\mail.jar;%JAXRPC_HOME%\lib\relaxngDatatype.jar;%JAXRPC_HOME%\lib\jcert.jar;%JAXRPC_HOME%\lib\jnet.jar;%JAXRPC_HOME%\lib\jsse.jar;%JAVA_HOME%\lib\tools.jar

%JAVA_HOME%\bin\java -cp "%CLASSPATH%" com.sun.xml.rpc.tools.wscompile.Main %CMD_LINE_ARGS%

endlocal

:END

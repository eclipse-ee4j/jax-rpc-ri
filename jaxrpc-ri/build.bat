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


echo JAX-RPC-RI Builder
echo -------------------

if "%JAVA_HOME%" == "" goto error

set LOCALCLASSPATH=%JAVA_HOME%\lib\tools.jar;.\lib\jsse.jar;.\lib\jnet.jar;.\lib\jcert.jar;.\lib\ant.jar;.\lib\junit.jar;%ADDITIONALCLASSPATH%
set ANT_HOME=./lib

echo Building with classpath %LOCALCLASSPATH%

echo Starting Ant...

%JAVA_HOME%\bin\java.exe -Dant.home="%ANT_HOME%" -classpath "%LOCALCLASSPATH%" org.apache.tools.ant.Main %1 %2 %3 %4 %5

goto end

:error

echo ERROR: JAVA_HOME not found in your environment.
echo Please, set the JAVA_HOME variable in your environment to match the
echo location of the Java Virtual Machine you want to use.

:end

set LOCALCLASSPATH=


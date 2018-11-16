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

package com.sun.xml.rpc.processor.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.model.Fault;
import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
import com.sun.xml.rpc.processor.model.java.JavaStructureType;
import com.sun.xml.rpc.processor.modeler.ModelerConstants;
import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
import com.sun.xml.rpc.processor.util.IndentingWriter;
import com.sun.xml.rpc.soap.SOAPVersion;

/**
 *
 * @author JAX-RPC Development Team
 */
public class CustomExceptionGenerator extends GeneratorBase {
    private Set faults;

    public CustomExceptionGenerator() {
    }

    public GeneratorBase getGenerator(
        Model model,
        Configuration config,
        Properties properties) {
        return new CustomExceptionGenerator(model, config, properties);
    }

    public GeneratorBase getGenerator(
        Model model,
        Configuration config,
        Properties properties,
        SOAPVersion ver) {
        return new CustomExceptionGenerator(model, config, properties);
    }

    private CustomExceptionGenerator(
        Model model,
        Configuration config,
        Properties properties) {
        super(model, config, properties);
    }

    protected void preVisitModel(Model model) throws Exception {
        faults = new HashSet();
    }

    protected void postVisitModel(Model model) throws Exception {
        faults = null;
    }

    protected void preVisitFault(Fault fault) throws Exception {
        if (isRegistered(fault))
            return;
        registerFault(fault);
        if (fault.getParentFault() != null) {
            preVisitFault(fault.getParentFault());
        }
    }

    private boolean isRegistered(Fault fault) {
        return faults.contains(fault.getJavaException().getName());
    }

    private void registerFault(Fault fault) {
        faults.add(fault.getJavaException().getName());
        generateCustomException(fault);
    }

    private void generateCustomException(Fault fault) {
        if (fault.getJavaException().isPresent())
            return;
        log(
            "generating CustomException for: "
                + fault.getJavaException().getName());
        try {
            String className = env.getNames().customExceptionClassName(fault);
            if ((donotOverride && 
                 GeneratorUtil.classExists(env, className))) {
                log("Class " + className + " exists. Not overriding.");
                return;
            }
            File classFile =
                env.getNames().sourceFileForClass(
                    className,
                    className,
                    sourceDir,
                    env);

            /* adding the file name and its type */
            GeneratedFileInfo fi = new GeneratedFileInfo();
            fi.setFile(classFile);
            fi.setType(GeneratorConstants.FILE_TYPE_EXCEPTION);
            env.addGeneratedFile(fi);

            IndentingWriter out =
                new IndentingWriter(
                    new OutputStreamWriter(new FileOutputStream(classFile)));
            writePackage(out, className);
            out.pln();
            JavaStructureType javaStructure =
                (JavaStructureType) fault.getJavaException();
            writeClassDecl(out, className, javaStructure);
            writeMembers(out, fault);
            out.pln();
            writeClassConstructor(out, className, fault);
            out.pln();
            writeGetter(out, fault);
            out.pOln("}"); // class
            out.close();
        } catch (Exception e) {
            fail(e);
        }
    }

    private void writeClassDecl(
        IndentingWriter p,
        String className,
        JavaStructureType javaStruct)
        throws IOException {
        JavaStructureType superclass = javaStruct.getSuperclass();
        if (superclass != null) {
            p.plnI(
                "public class "
                    + Names.stripQualifier(className)
                    + " extends "
                    + superclass.getName()
                    + " {");
        } else {
            p.plnI(
                "public class "
                    + Names.stripQualifier(className)
                    + " extends java.lang.Exception {");
        }
    }

    private void writeMembers(IndentingWriter p, Fault fault)
        throws IOException {
        Iterator members =
            ((JavaStructureType) fault.getJavaException()).getMembers();
        JavaStructureMember member;
        while (members.hasNext()) {
            member = (JavaStructureMember) members.next();
            if (!member.isInherited()) {
                p.pln(
                    "private "
                        + member.getType().getName()
                        + " "
                        + member.getName()
                        + ";");
            }
        }
        p.pln();
    }

    private void writeClassConstructor(
        IndentingWriter p,
        String className,
        Fault fault)
        throws IOException {
        JavaStructureType javaStructure =
            (JavaStructureType) fault.getJavaException();

        p.p("public " + Names.stripQualifier(className) + "(");
        Iterator members = javaStructure.getMembers();
        JavaStructureMember member;
        int stringTypeCount = 0;
        String stringMemberName = null;
        for (int i = 0; members.hasNext(); i++) {
            member = (JavaStructureMember) members.next();
            if (i > 0)
                p.p(", ");
            p.p(member.getType().getName() + " " + member.getName());
            if (member
                .getType()
                .getName()
                .equals(ModelerConstants.STRING_CLASSNAME)) {
                stringTypeCount++;
                stringMemberName = member.getName();
            }
        }
        p.plnI(") {");
        if (fault.getParentFault() != null) {
            members = javaStructure.getMembers();
            int i = 0;
            while (members.hasNext()) {
                member = (JavaStructureMember) members.next();
                if (member.isInherited()) {
                    if (i++ == 0)
                        p.p("super(");
                    else
                        p.p(", ");
                    p.p(member.getName());
                }
            }
            if (i > 0)
                p.pln(");");
        } else if (stringTypeCount == 1) {
            p.pln("super(" + stringMemberName + ");");
        }
        members = fault.getJavaException().getMembers();
        for (int i = 0; members.hasNext(); i++) {
            member = (JavaStructureMember) members.next();
            if (!member.isInherited()) {
                p.pln(
                    "this."
                        + member.getName()
                        + " = "
                        + member.getName()
                        + ";");
            }
        }
        p.pOln("}");
    }

    private void writeGetter(IndentingWriter p, Fault fault)
        throws IOException {
        Iterator members =
            ((JavaStructureType) fault.getJavaException()).getMembers();
        JavaStructureMember member;
        int i = 0;
        while (members.hasNext()) {
            if (i > 0)
                p.pln();
            member = (JavaStructureMember) members.next();
            if (!member.isInherited()) {
                p.plnI(
                    "public "
                        + member.getType().getName()
                        + " "
                        + member.getReadMethod()
                        + "() {");
                p.pln("return " + member.getName() + ";");
                p.pOln("}");
                i++;
            }
        }
    }
}

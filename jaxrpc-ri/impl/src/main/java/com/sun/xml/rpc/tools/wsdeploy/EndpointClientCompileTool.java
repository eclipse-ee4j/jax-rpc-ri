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

package com.sun.xml.rpc.tools.wsdeploy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import com.sun.xml.rpc.processor.Processor;
import com.sun.xml.rpc.processor.ProcessorNotificationListener;
import com.sun.xml.rpc.processor.config.Configuration;
import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
import com.sun.xml.rpc.processor.util.XMLModelWriter;
import com.sun.xml.rpc.tools.wscompile.ActionConstants;
import com.sun.xml.rpc.tools.wscompile.CompileTool;
import com.sun.xml.rpc.util.localization.Localizable;

/**
 *
 * @author JAX-RPC Development Team
 */
public class EndpointClientCompileTool extends CompileTool {
    
    public EndpointClientCompileTool(
        OutputStream out,
        String program,
        WebServicesInfo wsi,
        ArrayList list,
        File dir,
        String target,
        String classpath,
        ProcessorNotificationListener l) {
            
        super(out, program);
        webServicesInfo = wsi;
        targetDirectory = dir;
        additionalClasspath = classpath;
        listener = l;
        clientList = list;
        endpointClient = (EndpointClientInfo) clientList.get(0);
        useModel = endpointClient.getModel() != null;
        this.targetVersion = target;
    }
    
    protected void beforeHook() {
        
        // set things up properly
        String targetPath = targetDirectory.getAbsolutePath();
        
        nonclassDestDir = new File(targetPath + FS + "WEB-INF");
        userClasspath = targetPath + FS + "WEB-INF" + FS + "lib";
        destDir = new File(userClasspath);
        
        // needs to be changed
        userClasspath = targetPath
            + FS
            + "WEB-INF"
            + FS
            + "lib"
            + PS
            + targetPath
            + FS
            + "WEB-INF"
            + FS
            + "classes";
        
        // Add all jar files under WEB-INF/lib to the user classpath
        if (new File(targetPath + FS + "WEB-INF" + FS + "lib").exists()) {
            File[] fs = new File(
                targetPath + FS + "WEB-INF" + FS + "lib").listFiles();
            for (int counter = 0; counter < fs.length; ++counter) {
                userClasspath += PS + fs[counter];
            }
        }
        
        if (additionalClasspath != null && additionalClasspath.length() > 0) {
            userClasspath += PS + additionalClasspath;
        }
        
        if (!useModel) {
            modelFile = new File(makeModelFileName());
        } else if (targetVersion != null) {
            onWarning(getMessage(
                "wscompile.warning.ignoringTargetVersionForModel",
                endpointClient.getModel(),
                targetVersion));
            targetVersion = null;
        }
        serializerInfix = "_" + endpointClient.getName() + "_";
        keepGenerated = true;
        compilerDebug = false;
        compilerOptimize = true;
        super.beforeHook();
    }
    
    protected void withModelHook() {
    }
    
    public com.sun.xml.rpc.spi.tools.Configuration createConfiguration()
        throws Exception {
            
        // create our own configuration
        Configuration config = new Configuration(environment);
        if (useModel) {
            ModelFileModelInfo modelInfo = new ModelFileModelInfo();
            modelInfo.setLocation(makeAbsolute(endpointClient.getModel()));
            config.setModelInfo(modelInfo);
        }
        return config;
    }
    
    protected String makeTargetNamespaceURI() {
        String base = webServicesInfo.getTargetNamespaceBase();
        if (base.endsWith("/") || base.startsWith("urn:")) {
            return base + endpointClient.getName();
        } else {
            return base + "/" + endpointClient.getName();
        }
    }
    
    protected String makeTypeNamespaceURI() {
        String base = webServicesInfo.getTypeNamespaceBase();
        if (base.endsWith("/") || base.startsWith("urn:")) {
            return base + endpointClient.getName();
        } else {
            return base + "/" + endpointClient.getName();
        }
    }
    
    protected String makeModelFileName() {
        return targetDirectory.getAbsolutePath()
            + FS
            + "WEB-INF"
            + FS
            + endpointClient.getName()
            + "_model.xml.gz";
    }
    
    protected String makeJavaPackageName() {
        return "jaxrpc.generated." +
            environment.getNames().validJavaPackageName(
                endpointClient.getName());
    }
    
    protected String makeAbsolute(String s) {
        if (s == null) {
            return null;
        }
        return new File(targetDirectory.getAbsolutePath() + s)
            .getAbsolutePath();
    }
    
    protected String makeAppRelative(File f) {
        if (f == null) {
            return null;
        }
        String s = f.getAbsolutePath();
        String target = targetDirectory.getAbsolutePath();
        if (s.startsWith(target)) {
            return s.substring(target.length()).replace(FSCHAR, '/');
        } else {
            
            // TODO - isn't this an error?
            return null;
        }
    }
    
    protected File findGeneratedFileEndingWith(String s) {
        for (Iterator iter = environment.getGeneratedFiles();
            iter.hasNext();) {
                
            GeneratedFileInfo fileInfo = (GeneratedFileInfo) iter.next();
            File file = fileInfo.getFile();
            if (file.getAbsolutePath().endsWith(s)) {
                return file;
            }
        }
        return null;
    }
    
    protected void registerProcessorActions(Processor processor) {
        
        // completely override the actions in the base class
        if (!useModel) {
            try {
                processor.add(new XMLModelWriter(modelFile));
            } catch (FileNotFoundException e) {
                
                // should not happen
            }
        }
        
        processor.add(getAction(
            ActionConstants.ACTION_SERVICE_INTERFACE_GENERATOR));
        processor.add(getAction(ActionConstants.ACTION_SERVICE_GENERATOR));
        processor.add(getAction(ActionConstants.ACTION_CUSTOM_CLASS_GENERATOR));
        processor.add(getAction(
            ActionConstants.ACTION_ENUMERATION_ENCODER_GENERATOR));
        processor.add(getAction(
            ActionConstants.ACTION_LITERAL_OBJECT_SERIALIZER_GENERATOR));
        processor.add(getAction(
            ActionConstants.ACTION_SOAP_FAULT_SERIALIZER_GENERATOR));
        processor.add(getAction(
            ActionConstants.ACTION_FAULT_EXCEPTION_BUILDER_GENERATOR));
        if (delegate != null) {
            delegate.postRegisterProcessorActions();
        }
    }
    
    /* Methods for localization of Messages */
    public void onError(Localizable msg) {
        if (delegate != null) {
            delegate.preOnError();
        }
        report(getMessage("wscompile.error", localizer.localize(msg)));
    }
    public void onWarning(Localizable msg) {
        report(getMessage("wscompile.warning", localizer.localize(msg)));
    }
    public void onInfo(Localizable msg) {
        report(getMessage("wscompile.info", localizer.localize(msg)));
    }
    
    protected WebServicesInfo webServicesInfo;
    protected File targetDirectory;
    protected boolean useModel;
    protected String additionalClasspath;
    protected Hashtable hashtable;
    protected ArrayList vector;
    protected EndpointClientInfo endpointClient;
    protected ArrayList clientList;
    protected boolean localUseWSIBasicProfile = false;
    
    private final static String PS = System.getProperty("path.separator");
    private final static char PSCHAR =
        System.getProperty("path.separator").charAt(0);
    private final static String FS = System.getProperty("file.separator");
    private final static char FSCHAR =
        System.getProperty("file.separator").charAt(0);
}

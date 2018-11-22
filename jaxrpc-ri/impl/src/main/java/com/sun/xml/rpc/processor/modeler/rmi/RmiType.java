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

package com.sun.xml.rpc.processor.modeler.rmi;

import java.util.Hashtable;

public class RmiType implements RmiConstants {
    
    /**
     * This hashtable is used to cache types
     */
    private static final Hashtable typeHash = new Hashtable(231);

    private String typeSig;
    private int typeCode = 0;
    /*
     * Commenting it out the caching of classobj objects in getTypeClass()
     * so that Class objs get GCed and env's URLClassLoader gets GCed. That
     * releases any opened jar files and that is a problem on windows while
     * redployment.
     *
     * private Class classObj = null;
     */
    
    /*
     * Predefined types.
     */
    public static final RmiType tVoid       = new RmiType(TC_VOID, 	SIG_VOID);
    public static final RmiType tBoolean    = new RmiType(TC_BOOLEAN, 	SIG_BOOLEAN);
    public static final RmiType tByte       = new RmiType(TC_BYTE, 	SIG_BYTE);
    public static final RmiType tChar       = new RmiType(TC_CHAR, 	SIG_CHAR);
    public static final RmiType tShort      = new RmiType(TC_SHORT, 	SIG_SHORT);
    public static final RmiType tInt        = new RmiType(TC_INT, 	SIG_INT);
    public static final RmiType tFloat      = new RmiType(TC_FLOAT, 	SIG_FLOAT);
    public static final RmiType tLong       = new RmiType(TC_LONG, 	SIG_LONG);
    public static final RmiType tDouble     = new RmiType(TC_DOUBLE, 	SIG_DOUBLE);
    public static final RmiType tObject     = RmiType.classType(OBJECT_CLASSNAME);
    public static final RmiType tClassDesc  = RmiType.classType(CLASS_CLASSNAME);
    public static final RmiType tString     = RmiType.classType(STRING_CLASSNAME);

    protected RmiType() {
    }
    
    protected RmiType(int typeCode, String typeSig) {
        this.typeCode = typeCode;
        this.typeSig = typeSig;
        typeHash.put(typeSig, this);
    }

    /**
     * Return the Java type signature.
     */
    public final String getTypeSignature() {
        return typeSig;
    }

    
    public int getTypeCode() {
        return typeCode;
    }
    
    public RmiType getElementType() {
        throw new UnsupportedOperationException();
    }
    
    public String getClassName() {
        throw new UnsupportedOperationException();
    }
    
    public int getArrayDimension() {
        return 0;
    }
    
    public Class getTypeClass(ClassLoader loader) throws ClassNotFoundException {
	    if (typeSig.length() == 1) {
		    return RmiUtils.getClassForName(typeString(false), loader);
	    } else {
		    String sig = getTypeSigClassName(typeSig);
		    return Class.forName(sig, true, loader);
	    }
    }
    
    private static String getTypeSigClassName(String typeSig) {
        String sig = typeSig;
        if (sig.charAt(0) == SIGC_CLASS) {
            sig = sig.substring(1, sig.length()-1).replace(SIGC_PACKAGE, '.');
        } 
        return sig;
    }
    
    public static RmiType classType(String className) {
        String sig =
            new String(SIG_CLASS + className + SIG_ENDCLASS);
        RmiType t = (RmiType)typeHash.get(sig);
        if (t == null) {
            t = new ClassType(sig, className);
        }
        
        return t;
    }
    
    public static RmiType arrayType(RmiType elem) {
        String sig = new String(SIG_ARRAY + elem.getTypeSignature());
        RmiType t = (RmiType)typeHash.get(sig);
        if (t == null) {
            t = new ArrayType(sig, elem);
        }
        return t;
    }
    
    
    public static RmiType getRmiType(Class classObj) {
        String sig;
        if (classObj.isArray()) {
            sig = classObj.getName();
        } else {
            sig = RmiUtils.getTypeSig(classObj.getName());            
        }
        return getRmiType(sig);
    }
    
    public static RmiType getRmiType(String sig) {
        RmiType type = (RmiType)typeHash.get(sig);
        if (type != null) {
            return type;
        }
        switch (sig.charAt(0)) {
          case SIGC_ARRAY:
            return arrayType(getRmiType(sig.substring(1)));
        
          case SIGC_CLASS:
            return classType(sig.substring(1, sig.length() - 1).replace(SIGC_PACKAGE, '.'));
        }        
        return type;
    }
    
    public String typeString(boolean abbrev) {
        switch (typeCode) {
            case TC_VOID:   return VOID_CLASSNAME;    
            case TC_BOOLEAN:return BOOLEAN_CLASSNAME; 
            case TC_BYTE:   return BYTE_CLASSNAME;		
            case TC_CHAR:   return CHAR_CLASSNAME;	
            case TC_SHORT:  return SHORT_CLASSNAME;
            case TC_INT:    return INT_CLASSNAME;
            case TC_LONG:   return LONG_CLASSNAME;
            case TC_FLOAT:  return FLOAT_CLASSNAME;
            case TC_DOUBLE: return DOUBLE_CLASSNAME;
            default:        return "unknown";
        }
    }
    
    public boolean isNillable() {
        return false;
    }

    public String toString() {
        return typeString(false);
    }    
}



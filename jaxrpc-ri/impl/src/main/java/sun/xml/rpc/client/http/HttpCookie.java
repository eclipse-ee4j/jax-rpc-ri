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

package com.sun.xml.rpc.client.http;

import java.net.URL;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * An object which represents an HTTP cookie.  Can be constructed by
 * parsing a string from the set-cookie: header.
 *
 * Syntax: Set-Cookie: NAME=VALUE; expires=DATE;
 *             path=PATH; domain=DOMAIN_NAME; secure
 *
 * All but the first field are optional.
 *
 * @author JAX-RPC Development Team
 */
public class HttpCookie implements java.io.Serializable {

    private Date expirationDate = null;
    private String nameAndValue;
    private String path;
    private String domain;
    private boolean isSecure = false;

    public HttpCookie(String cookieString) {
        parseCookieString(cookieString);
    }

    //
    // Constructor for use by the bean
    //
    public HttpCookie(
        Date expirationDate,
        String nameAndValue,
        String path,
        String domain,
        boolean isSecure) {

        this.expirationDate = expirationDate;
        this.nameAndValue = nameAndValue;
        this.path = path;
        this.domain = stripPort(domain);
        this.isSecure = isSecure;
    }

    public HttpCookie(URL url, String cookieString) {
        parseCookieString(cookieString);
        applyDefaults(url);
    }

    /**
     * Fills in default values for domain, path, etc. from the URL
     * after creation of the cookie.
     */
    private void applyDefaults(URL url) {

        if (domain == null) {
            domain = url.getHost();

            // REMIND: record the port
        }

        if (path == null) {
            path = url.getFile();

            // The documentation for cookies say that the path is
            // by default, the path of the document, not the filename of the
            // document.  This could be read as not including that document
            // name itself, just its path (this is how NetScape inteprets it)
            // so amputate the document name!
            int last = path.lastIndexOf("/");

            if (last > -1) {
                path = path.substring(0, last);
            }
        }
    }

    private String stripPort(String domainName) {

        int index = domainName.indexOf(':');

        if (index == -1) {
            return domainName;
        }

        return domainName.substring(0, index);
    }

    /**
     * Parse the given string into its individual components, recording them
     * in the member variables of this object.
     */
    private void parseCookieString(String cookieString) {

        StringTokenizer tokens = new StringTokenizer(cookieString, ";");

        if (!tokens.hasMoreTokens()) {

            // REMIND: make this robust against parse errors
        }

        nameAndValue = tokens.nextToken().trim();

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();

            if (token.equalsIgnoreCase("secure")) {
                isSecure = true;
            } else {
                int equIndex = token.indexOf("=");

                if (equIndex < 0) {
                    continue;

                    // REMIND: malformed cookie
                }

                String attr = token.substring(0, equIndex);
                String val = token.substring(equIndex + 1);

                if (attr.equalsIgnoreCase("path")) {
                    path = val;
                } else if (attr.equalsIgnoreCase("domain")) {
                    if (val.indexOf(".") == 0) {

                        // spec seems to allow for setting the domain in
                        // the form 'domain=.eng.sun.com'.  We want to
                        // trim off the leading '.' so we can allow for
                        // both leading dot and non leading dot forms
                        // without duplicate storage.
                        domain = stripPort(val.substring(1));
                    } else {
                        domain = stripPort(val);
                    }
                } else if (attr.equalsIgnoreCase("expires")) {
                    expirationDate = parseExpireDate(val);
                } else {

                    // unknown attribute -- do nothing
                }
            }
        }
    }

    //======================================================================
    //
    // Accessor functions
    //
    public String getNameValue() {
        return nameAndValue;
    }

    /**
     * Returns just the name part of the cookie
     */
    public String getName() {

        int index = nameAndValue.indexOf("=");

        return nameAndValue.substring(0, index);
    }

    /**
     * Returns the domain of the cookie as it was presented
     */
    public String getDomain() {

        // REMIND: add port here if appropriate
        return domain;
    }

    public String getPath() {
        return path;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    boolean hasExpired() {

        if (expirationDate == null) {
            return false;
        }

        return (expirationDate.getTime() <= System.currentTimeMillis());
    }

    /**
     * Returns true if the cookie has an expiration date (meaning it's
     * persistent), and if the date nas not expired;
     */
    boolean isSaveable() {
        return (expirationDate != null)
            && (expirationDate.getTime() > System.currentTimeMillis());
    }

    public boolean isSecure() {
        return isSecure;
    }

    private Date parseExpireDate(String dateString) {

        // format is wdy, DD-Mon-yyyy HH:mm:ss GMT
        RfcDateParser parser = new RfcDateParser(dateString);
        Date theDate = parser.getDate();

        return theDate;
    }

    public String toString() {

        String result = nameAndValue;

        if (expirationDate != null) {
            result += "; expires=" + expirationDate;
        }

        if (path != null) {
            result += "; path=" + path;
        }

        if (domain != null) {
            result += "; domain=" + domain;
        }

        if (isSecure) {
            result += "; secure";
        }

        return result;
    }
}

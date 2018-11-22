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

package com.sun.xml.rpc.streaming;

/**
 * An event contains all the current state of a StreamingParser.
 *
 * @author JAX-RPC Development Team
 */

public final class Event {
    public int state = StreamingParser.AT_END;
    public String name;
    public String value;
    public String uri;
    public int line;

    public Event() {
    }

    public Event(int s, String n, String v, String u) {
        this(s, n, v, u, -1);
    }

    public Event(int s, String n, String v, String u, int i) {
        state = s;
        name = n;
        value = v;
        uri = u;
        line = i;
    }

    public Event(Event e) {
        from(e);
    }

    public void from(Event e) {
        this.state = e.state;
        this.name = e.name;
        this.value = e.value;
        this.uri = e.uri;
        this.line = e.line;
    }

    public String toString() {
        // intended for debug only
        return "Event("
            + getStateName()
            + ", "
            + name
            + ", "
            + value
            + ", "
            + uri
            + ", "
            + line
            + ")";
    }

    protected String getStateName() {
        switch (state) {
            case StreamingParser.START :
                return "start";
            case StreamingParser.END :
                return "end";
            case StreamingParser.ATTR :
                return "attr";
            case StreamingParser.CHARS :
                return "chars";
            case StreamingParser.IWS :
                return "iws";
            case StreamingParser.PI :
                return "pi";
            case StreamingParser.AT_END :
                return "at_end";
            default :
                return "unknown";
        }
    }
}

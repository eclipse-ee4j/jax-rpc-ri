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

package com.sun.xml.rpc.encoding;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;

import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.util.IntegerArrayList;
import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

/**
 * Tracks the state of an object whose deserialization depends on
 * other, yet to be completed, deserializations.
 *
 * @author JAX-RPC Development Team
 */
public class SOAPDeserializationState {
    private static final boolean writeDebug = false;

    private static final int CREATION_GATES_CONSTRUCTION =
        SOAPInstanceBuilder.GATES_CONSTRUCTION
            + SOAPInstanceBuilder.REQUIRES_CREATION;
    private static final int INITIALIZATION_GATES_CONSTRUCTION =
        SOAPInstanceBuilder.GATES_CONSTRUCTION
            + SOAPInstanceBuilder.REQUIRES_COMPLETION;
    private static final int COMPLETION_GATES_CONSTRUCTION =
        SOAPInstanceBuilder.GATES_CONSTRUCTION
            + SOAPInstanceBuilder.REQUIRES_INITIALIZATION;
    private static final int CREATION_GATES_INITIALIZATION =
        SOAPInstanceBuilder.GATES_INITIALIZATION
            | SOAPInstanceBuilder.REQUIRES_CREATION;
    private static final int INITIALIZATION_GATES_INITIALIZATION =
        SOAPInstanceBuilder.GATES_INITIALIZATION
            | SOAPInstanceBuilder.REQUIRES_INITIALIZATION;
    private static final int COMPLETION_GATES_INITIALIZATION =
        SOAPInstanceBuilder.GATES_INITIALIZATION
            | SOAPInstanceBuilder.REQUIRES_COMPLETION;

    private static final int NO_STATE = 0;
    private static final int CREATED_STATE =
        SOAPInstanceBuilder.REQUIRES_CREATION;
    private static final int INITIALIZED_STATE =
        SOAPInstanceBuilder.REQUIRES_INITIALIZATION;
    private static final int COMPLETE_STATE =
        SOAPInstanceBuilder.REQUIRES_COMPLETION;

    private static final int CREATION_EVENT = CREATED_STATE;
    private static final int INITIALIZATION_EVENT = INITIALIZED_STATE;
    private static final int COMPLETION_EVENT = COMPLETE_STATE;
    private static final int EVENT_BIT_MASK =
        CREATION_EVENT | INITIALIZATION_EVENT | COMPLETION_EVENT;

    private static final int CREATION_GATE =
        SOAPInstanceBuilder.GATES_CONSTRUCTION;
    private static final int INITIALIZATION_GATE =
        SOAPInstanceBuilder.GATES_INITIALIZATION;
    private static final int GATE_BIT_MASK =
        CREATION_GATE | INITIALIZATION_GATE;

    private JAXRPCDeserializer deserializer = null;
    private SOAPInstanceBuilder builder = null;
    private List listeners = new ArrayList();
    private IntegerArrayList listenerMembers = new IntegerArrayList();
    private int constructionGates = 0;
    private int initializationGates = 0;
    private int completionGates = 0;
    private boolean hasBeenRead = false;
    private int state = NO_STATE;
    private Object instance = null;
    private XMLReader recordedElement = null;
    private QName recordedElementExpectedName = null;
    private SOAPDeserializationContext recordedElementDeserialzationContext;

    public boolean isCompleteForKnownMembers() {
        return completionGates == 0;
    }

    public boolean isComplete() {
        return state == COMPLETE_STATE;
    }

    public void promoteToCompleteOrFail() {
        switch (state) {
            case NO_STATE :
                // nobody cares about this object
                return;
            case COMPLETE_STATE :
                // nothing to do here
                return;
            case INITIALIZED_STATE :
                if (writeDebug) {
                    System.out.println(
                        this.stringRep() + " promoted to complete state");
                }
                state = COMPLETE_STATE;
                return;
            default :
                throw new DeserializationException("soap.incompleteObject");
        }
    }

    public SOAPDeserializationState() {
    }

    public void registerListener(
        SOAPDeserializationState parentState,
        int memberIndex) {
        if (deserializer == null) {
            throw new DeserializationException("soap.state.wont.notify.without.deserializer" /*, new Object[] {parentState.stringRep(), new Integer(memberIndex)}*/
            );
        }
        if (writeDebug) {
            System.out.println(
                ""
                    + parentState.stringRep()
                    + ","
                    + memberIndex
                    + " waiting on: "
                    + this.stringRep());
        }

        listeners.add(parentState);
        listenerMembers.add(memberIndex);

        parentState.waitFor(memberIndex);

        sendPastEventsTo(parentState, memberIndex);
    }

    public void sendPastEventsTo(
        SOAPDeserializationState listener,
        int memberIndex) {
            
        int pastState = NO_STATE;

        while (pastState != state) {
            switch (pastState) {
                case NO_STATE :
                    if (writeDebug) {
                        System.out.println(
                            this.stringRep()
                                + " notifying: "
                                + listener.stringRep()
                                + ","
                                + memberIndex
                                + " of past creation");
                    }
                    listener.setMember(memberIndex, getInstance());
                    pastState = CREATED_STATE;
                    break;
                case CREATED_STATE :
                    if (writeDebug) {
                        System.out.println(
                            this.stringRep()
                                + " notifying: "
                                + listener.stringRep()
                                + ","
                                + memberIndex
                                + " of past initialization");
                    }
                    pastState = INITIALIZED_STATE;
                    break;
                case INITIALIZED_STATE :
                    if (writeDebug) {
                        System.out.println(
                            this.stringRep()
                                + " notifying: "
                                + listener.stringRep()
                                + ","
                                + memberIndex
                                + " of past completion");
                    }
                    pastState = COMPLETE_STATE;
                    break;
            }
            listener.beNotified(memberIndex, pastState);
        }
    }

    private void waitFor(int memberIndex) {
        switch (memberGateType(memberIndex)) {
            case CREATION_GATES_CONSTRUCTION :
            case INITIALIZATION_GATES_CONSTRUCTION :
            case COMPLETION_GATES_CONSTRUCTION :
                constructionGates++;
                break;
            case CREATION_GATES_INITIALIZATION :
            case INITIALIZATION_GATES_INITIALIZATION :
            case COMPLETION_GATES_INITIALIZATION :
                initializationGates++;
                break;
        }

        // Anything that we have to wait for gates completion
        completionGates++;

        if (writeDebug) {
            System.out.println(
                this.stringRep()
                    + " now has "
                    + constructionGates
                    + " construction gates, "
                    + initializationGates
                    + " initialization gates, and "
                    + completionGates
                    + " completion gates");
        }
    }

    public void beNotified(int memberIndex, int event) {
        int gateType = memberGateType(memberIndex);
        int watchedEvent = gateType & EVENT_BIT_MASK;

        if (event == watchedEvent) {
            int gatedState = gateType & GATE_BIT_MASK;

            if (writeDebug) {
                System.out.println("event: " + event + " at: " + memberIndex);
            }

            switch (gatedState) {
                case CREATION_GATE :
                    --constructionGates;
                    if (writeDebug) {
                        System.out.println(
                            this.stringRep()
                                + " has: "
                                + constructionGates
                                + " construction gates left");
                    }
                    break;
                case INITIALIZATION_GATE :
                    --initializationGates;
                    if (writeDebug) {
                        System.out.println(
                            this.stringRep()
                                + " has: "
                                + initializationGates
                                + " initialization gates left");
                    }
                    break;
            }
        }

        if (event == COMPLETION_EVENT) {
            --completionGates;
            if (writeDebug) {
                System.out.println(
                    this.stringRep()
                        + " has: "
                        + completionGates
                        + " completion gates left");
            }
        }

        updateState();
    }

    private void updateState() {
        switch (state) {
            case NO_STATE :
                if (constructionGates > 0) {
                    return;
                }
                if (instance == null && builder != null) {
                    builder.construct();
                }
                if (writeDebug) {
                    System.out.println(this.stringRep() + " has been created");
                }
                changeStateTo(CREATED_STATE);
            case CREATED_STATE :
                if (initializationGates > 0 || !hasBeenRead) {
                    return;
                }
                if (builder != null) {
                    builder.initialize();
                }
                if (writeDebug) {
                    System.out.println(
                        this.stringRep() + " has been initialized");
                }
                changeStateTo(INITIALIZED_STATE);
            case INITIALIZED_STATE :
                if (completionGates > 0) {
                    return;
                }
                if (writeDebug) {
                    System.out.println(
                        this.stringRep() + " has been completed");
                }
                changeStateTo(COMPLETE_STATE);
        }
    }

    private void changeStateTo(int newState) {
        state = newState;
        notifyListeners();
    }

    private void notifyListeners() {
        for (int i = 0; i < listeners.size(); ++i) {
            SOAPDeserializationState eachListener =
                (SOAPDeserializationState) listeners.get(i);
            int listenerMember = listenerMembers.get(i);

            if (writeDebug) {
                System.out.println("\tnotifying: " + eachListener.stringRep());
            }
            if (state == CREATED_STATE) {
                eachListener.setMember(listenerMember, getInstance());
            }

            eachListener.beNotified(listenerMember, state);
        }
    }

    public int memberGateType(int memberIndex) {
        if (builder == null) {
            throw new IllegalStateException();
        }

        return builder.memberGateType(memberIndex);
    }

    public void setInstance(Object instance) {
        this.instance = instance;

        if (builder != null) {
            builder.setInstance(instance);
        }
    }

    protected void setMember(int memberIndex, Object value) {
        if (builder == null) {
            throw new IllegalStateException();
        }

        builder.setMember(memberIndex, value);
    }

    public void setBuilder(SOAPInstanceBuilder newBuilder) {
        if (newBuilder == null) {
            throw new IllegalArgumentException();
        }

        if (builder != null && builder != newBuilder) {
            throw new IllegalStateException();
        }

        builder = newBuilder;
        builder.setInstance(instance);
    }

    public SOAPInstanceBuilder getBuilder() {
        return builder;
    }

    public void setDeserializer(JAXRPCDeserializer deserializer) {
        try {
            if (deserializer == null) {
                return;
                // Maybe should throw an exception. Not exiting at this point
                // could cause infinite recursion.
            }
            if (this.deserializer != null) {
                return;
            }

            this.deserializer = deserializer;

            if (recordedElement != null) {
                deserialize(
                    recordedElementExpectedName,
                    recordedElement,
                    recordedElementDeserialzationContext);
            }
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    public void doneReading() {
        if (writeDebug) {
            System.out.println(this.stringRep() + " has been read");
        }
        hasBeenRead = true;
        updateState();
    }

    public Object getInstance() {
        if (builder == null) {
            return instance;
        }

        return builder.getInstance();
    }

    public void deserialize(
        QName name,
        XMLReader reader,
        SOAPDeserializationContext context) {
            
        try {
            if (deserializer == null) {
                recordedElementExpectedName = name;
                recordedElement = reader.recordElement();
                recordedElementDeserialzationContext = context;
                return;
            }

            deserializer.deserialize(name, reader, context);
        } catch (DeserializationException e) {
            throw e;
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    public void deserialize(
        DataHandler dataHandler,
        SOAPDeserializationContext context)
        throws DeserializationException {
            
        try {
            if (deserializer == null) {
                throw new DeserializationException("deserializationstate.deserialize.no.deserializer");
            }

            deserializer.deserialize(dataHandler, context);
        } catch (JAXRPCExceptionBase e) {
            throw new DeserializationException(e);
        } catch (Exception e) {
            throw new DeserializationException(
                new LocalizableExceptionAdapter(e));
        }
    }

    private String stringRep() {
        StringBuffer rep = new StringBuffer("" + hashCode() + ":");
        if (getInstance() != null) {
            String instanceClassName = getInstance().getClass().getName();
            int lastDotLoc = instanceClassName.lastIndexOf('.');
            rep.append(instanceClassName.substring(lastDotLoc));
        }
        rep.append(":");
        if (deserializer != null) {
            String deserializerClassName = deserializer.getClass().getName();
            int lastDotLoc = deserializerClassName.lastIndexOf('.');
            rep.append(deserializerClassName.substring(lastDotLoc));
        }
        return rep.toString();
    }
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more 
 * contributor license agreements.  See the NOTICE file distributed with this 
 * work for additional information regarding copyright ownership. The ASF 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 * 
 *   https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the 
 * License for the specific language governing permissions and limitations
 * under the License.  
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.Callback;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 *
 * @author cmachado
 */
public class PubSub {
    private static final String SENDER_NAME = "jfrdp";
    private static final Memory SENDER;
    private final Pointer handle;
    
    static {
        SENDER = new Memory(SENDER_NAME.length() + 1);
        SENDER.setString(0, SENDER_NAME);
    }

    PubSub(Pointer handle) {
        this.handle = handle;
    }

    private <T extends Structure> void eventArgsInit(Class<T> c, WEventArgs e) {
        e.size = Native.getNativeSize(c);
        e.sender = SENDER;
    }

    int subscribe(String eventName, Callback handler) {
        return WinPRLibrary.PubSub_Subscribe(handle, eventName, handler);
    }

    int unsubscribe(String eventName, Callback handler) {
        return WinPRLibrary.PubSub_Unsubscribe(handle, eventName, handler);
    }
    
    int onEvent(String eventName, Pointer context, Structure e) {
        e.write();
        return WinPRLibrary.PubSub_OnEvent(handle, eventName, context, e.getPointer());
    }

    public int subscribe(WinPRLibrary.ChannelConnectedEventHandler handler) {
        return subscribe("ChannelConnected", handler);
    }

    public int unsubscribe(WinPRLibrary.ChannelConnectedEventHandler handler) {
        return unsubscribe("ChannelConnected", handler);
    }

    public int onChannelConnected(RdpContext context, ChannelConnectedEventArgs args) {
        eventArgsInit(ChannelConnectedEventArgs.ByValue.class, args.e);
        return onEvent("ChannedConnected", context.getPointer(), args);
    }

    public int subscribe(WinPRLibrary.ChannelDisconnectedEventHandler handler) {
        return subscribe("ChannelDisconnected", handler);
    }

    public int unsubscribe(WinPRLibrary.ChannelDisconnectedEventHandler handler) {
        return unsubscribe("ChannelDisconnected", handler);
    }

    public int onChannelDisconnected(RdpContext context, ChannelDisconnectedEventArgs args) {
        eventArgsInit(ChannelDisconnectedEventArgs.ByValue.class, args.e);
        return onEvent("ChannelDisconnected", context.getPointer(), args);
    }

    public int subscribe(WinPRLibrary.ResizeWindowChangeEventHandler handler) {
        return subscribe("ResizeWindow", handler);
    }

    public int unsubscribe(WinPRLibrary.ResizeWindowChangeEventHandler handler) {
        return unsubscribe("ResizeWindow", handler);
    }

    public int onResizeWindow(RdpContext context, ResizeWindowChangeEventArgs args) {
        eventArgsInit(ResizeWindowChangeEventArgs.ByValue.class, args.e);
        return onEvent("ResizeWindow", context.getPointer(), args);
    }
}

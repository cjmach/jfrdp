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

import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.SizeT;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 * Defines the context for a given instance of RDP connection. It is embedded in
 * the freerdp structure.
 * 
 * @author cmachado
 */
@FieldOrder({"instance", "peer", "ServerMode", "LastError", "paddingA", "argc",
    "argv", "pubSub", "channelErrorEvent", "channelErrorNum", "errorDescription",
    "paddingB", "rdp", "gdi", "rail", "cache", "channels", "graphics", "input",
    "update", "settings", "metrics", "codecs", "autodetect", "paddingC1",
    "disconnectUltimatum", "paddingC", "dump", "log", "paddingD", "paddingE"})
public class RdpContext extends Structure {
    /**
     * Pointer to a freerdp structure. This is a back-link to retrieve the 
     * freerdp instance from the context.
     */
    public Pointer instance;
    
    /**
     * Pointer to the client peer. This field is used only on the server side.
     */
    public Pointer peer;
    
    /**
     * {@code true} when context is in server mode.
     */
    public boolean ServerMode;
    
    public UInt LastError;
    
    public long[] paddingA = new long[16 - 4];
    
    /**
     * Number of arguments given to the program at launch time.
     */
    public int argc;
    
    /**
     * List of arguments given to the program at launch time.
     */
    public Pointer argv;
    
    public Pointer pubSub;
    
    public Pointer channelErrorEvent;
    public UInt channelErrorNum;
    public Pointer errorDescription;
    
    public long[] paddingB = new long[32 - 22];

    /**
     * Pointer to a RdpRdp structure used to keep the connection's parameters.
     * There is no need to specifically allocate/deallocate this.
     */
    public Pointer rdp;
    
    /**
     * Pointer to a {@link RdpGdi} structure used to keep the gdi settings. It 
     * is allocated by {@link FreeRdp#gdiInit(pt.cjmach.jfrdp.lib.PixelFormat)} 
     * and deallocated by {@link FreeRdp#gdiFree()}. It must be deallocated before 
     * deallocating this {@code RdpContext} structure.
     */
    public Pointer gdi;
    public Pointer rail;
    public Pointer cache;
    public Pointer channels;
    public Pointer graphics;
    public Pointer input;
    public Pointer update;
    public Pointer settings;
    public Pointer metrics;
    public Pointer codecs;
    public Pointer autodetect;

    public long[] paddingC1 = new long[45 - 44];

    public int disconnectUltimatum;

    public long[] paddingC = new long[64 - 46];

    public Pointer dump;
    public Pointer log;

    public long[] paddingD = new long[96 - 66];
    
    public long[] paddingE = new long[128 - 96];

    private PubSub pubSubObj;
    private RdpGdi gdiObj;
    private RdpInput inputObj;
    private RdpSettings settingsObj;
    private RdpUpdate updateObj;

    RdpContext() {

    }

    public RdpContext(Pointer context) {
        super(context);
        read();

        pubSubObj = new PubSub(pubSub);
        inputObj = new RdpInput(input);
        settingsObj = new RdpSettings(settings);
        updateObj = new RdpUpdate(update);
    }

    public boolean abortConnect() {
        return freerdp_abort_connect_context(getPointer());
    }

    public boolean checkEventHandles() {
        return freerdp_check_event_handles(getPointer());
    }

    public UInt getEventHandles(Pointer events, int length) {
        return freerdp_get_event_handles(getPointer(), events, new UInt(length));
    }

    public UInt getEventHandles(long[] events, int length) {
        return freerdp_get_event_handles(getPointer(), events, new UInt(length));
    }

    public RdpGdi getGdi() {
        Pointer gdiPointer = (Pointer) readField("gdi");
        if (gdiPointer == Pointer.NULL) {
            return null;
        }
        if (gdiObj == null || !gdiObj.getPointer().equals(gdiPointer)) {
            gdiObj = new RdpGdi(gdiPointer);
        }
        return gdiObj;
    }
    
    public RdpInput getInput() {
        return inputObj;
    }

    @Override
    protected int getNativeAlignment(Class<?> type, Object value, boolean isFirstElement) {
        return Long.BYTES;
    }

    public PubSub getPubSub() {
        return pubSubObj;
    }

    public RdpSettings getSettings() {
        return settingsObj;
    }
    
    public RdpUpdate getUpdate() {
        return updateObj;
    }

    public void onChannelConnected(ChannelConnectedEventArgs e) {
        FreeRdpClientLibrary.freerdp_client_OnChannelConnectedEventHandler(getPointer(), e);
    }

    public void onChannelDisconnected(ChannelDisconnectedEventArgs e) {
        FreeRdpClientLibrary.freerdp_client_OnChannelDisconnectedEventHandler(getPointer(), e);
    }
    
    public void registerPointer(RdpPointer pointer) {
        pointer.size = new SizeT(Native.getNativeSize(RdpPointer.ByValue.class));
        pointer.write();
        graphics_register_pointer(graphics, pointer.getPointer());
    }

    public boolean shallDisconnect() {
        return freerdp_shall_disconnect_context(getPointer());
    }

    public static class ByValue extends RdpContext implements Structure.ByValue {
    }

    public static class ByReference extends RdpContext implements Structure.ByReference {
    }
}

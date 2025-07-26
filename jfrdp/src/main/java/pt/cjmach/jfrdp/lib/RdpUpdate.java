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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author cmachado
 */
@FieldOrder({"context", "paddingA", "BeginPaint", "EndPaint", "SetBounds", 
    "Synchronize", "DesktopResize", "BitmapUpdate", "Palette", "PlaySound", 
    "SetKeyboardIndicators", "SetKeyboardImeStatus", "paddingB", "pointer", 
    "primary", "secondary", "altsec", "window", "paddingC", "RefreshRect",
    "SuppressOutput", "RemoteMonitors", "paddingD", "SurfaceCommand", 
    "SurfaceBits", "SurfaceFrameMarker", "SurfaceFrameBits", 
    "SurfaceFrameAcknowledge", "SaveSessionInfo", "ServerStatusInfo", 
    "autoCalculateBitmapData", "paddingE"})
public class RdpUpdate extends Structure {

    public Pointer context;
    
    public int[] paddingA = new int[16 - 1];

    public pBeginPaint BeginPaint;
    public pEndPaint EndPaint;
    public pSetBounds SetBounds;
    public pSynchronize Synchronize;
    public pDesktopResize DesktopResize;
    public pBitmapUpdate BitmapUpdate;
    public pPalette Palette;
    public pPlaySound PlaySound;
    public pSetKeyboardIndicators SetKeyboardIndicators;
    public pSetKeyboardImeStatus SetKeyboardImeStatus;
    
    public int[] paddingB = new int[32 - 26];

    public Pointer pointer;
    public Pointer primary;
    public Pointer secondary;
    public Pointer altsec;
    public Pointer window;

    public int[] paddingC = new int[48 - 37];

    public pRefreshRect RefreshRect;
    public pSuppressOutput SuppressOutput;
    public pRemoteMonitors RemoteMonitors;

    public int[] paddingD = new int[64 - 51];

    public pSurfaceCommand SurfaceCommand;
    public pSurfaceBits SurfaceBits;
    public pSurfaceFrameMarker SurfaceFrameMarker;
    public pSurfaceFrameBits SurfaceFrameBits;
    public pSurfaceFrameAcknowledge SurfaceFrameAcknowledge;
    public pSaveSessionInfo SaveSessionInfo;
    public pServerStatusInfo ServerStatusInfo;

    public boolean autoCalculateBitmapData;

    public int[] paddingE = new int[80 - 72];

    RdpUpdate(Pointer update) {
        super(update);
        read();
    }
    
    public void setBeginPaint(pBeginPaint beginPaint) {
        writeField("BeginPaint", beginPaint);
    }
    
    public void setEndPaint(pEndPaint endPaint) {
        writeField("EndPaint", endPaint);
    }
    
    public void setDesktopResize(pDesktopResize desktopResize) {
        writeField("DesktopResize", desktopResize);
    }
}

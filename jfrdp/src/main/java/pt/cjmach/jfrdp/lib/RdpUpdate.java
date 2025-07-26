/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author mach
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

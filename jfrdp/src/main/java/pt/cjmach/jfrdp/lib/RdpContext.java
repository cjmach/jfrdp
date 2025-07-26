/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author mach
 */
@FieldOrder({"instance", "peer", "ServerMode", "LastError", "paddingA", "argc",
    "argv", "pubSub", "channelErrorEvent", "channelErrorNum", "errorDescription",
    "paddingB", "rdp", "gdi", "rail", "cache", "channels", "graphics", "input",
    "update", "settings", "metrics", "codecs", "autodetect", "paddingC1",
    "disconnectUltimatum", "paddingC", "dump", "log", "paddingD", "paddingE"})
public class RdpContext extends Structure {

    public Pointer instance;
    public Pointer peer;
    public boolean ServerMode;
    public UInt LastError;
    
    public long[] paddingA = new long[16 - 4];
    
    public int argc;
    public Pointer argv;
    
    public Pointer pubSub;
    
    public Pointer channelErrorEvent;
    public UInt channelErrorNum;
    public Pointer errorDescription;
    
    public long[] paddingB = new long[32 - 22];

    public Pointer rdp;
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
        int gdiOffset = fieldOffset("gdi");
        Pointer gdiPointer = getPointer().getPointer(gdiOffset);
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
        return 8;
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

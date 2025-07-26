/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.Callback;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 *
 * @author mach
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

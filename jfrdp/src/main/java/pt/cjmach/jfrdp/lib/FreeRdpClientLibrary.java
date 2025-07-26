/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 *
 * @author mach
 */
public class FreeRdpClientLibrary {

    static final String LIBRARY_NAME = "freerdp-client3";

    static {
        Native.register(LIBRARY_NAME);
    }

    //
    // client/cmdline.h
    //
    public static native boolean freerdp_client_load_addins(Pointer channels, Pointer settings);

    //
    // client/channels.h
    //
    public static native Pointer freerdp_channels_load_static_addin_entry(Pointer pszName, Pointer pszSubsystem, Pointer pszType, UInt dwFlags);

    public static native Pointer freerdp_channels_get_static_channel_interface(Pointer channels, String name);

    //
    // client.h
    //
    public static native void freerdp_client_context_free(Pointer context);

    public static native Pointer freerdp_client_context_new(RdpClientEntryPoints pEntryPoints);

    public static native int freerdp_client_start(Pointer context);

    public static native int freerdp_client_stop(Pointer context);

    public static native Pointer freerdp_client_get_instance(Pointer context);

    public static native Pointer freerdp_client_get_thread(Pointer context);

    public static native void freerdp_client_OnChannelConnectedEventHandler(Pointer context, ChannelConnectedEventArgs e);

    public static native void freerdp_client_OnChannelDisconnectedEventHandler(Pointer context, ChannelDisconnectedEventArgs e);

    public static interface pRdpGlobalInit extends Callback {

        boolean invoke();
    }

    public static interface pRdpGlobalUninit extends Callback {

        void invoke();
    }

    public static interface pRdpClientNew extends Callback {

        boolean invoke(Pointer instance, Pointer context);
    }

    public static interface pRdpClientFree extends Callback {

        void invoke(Pointer instance, Pointer context);
    }

    public static interface pRdpClientStart extends Callback {

        int invoke(Pointer context);
    }

    public static interface pRdpClientStop extends Callback {

        int invoke(Pointer context);
    }

    public static interface LoadChannelAddinEntryFn extends Callback {

        Pointer invoke(Pointer pszName, Pointer pszSubsystem, Pointer pszType, UInt dwFlags);
    }

    public static interface pcDispCaps extends Callback {

        UInt invoke(Pointer context, UInt MaxNumMonitors, UInt MaxMonitorAreaFactorA, UInt MaxMonitorAreaFactorB);
    }

    public static interface pcDispSendMonitorLayout extends Callback {

        UInt invoke(Pointer context, UInt NumMonitors, DisplayControlMonitorLayout[] Monitors);
    }
}

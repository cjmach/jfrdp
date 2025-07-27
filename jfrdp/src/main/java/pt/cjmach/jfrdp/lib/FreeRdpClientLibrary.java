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
import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 *
 * @author cmachado
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
    
    /**
     * 
     * @param context
     * @return {@code 0} if successful, non-zero otherwise.
     */
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

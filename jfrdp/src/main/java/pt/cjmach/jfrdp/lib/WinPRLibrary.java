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
import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 *
 * @author cmachado
 */
public class WinPRLibrary {

    static final String LIBRARY_NAME = "winpr3";
    
    public static final int INFINITE = 0xFFFFFFFF;
    public static final int WAIT_OBJECT_0 = 0x00000000;
    public static final int WAIT_ABANDONED = 0x00000080;
    public static final int WAIT_IO_COMPLETION = 0x000000C0;

    public static final int WAIT_TIMEOUT = 0x00000102;
    public static final int WAIT_FAILED = 0xFFFFFFFF;
    public static final int MAXIMUM_WAIT_OBJECTS = 64;

    static {
        Native.register(LIBRARY_NAME);
    }

    //
    // winpr/collections.h
    //
    public static native int PubSub_Subscribe(Pointer pubSub, String eventName, Callback eventHandler);

    public static native int PubSub_Unsubscribe(Pointer pubSub, String eventName, Callback eventHandler);

    public static native int PubSub_OnEvent(Pointer pubSub, String eventName, Pointer context, Pointer e);

    //
    // winpr/input.h
    //
    public static native UInt GetVirtualScanCodeFromVirtualKeyCode(UInt vkcode, UInt /* WINPR_KBD_TYPE */ dwKeyboardType);

    public static native UInt GetVirtualKeyCodeFromKeycode(UInt keycode, UInt type);

    //
    // winpr/synch.h
    //
    public static native UInt WaitForMultipleObjects(UInt nCount, Pointer lpHandles, boolean bWaitAll, UInt dwMilliseconds);
    public static native UInt WaitForMultipleObjects(UInt nCount, long[] lpHandles, boolean bWaitAll, UInt dwMilliseconds);

    //
    // winpr/thread.h
    //
    public static native Pointer CreateThread(Pointer lpThreadAttributes, SizeT dwStackSize, PThreadStartRoutine lpStartAddress, Pointer lpParameter, UInt dwCreationFlags, Pointer lpThreadId);

    public static native boolean TerminateThread(Pointer hThread, UInt dwExitCode);
    
    public static interface PThreadStartRoutine extends Callback {
        UInt invoke(Pointer lpThreadParameter);
    }
    
    public static interface ChannelConnectedEventHandler extends Callback {
        void invoke(Pointer context, ChannelConnectedEventArgs e);
    }
    
    public static interface ChannelDisconnectedEventHandler extends Callback {
        void invoke(Pointer context, ChannelDisconnectedEventArgs e);
    }
    
    public static interface ResizeWindowChangeEventHandler extends Callback {
        void invoke(Pointer context, ResizeWindowChangeEventArgs e);
    }
}

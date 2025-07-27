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
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import static pt.cjmach.jfrdp.lib.FreeRdpClientLibrary.*;

/**
 *
 * @author cmachado
 */
public class RdpClient {

    private final RdpClientEntryPoints entryPoints;
    private final pRdpGlobalInit globalInit;
    private final pRdpGlobalUninit globalUninit;
    private final pRdpClientNew clientNew;
    private final pRdpClientFree clientFree;
    private final pRdpClientStart clientStart;
    private final pRdpClientStop clientStop;

    private FreeRdp freeRdp;
    private RdpContext context;

    public RdpClient() {
        globalInit = this::onGlobalInit;
        globalUninit = this::onGlobalUninit;
        clientNew = this::onClientNew;
        clientFree = this::onClientFree;
        clientStart = this::onClientStart;
        clientStop = this::onClientStop;

        entryPoints = new RdpClientEntryPoints();
        entryPoints.Size = new UInt(entryPoints.size());
        entryPoints.Version = new UInt(1);
        entryPoints.ContextSize = Native.getNativeSize(RdpContext.ByValue.class);
        entryPoints.GlobalInit = globalInit;
        entryPoints.GlobalUninit = globalUninit;
        entryPoints.ClientNew = clientNew;
        entryPoints.ClientFree = clientFree;
        entryPoints.ClientStart = clientStart;
        entryPoints.ClientStop = clientStop;
    }

    private void clientThread() {
        UInt infinite = new UInt(WinPRLibrary.INFINITE);
        try (Memory handles = new Memory(Native.POINTER_SIZE * WinPRLibrary.MAXIMUM_WAIT_OBJECTS)) {
            while (!context.shallDisconnect()) {
                UInt nCount = context.getEventHandles(handles, WinPRLibrary.MAXIMUM_WAIT_OBJECTS);
                if (nCount.longValue() == 0) {
                    break;
                }
                UInt waitStatus = WinPRLibrary.WaitForMultipleObjects(nCount, handles, false, infinite);
                if (waitStatus.intValue() == WinPRLibrary.WAIT_FAILED) {
                    break;
                }
                if (!context.checkEventHandles()) {
                    break;
                }
            }
        }
        freeRdp.disconnect();
    }

    public synchronized void createContext() {
        if (context != null) {
            return;
        }
        freerdp_client_context_new(entryPoints);
    }

    public RdpContext getContext() {
        if (context == null) {
            createContext();
        }
        return context;
    }

    boolean onGlobalInit() {
        return true;
    }

    void onGlobalUninit() {

    }

    boolean onClientNew(Pointer instance, Pointer context) {
        this.freeRdp = new FreeRdp(instance, context);
        this.context = this.freeRdp.getContext();
        return true;
    }

    void onClientFree(Pointer instance, Pointer context) {

    }

    int onClientStart(Pointer context) {
        if (!freeRdp.connect()) {
            return -1;
        }
        Thread th = new Thread(this::clientThread);
        th.setDaemon(true);
        th.start();
        return 0;
    }

    int onClientStop(Pointer context) {
        return 0;
    }

    public FreeRdp getFreeRdp() {
        return freeRdp;
    }

    public void start() throws RdpException {
        int result = freerdp_client_start(context.getPointer());
        if (result != 0) {
            throw new RdpException(context);
        }
    }
}

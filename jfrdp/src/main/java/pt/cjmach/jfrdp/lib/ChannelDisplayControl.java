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
import com.sun.jna.Pointer;

/**
 *
 * @author cmachado
 */
public class ChannelDisplayControl {

    public static final int ORIENTATION_LANDSCAPE = 0;
    public static final int ORIENTATION_PORTRAIT = 90;
    public static final int ORIENTATION_LANDSCAPE_FLIPPED = 180;
    public static final int ORIENTATION_PORTRAIT_FLIPPED = 270;

    public static final int DISPLAY_CONTROL_MONITOR_PRIMARY = 0x00000001;
    public static final int DISPLAY_CONTROL_HEADER_LENGTH = 0x00000008;

    public static final int DISPLAY_CONTROL_MIN_MONITOR_WIDTH = 200;
    public static final int DISPLAY_CONTROL_MAX_MONITOR_WIDTH = 8192;
    public static final int DISPLAY_CONTROL_MIN_MONITOR_HEIGHT = 200;
    public static final int DISPLAY_CONTROL_MAX_MONITOR_HEIGHT = 8192;

    public static final int DISPLAY_CONTROL_MIN_PHYSICAL_MONITOR_WIDTH = 10;
    public static final int DISPLAY_CONTROL_MAX_PHYSICAL_MONITOR_WIDTH = 10000;
    public static final int DISPLAY_CONTROL_MIN_PHYSICAL_MONITOR_HEIGHT = 10;
    public static final int DISPLAY_CONTROL_MAX_PHYSICAL_MONITOR_HEIGHT = 10000;

    private int maxNumMonitors;
    private int maxMonitorAreaFactorA;
    private int maxMonitorAreaFactorB;
    private boolean capsSet;
    private final DispClientContext context;
    private Runnable capsSetRunnable;
    private final FreeRdpClientLibrary.pcDispSendMonitorLayout sendMonitorLayout;

    private final FreeRdpClientLibrary.pcDispCaps dispCaps;

    ChannelDisplayControl(Pointer context) {
        this.context = new DispClientContext(context);
        dispCaps = this::onDisplayControlCaps;
        this.context.writeField("DisplayControlCaps", dispCaps);
        this.sendMonitorLayout = this.context.SendMonitorLayout;
    }

    private UInt onDisplayControlCaps(Pointer context, UInt maxNumMonitors, UInt maxMonitorAreaFactorA, UInt maxMonitorAreaFactorB) {
        this.maxNumMonitors = maxNumMonitors.intValue();
        this.maxMonitorAreaFactorA = maxMonitorAreaFactorA.intValue();
        this.maxMonitorAreaFactorB = maxMonitorAreaFactorB.intValue();
        this.capsSet = true;
        if (capsSetRunnable != null) {
            capsSetRunnable.run();
        }
        return new UInt(0);
    }

    public void resizeDisplay(int width, int height) {
        int requestWidth = Utils.clamp(width, DISPLAY_CONTROL_MIN_MONITOR_WIDTH,
                DISPLAY_CONTROL_MAX_MONITOR_WIDTH);
        int requestHeight = Utils.clamp(height, DISPLAY_CONTROL_MIN_MONITOR_HEIGHT,
                DISPLAY_CONTROL_MAX_MONITOR_HEIGHT);
        if (requestWidth % 2 == 1) {
            requestWidth--;
        }
        if (capsSet && (requestWidth * requestHeight) <= (maxNumMonitors * maxMonitorAreaFactorA * maxMonitorAreaFactorB)) {
            DisplayControlMonitorLayout monitorLayout = new DisplayControlMonitorLayout();
            monitorLayout.Flags = new UInt(DISPLAY_CONTROL_MONITOR_PRIMARY);
            monitorLayout.Width = new UInt(requestWidth);
            monitorLayout.Height = new UInt(requestHeight);
            monitorLayout.Orientation = new UInt(ORIENTATION_LANDSCAPE);
            monitorLayout.DesktopScaleFactor = new UInt(100);
            monitorLayout.DeviceScaleFactor = new UInt(100);

            monitorLayout.write();
            DisplayControlMonitorLayout[] layouts = new DisplayControlMonitorLayout[]{monitorLayout};
            sendMonitorLayout.invoke(context.getPointer(), new UInt(1), layouts);
        }
    }

    public void setCapsSet(Runnable capsSet) {
        this.capsSetRunnable = capsSet;
    }

}

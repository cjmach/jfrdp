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
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author cmachado
 */
@FieldOrder({"Flags", "Left", "Top", "Width", "Height", "PhysicalWidth",
    "PhysicalHeight", "Orientation", "DesktopScaleFactor", "DeviceScaleFactor"})
public class DisplayControlMonitorLayout extends Structure {
    public UInt Flags;
    public int Left;
    public int Top;
    public UInt Width;
    public UInt Height;
    public UInt PhysicalWidth;
    public UInt PhysicalHeight;
    public UInt Orientation;
    public UInt DesktopScaleFactor;
    public UInt DeviceScaleFactor;
}

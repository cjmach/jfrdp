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
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author cmachado
 */
@FieldOrder({"selectedObject", "format", "bkColor", "textColor", "brush", "clip", 
    "pen", "hwnd", "drawMode", "bkMode"})
public class GdiDC extends Structure {

    public Pointer selectedObject;
    public UInt format;
    public UInt bkColor;
    public UInt textColor;
    public Pointer brush;
    public Pointer clip;
    public Pointer pen;
    public Pointer hwnd;
    public int drawMode;
    public int bkMode;
    
    private GdiWnd wndObj;
    
    GdiDC(Pointer pointer) {
        super(pointer);
        read();
    }
    
    public GdiWnd getWnd() {
        Pointer ptr = (Pointer) readField("hwnd");
        if (ptr == Pointer.NULL) {
            return null;
        }
        if (wndObj == null || !wndObj.getPointer().equals(ptr)) {
            wndObj = new GdiWnd(ptr);
        }
        return wndObj;
    }
}

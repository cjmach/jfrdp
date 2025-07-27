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

import pt.cjmach.jfrdp.lib.types.ULong;
import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.SizeT;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author cmachado
 */
@FieldOrder({"size", "New", "Free", "Paint", "Decompress", "SetSurface",
    "paddingA", "left", "top", "right", "bottom", "width", "height", "format",
    "flags", "length", "data", "key64", "paddingB", "compressed", "ephemeral",
    "paddingC"})
public class RdpBitmap extends Structure {

    public SizeT size;
    public Pointer New;
    public Pointer Free;
    public Pointer Paint;
    public Pointer Decompress;
    public Pointer SetSurface;

    public int[] paddingA = new int[16 - 6];

    public UInt left;
    public UInt top;
    public UInt right;
    public UInt bottom;
    public UInt width;
    public UInt height;
    public UInt format;
    public UInt flags;
    public UInt length;
    public Pointer data;
    public ULong key64;

    public int[] paddingB = new int[32 - 27];

    public boolean compressed;
    public boolean ephemeral;

    public int[] paddingC = new int[64 - 34];
}

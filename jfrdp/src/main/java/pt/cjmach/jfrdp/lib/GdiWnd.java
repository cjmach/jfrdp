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
@FieldOrder({"count", "ninvalid", "invalid", "cinvalid"})
public class GdiWnd extends Structure {

    public UInt count;
    public int ninvalid;
    public Pointer invalid;
    public Pointer cinvalid;
    
    GdiWnd() {
    }
    
    GdiWnd(Pointer pointer) {
        super(pointer);
        read();
    }
    
    public GdiRgn[] getCInvalid() {
        int n = getNInvalid();
        if (n <= 0) {
            return new GdiRgn[0];
        }
        Pointer cinv = (Pointer) readField("cinvalid");
        if (cinv == Pointer.NULL) {
            return new GdiRgn[0];
        }
        GdiRgn firstRgn = new GdiRgn(cinv);
        GdiRgn[] result = (GdiRgn[]) firstRgn.toArray(n);
        return result;
    }
    
    public GdiRgn getInvalid() {
        Pointer ptr = (Pointer) readField("invalid");
        return new GdiRgn(ptr);
    }
    
    public int getNInvalid() {
        return (int) readField("ninvalid");
    }
    
    public void setNInvalid(int value) {
        writeField("ninvalid", value);
    }
    
    public static class ByValue extends GdiWnd implements Structure.ByValue {
    }
}

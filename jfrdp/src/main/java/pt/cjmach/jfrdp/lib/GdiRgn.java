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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author cmachado
 */
@FieldOrder({"objectType", "x", "y", "w", "h", "Null"})
public class GdiRgn extends Structure {

    public byte objectType;
    public int x;
    public int y;
    public int w;
    public int h;
    public boolean Null;

    public GdiRgn() {
    }

    public GdiRgn(Pointer pointer) {
        super(pointer);
        read();
    }

    public int getH() {
        return (int) readField("h");
    }

    public boolean isNull() {
        return (boolean) readField("Null");
    }

    public void setNull(boolean v) {
        writeField("Null", v);
    }

    public int getX() {
        return (int) readField("x");
    }

    public int getY() {
        return (int) readField("y");
    }

    public int getW() {
        return (int) readField("w");
    }

    public static class ByValue extends GdiRgn implements Structure.ByValue {
    }
}

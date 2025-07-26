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

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author cmachado
 */
@FieldOrder({"e", "Width", "Height"})
public class ResizeWindowChangeEventArgs extends Structure {

    public WEventArgs e;
    public int Width;
    public int Height;
    
    public ResizeWindowChangeEventArgs() {
    }
    
    public ResizeWindowChangeEventArgs(int width, int height) {
        this.Width = width;
        this.Height = height;
    }
    
    public static class ByValue extends ResizeWindowChangeEventArgs implements Structure.ByValue {
    }
}

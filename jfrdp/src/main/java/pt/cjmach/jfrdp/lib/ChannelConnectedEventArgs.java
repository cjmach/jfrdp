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
import java.nio.charset.StandardCharsets;

/**
 *
 * @author cmachado
 */
@FieldOrder({"e", "name", "Interface"})
public class ChannelConnectedEventArgs extends Structure {
    public WEventArgs e;
    public Pointer name;
    public Pointer Interface;
    
    public Pointer getInterface() {
        return Interface;
    }
    
    public String getName() {
        return name.getString(0, StandardCharsets.US_ASCII.name());
    }
    
    public static class ByValue extends ChannelConnectedEventArgs implements Structure.ByValue {
        
    }
    
    public static class ByReference extends ChannelConnectedEventArgs implements Structure.ByReference {
        
    }
}

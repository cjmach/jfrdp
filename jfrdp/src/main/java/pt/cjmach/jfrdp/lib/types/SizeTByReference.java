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
package pt.cjmach.jfrdp.lib.types;

import com.sun.jna.Native;
import com.sun.jna.ptr.ByReference;

/**
 *
 * @author cmachado
 */
public class SizeTByReference extends ByReference {
    public SizeTByReference() {
        this(new SizeT(0));
    }
    
    public SizeTByReference(SizeT value) {
        super(Native.SIZE_T_SIZE);
    }
    
    public void setValue(SizeT value) {
        switch (Native.SIZE_T_SIZE) {
            case 4:
                getPointer().setInt(0, value.intValue());
                break;
                
            case 8:
                getPointer().setLong(0, value.longValue());
                break;
                
            default:
                throw new IllegalArgumentException("Unsupported size_t size: " + Native.SIZE_T_SIZE);
        }
    }
    
    public SizeT getValue() {
        switch (Native.SIZE_T_SIZE) {
            case 4:
                return new SizeT(getPointer().getInt(0));
                
            case 8:
                return new SizeT(getPointer().getLong(0));
                
            default:
                throw new IllegalArgumentException("Unsupported size_t size: " + Native.SIZE_T_SIZE);
        }
    }
}

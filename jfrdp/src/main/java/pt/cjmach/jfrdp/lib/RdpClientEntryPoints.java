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

import static pt.cjmach.jfrdp.lib.FreeRdpClientLibrary.*;

/**
 *
 * @author cmachado
 */
@FieldOrder({"Size", "Version", "settings", "GlobalInit", "GlobalUninit",
    "ContextSize", "ClientNew", "ClientFree", "ClientStart", "ClientStop"})
public class RdpClientEntryPoints extends Structure {

    public UInt Size;
    public UInt Version;

    public Pointer settings;

    public pRdpGlobalInit GlobalInit;
    public pRdpGlobalUninit GlobalUninit;

    public int ContextSize;
    public pRdpClientNew ClientNew;
    public pRdpClientFree ClientFree;

    public pRdpClientStart ClientStart;
    public pRdpClientStop ClientStop;

    public static class ByValue extends RdpClientEntryPoints implements Structure.ByReference {
    }

    public static class ByReference extends RdpClientEntryPoints implements Structure.ByReference {
    }
}

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

/**
 *
 * @author cmachado
 */
public enum KeyboardType {
    /* IBM PC/XT or compatible (83-key) keyboard */
    IBM_PC_XT(0x00000001),
    /* Olivetti "ICO" (102-key) keyboard */
    OLIVETTI_ICO(0x00000002),
    /* IBM PC/AT (84-key) and similar keyboards */
    IBM_PC_AT(0x00000003),
    /* IBM enhanced (101-key or 102-key) keyboard */
    IBM_ENHANCED(0x00000004),
    /* Nokia 1050 and similar keyboards */
    NOKIA_1050(0x00000005),
    /* Nokia 9140 and similar keyboards */
    NOKIA_9140(0x00000006),
    /* JAPANESE keyboard */
    JAPANESE(0x00000007),
    /* KOREAN keyboard */
    KOREAN(0x00000008);

    private final int value;

    private KeyboardType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

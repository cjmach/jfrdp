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
public enum PixelFormat { 
    /* 32bpp formats */
    ARGB32(pixelFormat(32, PixelFormatType.ARGB, 8, 8, 8, 8)),
    XRGB32(pixelFormat(32, PixelFormatType.ARGB, 0, 8, 8, 8)),
    ABGR32(pixelFormat(32, PixelFormatType.ABGR, 8, 8, 8, 8)),
    XBGR32(pixelFormat(32, PixelFormatType.ABGR, 0, 8, 8, 8)),
    BGRA32(pixelFormat(32, PixelFormatType.BGRA, 8, 8, 8, 8)),
    BGRX32(pixelFormat(32, PixelFormatType.BGRA, 0, 8, 8, 8)),
    RGBA32(pixelFormat(32, PixelFormatType.RGBA, 8, 8, 8, 8)),
    RGBX32(pixelFormat(32, PixelFormatType.RGBA, 0, 8, 8, 8)),
    BGRX32_DEPTH30(pixelFormat(32, PixelFormatType.BGRA, 0, 10, 10, 10)),
    RGBX32_DEPTH30(pixelFormat(32, PixelFormatType.RGBA, 0, 10, 10, 10)),

    /* 24bpp formats */
    RGB24(pixelFormat(24, PixelFormatType.ARGB, 0, 8, 8, 8)),
    BGR24(pixelFormat(24, PixelFormatType.ABGR, 0, 8, 8, 8)),

    /* 16bpp formats */
    RGB16(pixelFormat(16, PixelFormatType.ARGB, 0, 5, 6, 5)),
    BGR16(pixelFormat(16, PixelFormatType.ABGR, 0, 5, 6, 5)),
    ARGB15(pixelFormat(16, PixelFormatType.ARGB, 1, 5, 5, 5)),
    RGB15(pixelFormat(15, PixelFormatType.ARGB, 0, 5, 5, 5)),
    ABGR15(pixelFormat(16, PixelFormatType.ABGR, 1, 5, 5, 5)),
    BGR15(pixelFormat(15, PixelFormatType.ABGR, 0, 5, 5, 5)),

    /* 8bpp formats */
    RGB8(pixelFormat(8, PixelFormatType.A, 8, 0, 0, 0)),

    /* 4 bpp formats */
    A4(pixelFormat(4, PixelFormatType.A, 4, 0, 0, 0)),

    /* 1bpp formats */
    MONO(pixelFormat(1, PixelFormatType.A, 1, 0, 0, 0));
    
    private final int value;
    
    private PixelFormat(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

    static int pixelFormat(int bpp, PixelFormatType type, int a, int r, int g, int b) {
        return (bpp << 24) | (type.getValue() << 16) | (a << 12) | (r << 8) | (g << 4) | (b);
    }
}

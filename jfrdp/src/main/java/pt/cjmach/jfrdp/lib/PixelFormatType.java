/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

/**
 *
 * @author mach
 */
enum PixelFormatType {
    A(0),
    ARGB(1),
    ABGR(2),
    RGBA(3),
    BGRA(4);
    
    private final int value;
    
    private PixelFormatType(int value) {
        this.value = value;
    }
    
    int getValue() {
        return value;
    }
}

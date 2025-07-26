/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib.types;

import com.sun.jna.IntegerType;

/**
 *
 * @author mach
 */
public class UInt extends IntegerType {
    public static final UInt ZERO = new UInt(0);
    
    public UInt() {
        super(4, true);
    }
    
    public UInt(long value) {
        super(4, value, true);
    }
}

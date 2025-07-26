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
public class ULong extends IntegerType {
    public ULong() {
        super(8, true);
    }
    
    public ULong(long value) {
        super(8, value, true);
    }
}

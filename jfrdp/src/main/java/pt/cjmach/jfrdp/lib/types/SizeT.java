/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib.types;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

/**
 *
 * @author mach
 */
public class SizeT extends IntegerType {
    
    public SizeT() {
        super(Native.SIZE_T_SIZE, true);
    }
    
    public SizeT(long value) {
        super(Native.SIZE_T_SIZE, value, true);
    }
}

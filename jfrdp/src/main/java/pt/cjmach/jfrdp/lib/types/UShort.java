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
public class UShort extends IntegerType {
    
    public UShort() {
        super(2, true);
    }
    
    public UShort(int value) {
        super(2, value, true);
    }
    
}

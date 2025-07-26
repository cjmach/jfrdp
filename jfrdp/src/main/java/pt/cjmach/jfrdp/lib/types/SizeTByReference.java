/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib.types;

import com.sun.jna.Native;
import com.sun.jna.ptr.ByReference;

/**
 *
 * @author mach
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

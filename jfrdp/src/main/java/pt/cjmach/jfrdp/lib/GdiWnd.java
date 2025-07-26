/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author mach
 */
@FieldOrder({"count", "ninvalid", "invalid", "cinvalid"})
public class GdiWnd extends Structure {

    public UInt count;
    public int ninvalid;
    public Pointer invalid;
    public Pointer cinvalid;
    
    GdiWnd() {
    }
    
    GdiWnd(Pointer pointer) {
        super(pointer);
        read();
    }
    
    public GdiRgn[] getCInvalid() {
        int n = getNInvalid();
        if (n <= 0) {
            return new GdiRgn[0];
        }
        Pointer cinv = (Pointer) readField("cinvalid");
        if (cinv == Pointer.NULL) {
            return new GdiRgn[0];
        }
        GdiRgn firstRgn = new GdiRgn(cinv);
        GdiRgn[] result = (GdiRgn[]) firstRgn.toArray(n);
        return result;
    }
    
    public GdiRgn getInvalid() {
        Pointer ptr = (Pointer) readField("invalid");
        return new GdiRgn(ptr);
    }
    
    public int getNInvalid() {
        return (int) readField("ninvalid");
    }
    
    public void setNInvalid(int value) {
        writeField("ninvalid", value);
    }
    
    public static class ByValue extends GdiWnd implements Structure.ByValue {
    }
}

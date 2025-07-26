/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author mach
 */
@FieldOrder({"p", "hdc", "bitmap", "org_bitmap"})
public class GdiBitmap extends Structure {

    public RdpBitmap p;
    public Pointer hdc;
    public Pointer bitmap;
    public Pointer org_bitmap;
    
    private GdiDC dcObj;
    
    GdiBitmap(Pointer bitmap) {
        super(bitmap);
        read();
    }
    
    public GdiDC getDC() {
        Pointer ptr = (Pointer) readField("hdc");
        if (ptr == Pointer.NULL) {
            return null;
        }
        if (dcObj == null || !dcObj.getPointer().equals(ptr)) {
            dcObj = new GdiDC(ptr);
        }
        return dcObj;
    }
}

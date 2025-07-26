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
@FieldOrder({"selectedObject", "format", "bkColor", "textColor", "brush", "clip", 
    "pen", "hwnd", "drawMode", "bkMode"})
public class GdiDC extends Structure {

    public Pointer selectedObject;
    public UInt format;
    public UInt bkColor;
    public UInt textColor;
    public Pointer brush;
    public Pointer clip;
    public Pointer pen;
    public Pointer hwnd;
    public int drawMode;
    public int bkMode;
    
    private GdiWnd wndObj;
    
    GdiDC(Pointer pointer) {
        super(pointer);
        read();
    }
    
    public GdiWnd getWnd() {
        Pointer ptr = (Pointer) readField("hwnd");
        if (ptr == Pointer.NULL) {
            return null;
        }
        if (wndObj == null || !wndObj.getPointer().equals(ptr)) {
            wndObj = new GdiWnd(ptr);
        }
        return wndObj;
    }
}

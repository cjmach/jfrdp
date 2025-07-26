/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.SizeT;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author mach
 */
@FieldOrder({"size", "New", "Free", "Set", "SetNull", "SetDefault", "SetPosition",
    "paddingA", "xPos", "yPos", "width", "height", "xorBpp", "lengthAndMask",
    "lengthXorMask", "xorMaskData", "andMaskData", "paddingB"})
public class RdpPointer extends Structure {

    public SizeT size;

    public pPointerNew New;
    public pPointerFree Free;
    public pPointerSet Set;
    public pPointerSetNull SetNull;
    public pPointerSetDefault SetDefault;
    public pPointerSetPosition SetPosition;

    public int[] paddingA = new int[16 - 7];

    public UInt xPos;
    public UInt yPos;
    public UInt width;
    public UInt height;
    public UInt xorBpp;
    public UInt lengthAndMask;
    public UInt lengthXorMask;
    public Pointer xorMaskData;
    public Pointer andMaskData;

    public int[] paddingB = new int[32 - 25];

    public RdpPointer() {

    }

    public RdpPointer(Pointer pointer) {
        super(pointer);
        read();
    }

    public boolean copy(byte[] destination, PixelFormat format) {
        boolean result = FreeRdpLibrary.freerdp_image_copy_from_pointer_data(
                destination, new UInt(format.getValue()), UInt.ZERO, UInt.ZERO, 
                UInt.ZERO, width, height, xorMaskData, lengthXorMask, andMaskData, 
                lengthAndMask, xorBpp, Pointer.NULL);
        return result;
    }
    
    public int getHeight() {
        return height.intValue();
    }
    
    public int getX() {
        return xPos.intValue();
    }
    
    public int getWidth() {
        return width.intValue();
    }
    
    public int getY() {
        return yPos.intValue();
    }

    public static class ByValue extends RdpPointer implements Structure.ByValue {
    }
}

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
@FieldOrder({"objectType", "x", "y", "w", "h", "Null"})
public class GdiRgn extends Structure {

    public byte objectType;
    public int x;
    public int y;
    public int w;
    public int h;
    public boolean Null;

    public GdiRgn() {
    }

    public GdiRgn(Pointer pointer) {
        super(pointer);
        read();
    }

    public int getH() {
        return (int) readField("h");
    }

    public boolean isNull() {
        return (boolean) readField("Null");
    }

    public void setNull(boolean v) {
        writeField("Null", v);
    }

    public int getX() {
        return (int) readField("x");
    }

    public int getY() {
        return (int) readField("y");
    }

    public int getW() {
        return (int) readField("w");
    }

    public static class ByValue extends GdiRgn implements Structure.ByValue {
    }
}

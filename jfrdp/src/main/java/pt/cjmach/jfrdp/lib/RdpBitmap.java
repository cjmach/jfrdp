/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.ULong;
import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.SizeT;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author mach
 */
@FieldOrder({"size", "New", "Free", "Paint", "Decompress", "SetSurface",
    "paddingA", "left", "top", "right", "bottom", "width", "height", "format",
    "flags", "length", "data", "key64", "paddingB", "compressed", "ephemeral",
    "paddingC"})
public class RdpBitmap extends Structure {

    public SizeT size;
    public Pointer New;
    public Pointer Free;
    public Pointer Paint;
    public Pointer Decompress;
    public Pointer SetSurface;

    public int[] paddingA = new int[16 - 6];

    public UInt left;
    public UInt top;
    public UInt right;
    public UInt bottom;
    public UInt width;
    public UInt height;
    public UInt format;
    public UInt flags;
    public UInt length;
    public Pointer data;
    public ULong key64;

    public int[] paddingB = new int[32 - 27];

    public boolean compressed;
    public boolean ephemeral;

    public int[] paddingC = new int[64 - 34];
}

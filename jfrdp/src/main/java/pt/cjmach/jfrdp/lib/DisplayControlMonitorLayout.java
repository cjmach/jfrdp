/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author mach
 */
@FieldOrder({"Flags", "Left", "Top", "Width", "Height", "PhysicalWidth",
    "PhysicalHeight", "Orientation", "DesktopScaleFactor", "DeviceScaleFactor"})
public class DisplayControlMonitorLayout extends Structure {
    public UInt Flags;
    public int Left;
    public int Top;
    public UInt Width;
    public UInt Height;
    public UInt PhysicalWidth;
    public UInt PhysicalHeight;
    public UInt Orientation;
    public UInt DesktopScaleFactor;
    public UInt DeviceScaleFactor;
}

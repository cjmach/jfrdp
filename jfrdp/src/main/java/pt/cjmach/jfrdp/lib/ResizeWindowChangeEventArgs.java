/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 *
 * @author mach
 */
@FieldOrder({"e", "Width", "Height"})
public class ResizeWindowChangeEventArgs extends Structure {

    public WEventArgs e;
    public int Width;
    public int Height;
    
    public ResizeWindowChangeEventArgs() {
    }
    
    public ResizeWindowChangeEventArgs(int width, int height) {
        this.Width = width;
        this.Height = height;
    }
    
    public static class ByValue extends ResizeWindowChangeEventArgs implements Structure.ByValue {
    }
}

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
@FieldOrder({"size", "sender"})
public class WEventArgs extends Structure {

    public int size;
    public Pointer sender;

    public static class ByValue extends WEventArgs implements Structure.ByValue {
    }

    public static class ByReference extends WEventArgs implements Structure.ByReference {
    }
}

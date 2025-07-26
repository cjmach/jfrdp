/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mach
 */
@FieldOrder({"e", "name", "Interface"})
public class ChannelDisconnectedEventArgs extends Structure {
    public WEventArgs e;
    public Pointer name;
    public Pointer Interface;
    
    public Pointer getInterface() {
        return Interface;
    }
    
    public String getName() {
        return name.getString(0, StandardCharsets.US_ASCII.name());
    }
    
    public static class ByValue extends ChannelDisconnectedEventArgs implements Structure.ByValue {
        
    }
    
    public static class ByReference extends ChannelDisconnectedEventArgs implements Structure.ByReference {
        
    }
}

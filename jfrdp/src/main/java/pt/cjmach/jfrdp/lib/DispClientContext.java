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

@FieldOrder({"handle", "custom", "DisplayControlCaps", "SendMonitorLayout"})
public class DispClientContext extends Structure {
    public Pointer handle;
    public Pointer custom;
    public FreeRdpClientLibrary.pcDispCaps DisplayControlCaps;
    public FreeRdpClientLibrary.pcDispSendMonitorLayout SendMonitorLayout;
    
    public DispClientContext(Pointer context) {
        super(context);
        read();
    }
}

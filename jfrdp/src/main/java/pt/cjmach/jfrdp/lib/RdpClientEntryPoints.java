/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

import static pt.cjmach.jfrdp.lib.FreeRdpClientLibrary.*;

/**
 *
 * @author mach
 */
@FieldOrder({"Size", "Version", "settings", "GlobalInit", "GlobalUninit",
    "ContextSize", "ClientNew", "ClientFree", "ClientStart", "ClientStop"})
public class RdpClientEntryPoints extends Structure {

    public UInt Size;
    public UInt Version;

    public Pointer settings;

    public pRdpGlobalInit GlobalInit;
    public pRdpGlobalUninit GlobalUninit;

    public int ContextSize;
    public pRdpClientNew ClientNew;
    public pRdpClientFree ClientFree;

    public pRdpClientStart ClientStart;
    public pRdpClientStop ClientStop;

    public static class ByValue extends RdpClientEntryPoints implements Structure.ByReference {
    }

    public static class ByReference extends RdpClientEntryPoints implements Structure.ByReference {
    }
}

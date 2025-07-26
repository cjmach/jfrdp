/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

/**
 *
 * @author mach
 */
public enum RdpAuthReason {
    // do not change the order. Based on the ordinal() value.
    NLA,
    TLS,
    RDP,
    GATEWAY_AUTH_HTTP,
    GATEWAY_AUTH_RDG,
    GATEWAY_AUTH_RPC,
    SMARTCARD_PIN;
}

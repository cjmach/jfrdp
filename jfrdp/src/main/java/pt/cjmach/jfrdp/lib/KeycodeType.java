/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

/**
 *
 * @author mach
 */
public enum KeycodeType {
    None(0x00000000),
    Apple(0x00000001),
    Evdev(0x00000002),
    Xkb(0x00000003);

    private final int type;

    private KeycodeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

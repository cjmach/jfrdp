/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

/**
 *
 * @author mach
 */
public enum KeyboardType {
    /* IBM PC/XT or compatible (83-key) keyboard */
    IBM_PC_XT(0x00000001),
    /* Olivetti "ICO" (102-key) keyboard */
    OLIVETTI_ICO(0x00000002),
    /* IBM PC/AT (84-key) and similar keyboards */
    IBM_PC_AT(0x00000003),
    /* IBM enhanced (101-key or 102-key) keyboard */
    IBM_ENHANCED(0x00000004),
    /* Nokia 1050 and similar keyboards */
    NOKIA_1050(0x00000005),
    /* Nokia 9140 and similar keyboards */
    NOKIA_9140(0x00000006),
    /* JAPANESE keyboard */
    JAPANESE(0x00000007),
    /* KOREAN keyboard */
    KOREAN(0x00000008);

    private final int value;

    private KeyboardType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

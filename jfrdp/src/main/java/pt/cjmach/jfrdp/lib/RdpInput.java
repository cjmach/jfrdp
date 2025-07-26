/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.UShort;
import com.sun.jna.Pointer;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author mach
 */
public class RdpInput {

    /**
     * Key Flags
     */
    private static final short KBD_EXT = 0x0100;

    /* keyboard Flags */
    private static final int KBD_FLAGS_EXTENDED = 0x0100;
    private static final int KBD_FLAGS_EXTENDED1 = 0x0200;
    private static final int KBD_FLAGS_DOWN = 0x4000;
    private static final int KBD_FLAGS_RELEASE = 0x8000;

    /* Pointer Flags */
    private static final short PTR_FLAGS_HWHEEL = 0x0400;
    private static final short PTR_FLAGS_WHEEL = 0x0200;
    private static final short PTR_FLAGS_WHEEL_NEGATIVE = 0x0100;
    private static final short PTR_FLAGS_MOVE = 0x0800;
    private static final int PTR_FLAGS_DOWN = 0x8000;
    private static final short PTR_FLAGS_BUTTON1 = 0x1000;
    /* left */
    private static final short PTR_FLAGS_BUTTON2 = 0x2000;
    /* right */
    private static final short PTR_FLAGS_BUTTON3 = 0x4000;
    /* middle */
    private static final short WHEEL_ROTATION_MASK = 0x01FF;

    /* Extended Pointer Flags */
    private static final int PTR_XFLAGS_DOWN = 0x8000;
    private static final short PTR_XFLAGS_BUTTON1 = 0x0001;
    private static final short PTR_XFLAGS_BUTTON2 = 0x0002;

    /* Keyboard Toggle Flags */
    public static final int KEYBOARD_SYNC_SCROLL_LOCK = 0x00000001;
    public static final int KEYBOARD_SYNC_NUM_LOCK = 0x00000002;
    public static final int KEYBOARD_SYNC_CAPS_LOCK = 0x00000004;
    public static final int KEYBOARD_SYNC_KANA_LOCK = 0x00000008;
    
    public static final int MOUSE_MOVE = (1 << 0);
    public static final int MOUSE_DOWN = (1 << 1);
    public static final int MOUSE_WHEEL = (1 << 2);
    public static final int MOUSE_WHEEL_NEGATIVE = (1 << 3);
    public static final int MOUSE_BUTTON1 = (1 << 4);
    public static final int MOUSE_BUTTON2 = (1 << 5);
    public static final int MOUSE_BUTTON3 = (1 << 6);
    public static final int MOUSE_BUTTON4 = (1 << 7);
    public static final int MOUSE_BUTTON5 = (1 << 8);
    public static final int MOUSE_HWHEEL = (1 << 9);

    private static final short RDP_SCANCODE_UNKNOWN = MakeRdpScancode((byte) 0x00, false);

    private final Pointer handle;

    RdpInput(Pointer handle) {
        this.handle = handle;
    }

    public boolean sendFocusInEvent(int flags) {
        return freerdp_input_send_focus_in_event(handle, new UShort(flags));
    }

    boolean sendKeyboardEventEx(boolean down, boolean repeat, UInt rdpScancode) {
        return freerdp_input_send_keyboard_event_ex(handle, down, repeat, rdpScancode);
    }

    boolean sendExtendedMouseEvent(UShort flags, UShort x, UShort y) {
        return freerdp_input_send_extended_mouse_event(handle, flags, x, y);
    }

    boolean sendMouseEvent(UShort flags, UShort x, UShort y) {
        return freerdp_input_send_mouse_event(handle, flags, x, y);
    }

    public boolean sendKey(int keyCode, boolean down) {
        return sendKey(keyCode, down, KeycodeType.Xkb, KeyboardType.IBM_ENHANCED);
    }

    public boolean sendKey(int keyCode, boolean down, KeycodeType keycodeType, KeyboardType keyboardType) {
        UInt virtualCode, virtualScanCode;
        virtualCode = getVirtualKeyCodeFromKeycode(new UInt(keyCode), keycodeType);
        virtualScanCode = getVirtualScanCodeFromVirtualKeyCode(virtualCode, keyboardType);

        if (virtualScanCode.shortValue() != RDP_SCANCODE_UNKNOWN) {
            return sendKeyboardEventEx(down, false, virtualScanCode);
        }
        return true;
    }

    public boolean sendUnicodeKey(boolean down, int code) {
        int flags = down ? KBD_FLAGS_DOWN : KBD_FLAGS_RELEASE;
        return freerdp_input_send_unicode_keyboard_event(handle, new UInt(flags), new UShort(code));
    }

    public boolean sendMouseEvent(int e, int x, int y, boolean scaling, double scale, double offsetX, double offsetY) {
        int flags = 0;
        int xflags = 0;

        if ((e & MOUSE_MOVE) != 0) {
            flags |= PTR_FLAGS_MOVE;
        }
        if ((e & MOUSE_DOWN) != 0) {
            flags |= PTR_FLAGS_DOWN;
        }
        if ((e & MOUSE_WHEEL) != 0) {
            flags |= PTR_FLAGS_WHEEL;
            if ((e & MOUSE_WHEEL_NEGATIVE) != 0) {
                flags |= PTR_FLAGS_WHEEL_NEGATIVE | 0x0088;
            } else {
                flags |= 0x0078;
            }
        }
        if ((e & MOUSE_HWHEEL) != 0) {
            flags |= PTR_FLAGS_HWHEEL;
            if ((e & MOUSE_WHEEL_NEGATIVE) != 0) {
                flags |= PTR_FLAGS_WHEEL_NEGATIVE | 0x0088;
            } else {
                flags |= 0x0078;
            }
        }

        if ((e & MOUSE_BUTTON1) != 0) {
            flags |= PTR_FLAGS_BUTTON1;
        }
        if ((e & MOUSE_BUTTON2) != 0) {
            flags |= PTR_FLAGS_BUTTON2;
        }
        if ((e & MOUSE_BUTTON3) != 0) {
            flags |= PTR_FLAGS_BUTTON3;
        }
        if ((e & MOUSE_BUTTON4) != 0) {
            xflags |= PTR_XFLAGS_BUTTON1;
        }
        if ((e & MOUSE_BUTTON5) != 0) {
            xflags |= PTR_XFLAGS_BUTTON2;
        }

//            if (scaling)
//            {
//                x = (ushort)((x - offsetX) / scale);
//                y = (ushort)((y - offsetY) / scale);
//            }
        if (xflags != 0) {
            if ((e & MOUSE_DOWN) != 0) {
                xflags |= PTR_XFLAGS_DOWN;
            }
            return sendExtendedMouseEvent(new UShort(xflags), new UShort(x), new UShort(y));
        }
        if (flags != 0) {
            return sendMouseEvent(new UShort(flags), new UShort(x), new UShort(y));
        }
        return true;
    }

    public boolean sendSmoothScroll(double deltaX, double deltaY) {
        int flags = 0, value;
        if (Math.abs(deltaY) >= Math.abs(deltaX)) {
            flags |= PTR_FLAGS_WHEEL;
            value = (int) Math.round(Math.abs(deltaY) * 0x78);
            if (value > 0) {
                /* Reversing direction here to reflect the behaviour on local side. */
                if (deltaY < 0.0) {
                    if (value > 255) {
                        value = 255;
                    }
                    flags |= (int) (value & WHEEL_ROTATION_MASK);
                } else {
                    if (value > 256) {
                        value = 256;
                    }
                    flags |= PTR_FLAGS_WHEEL_NEGATIVE;
                    flags |= (int) ((~value + 1) & WHEEL_ROTATION_MASK);
                }
            }
        } else {
            flags |= PTR_FLAGS_HWHEEL;
            value = (int) Math.round(Math.abs(deltaX) * 0x78);
            if (value > 0) {
                if (deltaX < 0.0) {
                    if (value > 256) {
                        value = 256;
                    }
                    flags |= PTR_FLAGS_WHEEL_NEGATIVE;
                    flags |= (int) ((~value + 1) & WHEEL_ROTATION_MASK);
                } else {
                    if (value > 255) {
                        value = 255;
                    }
                    flags |= (int) (value & WHEEL_ROTATION_MASK);
                }
            }
        }
        return sendMouseEvent(new UShort(flags), new UShort(0), new UShort(0));
    }

    private static byte MakeRdpScancode(byte _code, boolean _extended) {
        return (byte) ((_code & 0xFF) | (_extended ? KBD_EXT : 0));
    }

    static UInt getVirtualKeyCodeFromKeycode(UInt keycode, KeycodeType type) {
        return WinPRLibrary.GetVirtualKeyCodeFromKeycode(keycode, new UInt(type.getType()));
    }

    static UInt getVirtualScanCodeFromVirtualKeyCode(UInt vkcode, UInt /* WINPR_KBD_TYPE */ dwKeyboardType) {
        return WinPRLibrary.GetVirtualScanCodeFromVirtualKeyCode(vkcode, dwKeyboardType);
    }

    static UInt getVirtualScanCodeFromVirtualKeyCode(UInt vkcode, KeyboardType dwKeyboardType) {
        return getVirtualScanCodeFromVirtualKeyCode(vkcode, new UInt(dwKeyboardType.getValue()));
    }
}

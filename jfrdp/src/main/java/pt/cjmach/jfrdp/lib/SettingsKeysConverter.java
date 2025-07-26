/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import com.sun.jna.ToNativeContext;
import com.sun.jna.ToNativeConverter;

/**
 *
 * @author mach
 */
class SettingsKeysConverter implements ToNativeConverter {

    @Override
    public Class<?> nativeType() {
        return Integer.class;
    }

    @Override
    public Object toNative(Object value, ToNativeContext context) {
        SettingsKeys settingsKey = (SettingsKeys) value;
        return settingsKey.getSetting();
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.ULong;
import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.UShort;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.nio.charset.StandardCharsets;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author mach
 */
public class RdpSettings {
    /* Encryption Methods */
    public static final int ENCRYPTION_METHOD_NONE = 0X00000000;
    public static final int ENCRYPTION_METHOD_40BIT = 0X00000001;
    public static final int ENCRYPTION_METHOD_128BIT = 0X00000002;
    public static final int ENCRYPTION_METHOD_56BIT = 0X00000008;
    public static final int ENCRYPTION_METHOD_FIPS = 0X00000010;

    /* ENCRYPTION LEVELS */
    public static final int ENCRYPTION_LEVEL_NONE = 0X00000000;
    public static final int ENCRYPTION_LEVEL_LOW = 0X00000001;
    public static final int ENCRYPTION_LEVEL_CLIENT_COMPATIBLE = 0X00000002;
    public static final int ENCRYPTION_LEVEL_HIGH = 0X00000003;
    public static final int ENCRYPTION_LEVEL_FIPS = 0X00000004;

    private final Pointer handle;

    RdpSettings(Pointer handle) {
        this.handle = handle;
    }
    
    public boolean areValid() {
        return freerdp_settings_are_valid(handle);
    }
    
    public boolean getBoolean(SettingsKeys.Bool index) {
        return freerdp_settings_get_bool(handle, index);
    }
    
    public boolean setBoolean(SettingsKeys.Bool index, boolean value) {
        return freerdp_settings_set_bool(handle, index, value);
    }
    
    public short getInt16(SettingsKeys.Int16 index) {
        return freerdp_settings_get_int16(handle, index);
    }
    
    public boolean setInt16(SettingsKeys.Int16 index, short value) {
        return freerdp_settings_set_int16(handle, index, value);
    }
    
    public int getInt32(SettingsKeys.Int32 index) {
        return freerdp_settings_get_int32(handle, index);
    }
    
    public boolean setInt32(SettingsKeys.Int32 index, int value) {
        return freerdp_settings_set_int32(handle, index, value);
    }
    
    public long getInt64(SettingsKeys.Int64 index) {
        return freerdp_settings_get_int64(handle, index);
    }
    
    public boolean setInt64(SettingsKeys.Int64 index, long value) {
        return freerdp_settings_set_int64(handle, index, value);
    }
    
    public Pointer getPointer(SettingsKeys.Pointer index) {
        return freerdp_settings_get_pointer(handle, index);
    }
    
    public boolean setPointer(SettingsKeys.Pointer index, Pointer value) {
        return freerdp_settings_set_pointer(value, index, value);
    }
    
    public String getString(SettingsKeys.String index) {
        Pointer result = freerdp_settings_get_string(handle, index);
        return result.getString(0, StandardCharsets.UTF_8.name());
    }
    
    public boolean setString(SettingsKeys.String index, String value) {
        byte[] valueBytes = Native.toByteArray(value, StandardCharsets.UTF_8);
        return freerdp_settings_set_string(handle, index, valueBytes);
    }
    
    public boolean setString(SettingsKeys.String index, char[] value) {
        byte[] valueBytes = Utils.toBytes(value);
        return freerdp_settings_set_string(handle, index, valueBytes);
    }
    
    public int getUInt16(SettingsKeys.UInt16 index) {
        UShort result = freerdp_settings_get_uint16(handle, index);
        return result.intValue();
    }
    
    public boolean setUInt16(SettingsKeys.UInt16 index, int value) {
        if (value < 0) {
            return false;
        }
        return freerdp_settings_set_uint16(handle, index, new UShort(value));
    }
    
    public long getUInt32(SettingsKeys.UInt32 index) {
        UInt result = freerdp_settings_get_uint32(handle, index);
        return result.longValue();
    }
    
    public boolean setUInt32(SettingsKeys.UInt32 index, long value) {
        if (value < 0) {
            return false;
        }
        return freerdp_settings_set_uint32(handle, index, new UInt(value));
    }
    
    public long getUInt64(SettingsKeys.UInt64 index) {
        ULong result = freerdp_settings_get_uint64(handle, index);
        return result.longValue();
    }
    
    public boolean setUInt64(SettingsKeys.UInt64 index, long value) {
        if (value < 0) {
            return false;
        }
        return freerdp_settings_set_uint64(handle, index, new ULong(value));
    }
}

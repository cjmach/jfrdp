/*
 * Licensed to the Apache Software Foundation (ASF) under one or more 
 * contributor license agreements.  See the NOTICE file distributed with this 
 * work for additional information regarding copyright ownership. The ASF 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 * 
 *   https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the 
 * License for the specific language governing permissions and limitations
 * under the License.  
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.ULong;
import pt.cjmach.jfrdp.lib.types.SizeTByReference;
import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.SizeT;
import pt.cjmach.jfrdp.lib.types.UShort;
import com.sun.jna.Callback;
import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cmachado
 */
public class FreeRdpLibrary {

    static final String LIBRARY_NAME = "freerdp3";

    static {
        DefaultTypeMapper typeMapper = new DefaultTypeMapper();
        SettingsKeysConverter settingsKeyConverter = new SettingsKeysConverter();
        typeMapper.addToNativeConverter(SettingsKeys.Bool.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.Int16.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.Int32.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.Int64.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.Pointer.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.String.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.UInt16.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.UInt32.class, settingsKeyConverter);
        typeMapper.addToNativeConverter(SettingsKeys.UInt64.class, settingsKeyConverter);

        Map<String, Object> options = new HashMap<>();
        options.put(Library.OPTION_TYPE_MAPPER, typeMapper);
        NativeLibrary lib = NativeLibrary.getInstance(LIBRARY_NAME, options);
        Native.register(lib);
    }

    //
    // addin.h
    //
    public static native int freerdp_register_addin_provider(FreeRdpClientLibrary.LoadChannelAddinEntryFn provider, UInt dwFlags);

    //
    // freerdp.h
    //
    public static native boolean freerdp_connect(Pointer instance);

    public static native boolean freerdp_abort_connect_context(Pointer context);

    public static native boolean freerdp_shall_disconnect_context(Pointer context);

    public static native boolean freerdp_disconnect(Pointer instance);

    public static native boolean freerdp_reconnect(Pointer instance);

    public static native boolean freerdp_check_fds(Pointer instance);

    public static native UInt freerdp_get_event_handles(Pointer context, Pointer events, UInt count);

    public static native UInt freerdp_get_event_handles(Pointer context, long[] events, UInt count);

    public static native boolean freerdp_check_event_handles(Pointer context);

    public static native UInt freerdp_error_info(Pointer instance);

    public static native void freerdp_set_error_info(Pointer rdp, UInt error);

    public static native boolean freerdp_send_error_info(Pointer rdp);

    public static native boolean freerdp_get_stats(Pointer rdp, LongByReference inBytes, LongByReference outBytes, LongByReference inPackets, LongByReference outPackets);

    public static native void freerdp_get_version(IntByReference major, IntByReference minor, IntByReference revision);

    public static native Pointer freerdp_get_version_string();

    public static native Pointer freerdp_get_build_config();

    public static native void freerdp_free(Pointer instance);

    public static native boolean freerdp_focus_required(Pointer instance);

    public static native void freerdp_set_focus(Pointer instance);

    public static native int freerdp_get_disconnect_ultimatum(Pointer context);

    public static native UInt freerdp_get_last_error(Pointer context);

    public static native Pointer freerdp_get_last_error_name(UInt error);

    public static native Pointer freerdp_get_last_error_string(UInt error);

    public static native Pointer freerdp_get_last_error_category(UInt error);

    //
    // input.h
    //
    public static native boolean freerdp_input_send_keyboard_event_ex(Pointer input, boolean down, boolean repeat, UInt rdpScancode);
    
    public static native boolean freerdp_input_send_unicode_keyboard_event(Pointer input, UInt flags, UShort code);

    public static native boolean freerdp_input_send_mouse_event(Pointer input, UShort flags, UShort x, UShort y);

    public static native boolean freerdp_input_send_extended_mouse_event(Pointer input, UShort flags, UShort x, UShort y);

    public static native boolean freerdp_input_send_focus_in_event(Pointer input, UShort toggleStates);
    
    //
    // locale.h
    //
    public static native int freerdp_detect_keyboard_layout_from_system_locale(IntByReference keyboardLayoutId);
    // @since 3.14
    // public static native long freerdp_detect_keyboard_layout_from_locale(String localestr);
    public static native long freerdp_get_locale_id_from_string(String locale);    
    public static native UInt freerdp_get_keyboard_default_layout_for_locale(UInt locale);

    //
    // settings.h
    //
    public static native boolean freerdp_settings_are_valid(Pointer settings);

    public static native boolean freerdp_settings_get_bool(Pointer settings, SettingsKeys.Bool id);

    public static native boolean freerdp_settings_set_bool(Pointer settings, SettingsKeys.Bool id, boolean param);

    public static native short freerdp_settings_get_int16(Pointer settings, SettingsKeys.Int16 id);

    public static native boolean freerdp_settings_set_int16(Pointer settings, SettingsKeys.Int16 id, short param);

    public static native UShort freerdp_settings_get_uint16(Pointer settings, SettingsKeys.UInt16 id);

    public static native boolean freerdp_settings_set_uint16(Pointer settings, SettingsKeys.UInt16 id, UShort param);

    public static native int freerdp_settings_get_int32(Pointer settings, SettingsKeys.Int32 id);

    public static native boolean freerdp_settings_set_int32(Pointer settings, SettingsKeys.Int32 id, int param);

    public static native UInt freerdp_settings_get_uint32(Pointer settings, SettingsKeys.UInt32 id);

    public static native boolean freerdp_settings_set_uint32(Pointer settings, SettingsKeys.UInt32 id, UInt param);

    public static native long freerdp_settings_get_int64(Pointer settings, SettingsKeys.Int64 id);

    public static native boolean freerdp_settings_set_int64(Pointer settings, SettingsKeys.Int64 id, long param);

    public static native ULong freerdp_settings_get_uint64(Pointer settings, SettingsKeys.UInt64 id);

    public static native boolean freerdp_settings_set_uint64(Pointer settings, SettingsKeys.UInt64 id, ULong param);

    public static native Pointer freerdp_settings_get_string(Pointer settings, SettingsKeys.String id);

    public static native boolean freerdp_settings_set_string_len(Pointer settings, SettingsKeys.String id, byte[] param, SizeT len);

    public static native boolean freerdp_settings_set_string(Pointer settings, SettingsKeys.String id, byte[] param);

    public static native boolean freerdp_settings_set_string_from_utf16(Pointer settings, SettingsKeys.String id, byte[] param);

    public static native boolean freerdp_settings_set_string_from_utf16N(Pointer settings, SettingsKeys.String id, WString param, SizeT length);

    public static native boolean freerdp_settings_set_string_from_utf16N(Pointer settings, SettingsKeys.String id, byte[] param, SizeT length);

    public static native Pointer freerdp_settings_get_string_as_utf16(Pointer settings, SettingsKeys.String id, SizeTByReference pCharLen);

    public static native Pointer freerdp_settings_get_pointer(Pointer settings, SettingsKeys.Pointer id);

    public static native boolean freerdp_settings_set_pointer(Pointer settings, SettingsKeys.Pointer id, Pointer data);

    public static native boolean freerdp_settings_set_value_for_name(Pointer settings, String name, String value);

    public static native Pointer freerdp_settings_get_key_for_name(String value);

    public static native Pointer freerdp_settings_get_type_for_name(String value);

    public static native Pointer freerdp_settings_get_type_for_key(Pointer key);

    public static native Pointer freerdp_settings_get_type_name_for_key(Pointer key);

    public static native Pointer freerdp_settings_get_type_name_for_type(Pointer type);

    public static native Pointer freerdp_settings_get_name_for_key(Pointer key);

    public static native UInt freerdp_settings_get_codecs_flags(Pointer settings);

    public static native boolean freerdp_settings_update_from_caps(Pointer settings, Pointer capsFlags, Pointer capsData, Pointer capsSizes, UInt capsCount, boolean serverReceivedCaps);

    public static native Pointer freerdp_settings_get_server_name(Pointer settings);

    public static native Pointer freerdp_rail_support_flags_to_string(UInt flags, Pointer buffer, SizeT length);

    public static native Pointer freerdp_rdp_version_string(UInt version);

    public static native Pointer freerdp_rdpdr_dtyp_string(UInt type);

    public static native Pointer freerdp_encryption_level_string(UInt EncryptionLevel);

    public static native Pointer freerdp_encryption_methods_string(UInt EncryptionLevel, Pointer buffer, SizeT size);

    public static native Pointer freerdp_supported_color_depths_string(UShort mask, Pointer buffer, SizeT size);

    public static native Pointer freerdp_settings_get_config_path();
    
    //
    // codec/color.h
    // 
    public static native boolean freerdp_image_copy_from_pointer_data(
            byte[] pDstData, UInt DstFormat, UInt nDstStep, UInt nXDst,
            UInt nYDst, UInt nWidth, UInt nHeight, Pointer xorMask,
            UInt xorMaskLength, Pointer andMask, UInt andMaskLength,
            UInt xorBpp, Pointer palette);

    //
    // gdi.h
    //
    public static native boolean gdi_resize(Pointer gdi, UInt width, UInt height);

    public static native boolean gdi_init(Pointer instance, UInt format);

    public static native void gdi_free(Pointer instance);

    //
    // gdi/gfx.h
    //
    public static native boolean gdi_graphics_pipeline_init(Pointer gdi, Pointer gfx);

    public static native void gdi_graphics_pipeline_uninit(Pointer gdi, Pointer gfx);

    //
    // gdi/video.h
    //
    public static native void gdi_video_geometry_init(Pointer gdi, Pointer geom);

    public static native void gdi_video_geometry_uninit(Pointer gdi, Pointer geom);

    public static native void gdi_video_control_init(Pointer gdi, Pointer video);

    public static native void gdi_video_control_uninit(Pointer gdi, Pointer video);

    public static native void gdi_video_data_init(Pointer gdi, Pointer video);

    public static native void gdi_video_data_uninit(Pointer gdi, Pointer context);

    //
    // graphics.h
    //
    public static native void graphics_register_pointer(Pointer graphics, Pointer pointer);

    public static interface pContextNew extends Callback {

        boolean invoke(Pointer instance, Pointer context);
    }

    public static interface pContextFree extends Callback {

        void invoke(Pointer instance, Pointer context);
    }

    public static interface pConnectCallback extends Callback {

        boolean invoke(Pointer instance);
    }

    public static interface pPostDisconnect extends Callback {

        void invoke(Pointer instance);
    }

    public static interface pAuthenticate extends Callback {

        boolean invoke(Pointer instance, PointerByReference username, PointerByReference password, PointerByReference domain);
    }

    public static interface pAuthenticateEx extends Callback {

        boolean invoke(Pointer instance, PointerByReference username, PointerByReference password, PointerByReference domain, int reason);
    }

    public static interface pChooseSmartcard extends Callback {

        boolean invoke(Pointer instance, Pointer certList, UInt count, IntByReference choice, boolean gateway);
    }

    public enum AccessTokenType {
        Aad, /**
         * oauth2 access token for RDS AAD authentication
         */
        Avd
        /**
         * oauth2 access token for Azure Virtual Desktop
         */
    }

    // [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    // [return: MarshalAs(UnmanagedType.Bool)]
    // internal delegate bool pGetAccessToken(Pointer instance, AccessTokenType tokenType, Pointer token, UPointer count, ...);
    // [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    // [return: MarshalAs(UnmanagedType.Bool)]
    // internal delegate bool pGetCommonAccessToken(Pointer context, AccessTokenType tokenType, Pointer token, UPointer count, ...);
    public static interface pRetryDialog extends Callback {

        Pointer invoke(Pointer instance, String what, SizeT current, Pointer userarg);
    }

    public static interface pVerifyCertificateEx extends Callback {

        UInt invoke(Pointer instance, String host, UShort port, String commonName,
                String subject, String issuer, String fingerprint, UInt flags);
    }

    public static interface pVerifyChangedCertificateEx extends Callback {

        UInt invoke(Pointer instance, String host, UShort port, String commonName,
                String subject, String issuer, String new_fingerprint, String oldSubject,
                String oldIssuer, String oldFingerprint, UInt flags);
    }

    public static interface pVerifyX509Certificate extends Callback {

        int invoke(Pointer instance, Pointer data, SizeT length, String hostname, UShort port, UInt flags);
    }

    public static interface pLogonErrorInfo extends Callback {

        int invoke(Pointer instance, UInt data, UInt type);
    }

    public static interface pSendChannelData extends Callback {

        boolean invoke(Pointer instance, UShort channelId, Pointer data, SizeT size);
    }

    public static interface pSendChannelPacket extends Callback {

        boolean invoke(Pointer instance, UShort channelId, SizeT totalSize, UInt flags, Pointer data, SizeT chunkSize);
    }

    public static interface pReceiveChannelData extends Callback {

        boolean invoke(Pointer instance, UShort channelId, Pointer data, SizeT size, UInt flags, SizeT totalSize);
    }

    public static interface pPresentGatewayMessage extends Callback {

        boolean invoke(Pointer instance, UInt type, boolean isDisplayMandatory, boolean isConsentMandatory, SizeT length, String message);
    }

    public static interface pBeginPaint extends Callback {

        boolean invoke(Pointer context);
    }

    public static interface pEndPaint extends Callback {

        boolean invoke(Pointer context);
    }

    public static interface pSetBounds extends Callback {

        boolean invoke(Pointer context, Pointer bounds);
    }

    public static interface pSynchronize extends Callback {

        boolean invoke(Pointer context);
    }

    public static interface pDesktopResize extends Callback {

        boolean invoke(Pointer context);
    }

    public static interface pBitmapUpdate extends Callback {

        boolean invoke(Pointer context, Pointer bitmap);
    }

    public static interface pPalette extends Callback {

        boolean invoke(Pointer context, Pointer palette);
    }

    public static interface pPlaySound extends Callback {

        boolean invoke(Pointer context, Pointer playSound);
    }

    public static interface pSetKeyboardIndicators extends Callback {

        boolean invoke(Pointer context, UShort ledFlags);
    }

    public static interface pRefreshRect extends Callback {

        boolean invoke(Pointer context, byte count, Pointer areas);
    }

    public static interface pSuppressOutput extends Callback {

        boolean invoke(Pointer context, byte allow, Pointer area);
    }

    public static interface pRemoteMonitors extends Callback {

        boolean invoke(Pointer context, UInt count, Pointer monitors);
    }

    public static interface pSurfaceCommand extends Callback {

        boolean invoke(Pointer context, Pointer s);
    }

    public static interface pSurfaceBits extends Callback {

        boolean invoke(Pointer context, Pointer surfaceBitsCommand);
    }

    public static interface pSurfaceFrameMarker extends Callback {

        boolean invoke(Pointer context, Pointer surfaceFrameMarker);
    }

    public static interface pSurfaceFrameBits extends Callback {

        boolean invoke(Pointer context, Pointer cmd, boolean first, boolean last, UInt frameId);
    }

    public static interface pSurfaceFrameAcknowledge extends Callback {

        boolean invoke(Pointer context, UInt frameId);
    }

    public static interface pSaveSessionInfo extends Callback {

        boolean invoke(Pointer context, UInt type, Pointer data);
    }

    public static interface pSetKeyboardImeStatus extends Callback {

        boolean invoke(Pointer context, UShort imeId, UInt imeState, UInt imeConvMode);
    }

    public static interface pServerStatusInfo extends Callback {

        boolean invoke(Pointer context, UInt status);
    }

    /* Pointer Class */
    public static interface pPointerNew extends Callback {

        boolean invoke(Pointer context, Pointer pointer);
    }

    public static interface pPointerFree extends Callback {

        void invoke(Pointer context, Pointer pointer);
    }

    public static interface pPointerSet extends Callback {

        boolean invoke(Pointer context, Pointer pointer);
    }

    public static interface pPointerSetNull extends Callback {

        boolean invoke(Pointer context);
    }

    public static interface pPointerSetDefault extends Callback {

        boolean invoke(Pointer context);
    }

    public static interface pPointerSetPosition extends Callback {

        boolean invoke(Pointer context, UInt x, UInt y);
    }
}

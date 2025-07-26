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

import pt.cjmach.jfrdp.lib.types.UInt;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.ptr.IntByReference;
import java.util.Locale;

import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author cmachado
 */
@FieldOrder({"context", "pClientEntryPoints", "paddingA", "paddingX", "heartbeat",
    "paddingB", "ContextSize", "ContextNew", "ContextFree", "paddingC",
    "ConnectionCallbackState", "PreConnect", "PostConnect", "Authenticate",
    "reserved", "VerifyX509Certificate", "LogonErrorInfo", "PostDisconnect",
    "GatewayAuthenticate", "PresentGatewayMessage", "Redirect", "LoadChannels",
    "PostFinalDisconnect", "paddingD", "SendChannelData", "ReceiveChannelData",
    "VerifyCertificateEx", "VerifyChangedCertificateEx", "SendChannelPacket",
    "AuthenticateEx", "ChooseSmartcard", "GetAccessToken", "RetryDialog",
    "paddingE"})
public class FreeRdp extends Structure {

    private static final String CLIPRDR_SVC_CHANNEL_NAME = "cliprdr";
    private static final String DISP_DVC_CHANNEL_NAME = "Microsoft::Windows::RDS::DisplayControl";
    private static final String DRDYNVC_SVC_CHANNEL_NAME = "drdynvc";
    private static final String ENCOMSP_SVC_CHANNEL_NAME = "encomsp";
    private static final String GEOMETRY_CHANNEL_NAME = "geometry";
    private static final String GEOMETRY_DVC_CHANNEL_NAME = "Microsoft::Windows::RDS::Geometry::v08.01";
    private static final String RAIL_SVC_CHANNEL_NAME = "rail";
    private static final String RDPEI_DVC_CHANNEL_NAME = "Microsoft::Windows::RDS::Input";
    private static final String RDPGFX_CHANNEL_NAME = "rdpgfx";
    private static final String RDPGFX_DVC_CHANNEL_NAME = "Microsoft::Windows::RDS::Graphics";
    private static final String TSMF_DVC_CHANNEL_NAME = "TSMF";
    private static final String VIDEO_CHANNEL_NAME = "video";
    private static final String VIDEO_CONTROL_DVC_CHANNEL_NAME = "Microsoft::Windows::RDS::Video::Control::v08.01";
    private static final String VIDEO_DATA_DVC_CHANNEL_NAME = "Microsoft::Windows::RDS::Video::Data::v08.01";

    public Pointer context;
    public Pointer pClientEntryPoints;

    public long[] paddingA = new long[16 - 2];
    public long[] paddingX = new long[4];

    public Pointer heartbeat;

    public long[] paddingB = new long[32 - 21];

    public UInt ContextSize;
    public pContextNew ContextNew;
    public pContextFree ContextFree;

    public long[] paddingC = new long[47 - 35];

    public UInt ConnectionCallbackState;
    public pConnectCallback PreConnect;
    public pConnectCallback PostConnect;
    public pAuthenticate Authenticate;

    public long[] reserved = new long[2];

    public pVerifyX509Certificate VerifyX509Certificate;
    public pLogonErrorInfo LogonErrorInfo;
    public pPostDisconnect PostDisconnect;
    public pAuthenticate GatewayAuthenticate;
    public pPresentGatewayMessage PresentGatewayMessage;
    public pConnectCallback Redirect;
    public pConnectCallback LoadChannels;
    public pPostDisconnect PostFinalDisconnect;

    public long[] paddingD = new long[64 - 61];

    public pSendChannelData SendChannelData;
    public pReceiveChannelData ReceiveChannelData;
    public pVerifyCertificateEx VerifyCertificateEx;
    public pVerifyChangedCertificateEx VerifyChangedCertificateEx;
    public pSendChannelPacket SendChannelPacket;
    public pAuthenticateEx AuthenticateEx;
    public pChooseSmartcard ChooseSmartcard;
    public Pointer GetAccessToken;
    public Pointer RetryDialog;

    public long[] paddingE = new long[80 - 73];


    private final RdpContext rdpContext;
    private final WinPRLibrary.ChannelConnectedEventHandler channelConnectedEventHandler;
    private final WinPRLibrary.ChannelDisconnectedEventHandler channelDisconnectedEventHandler;
    private ChannelDisplayControl channelDisplayControl;
    private Runnable displayControlCapsSet;

    private final pConnectCallback preConnect;
    private final pPostDisconnect postDisconnect;
    private pPostDisconnect userPostDisconnect;

    private static final FreeRdpClientLibrary.LoadChannelAddinEntryFn LOAD_CHANNEL_FN;

    static {
        LOAD_CHANNEL_FN = FreeRdpClientLibrary::freerdp_channels_load_static_addin_entry;
        freerdp_register_addin_provider(LOAD_CHANNEL_FN, new UInt(0));
    }

    public FreeRdp(Pointer instance, Pointer context) {
        super(instance);
        read();

        channelConnectedEventHandler = this::onChannelConnected;
        channelDisconnectedEventHandler = this::onChannelDisconnected;

        rdpContext = new RdpContext(context);

        preConnect = this::onPreConnect;
        postDisconnect = this::onPostDisconnect;
        writeField("PreConnect", preConnect);
        writeField("PostDisconnect", postDisconnect);
    }

    public boolean connect() {
        return freerdp_connect(getPointer());
    }

    public boolean disconnect() {
        return freerdp_disconnect(getPointer());
    }

    public void gdiFree() {
        gdi_free(getPointer());
    }

    public boolean gdiInit(PixelFormat format) {
        return gdi_init(getPointer(), new UInt(format.getValue()));
    }

    public RdpContext getContext() {
        return rdpContext;
    }

    public ChannelDisplayControl getDisplayControl() {
        return channelDisplayControl;
    }

    @Override
    protected int getNativeAlignment(Class<?> type, Object value, boolean isFirstElement) {
        return Long.BYTES;
    }

    private void onChannelConnected(Pointer context, ChannelConnectedEventArgs e) {
        String name = e.getName();
        switch (name) {
            case DISP_DVC_CHANNEL_NAME:
                channelDisplayControl = new ChannelDisplayControl(e.getInterface());
                channelDisplayControl.setCapsSet(this::onDisplayControlCapsSet);
                break;

            case RDPGFX_DVC_CHANNEL_NAME:
                rdpContext.getGdi().graphicsPipelineInit(e.getInterface());
                break;

            case GEOMETRY_DVC_CHANNEL_NAME:
                rdpContext.getGdi().videoGeometryInit(e.getInterface());
                break;

            case VIDEO_CONTROL_DVC_CHANNEL_NAME:
                rdpContext.getGdi().videoControlInit(e.getInterface());
                break;

            case VIDEO_DATA_DVC_CHANNEL_NAME:
                rdpContext.getGdi().videoDataInit(e.getInterface());
                break;

            case RDPEI_DVC_CHANNEL_NAME:
            // TODO: Touch input redirection
            case TSMF_DVC_CHANNEL_NAME:
            // TODO: Old windows 7 multimedia redirection
            case RAIL_SVC_CHANNEL_NAME:
            // TODO: remote application
            case CLIPRDR_SVC_CHANNEL_NAME:
            case ENCOMSP_SVC_CHANNEL_NAME:
            // TODO: Multiparty channel
            default:
                rdpContext.onChannelConnected(e);
                break;
        }
    }

    private void onChannelDisconnected(Pointer context, ChannelDisconnectedEventArgs e) {
        String name = e.getName();
        switch (name) {
            case DISP_DVC_CHANNEL_NAME:
                channelDisplayControl = null;
                break;

            case RDPGFX_DVC_CHANNEL_NAME:
                rdpContext.getGdi().graphicsPipelineUninit(e.getInterface());
                break;

            case GEOMETRY_DVC_CHANNEL_NAME:
                rdpContext.getGdi().videoGeometryUninit(e.getInterface());
                break;

            case VIDEO_CONTROL_DVC_CHANNEL_NAME:
                rdpContext.getGdi().videoControlUninit(e.getInterface());
                break;

            case VIDEO_DATA_DVC_CHANNEL_NAME:
                rdpContext.getGdi().videoDataUninit(e.getInterface());
                break;

            case RDPEI_DVC_CHANNEL_NAME:
            // TODO: Touch input redirection
            case TSMF_DVC_CHANNEL_NAME:
            // TODO: Old windows 7 multimedia redirection
            case RAIL_SVC_CHANNEL_NAME:
            // TODO: remote application
            case CLIPRDR_SVC_CHANNEL_NAME:
            case ENCOMSP_SVC_CHANNEL_NAME:
            // TODO: Multiparty channel
            default:
                rdpContext.onChannelDisconnected(e);
                break;
        }
    }

    private void onDisplayControlCapsSet() {
        if (displayControlCapsSet != null) {
            displayControlCapsSet.run();
        }
    }

    private boolean onPreConnect(Pointer instance) {
        PubSub pubSub = rdpContext.getPubSub();
        pubSub.subscribe(channelConnectedEventHandler);
        pubSub.subscribe(channelDisconnectedEventHandler);
        return true;
    }

    private void onPostDisconnect(Pointer instance) {
        PubSub pubSub = rdpContext.getPubSub();
        pubSub.unsubscribe(channelConnectedEventHandler);
        pubSub.unsubscribe(channelDisconnectedEventHandler);
        if (userPostDisconnect != null) {
            userPostDisconnect.invoke(instance);
        }
    }

    public void setDisplayControlCapsSet(Runnable capsSet) {
        this.displayControlCapsSet = capsSet;
    }

    public void setPostConnect(pConnectCallback postConnect) {
        writeField("PostConnect", postConnect);
    }

    public void setPostDisconnect(pPostDisconnect postDisconnect) {
        this.userPostDisconnect = postDisconnect;
    }

    public static String getBuildConfig() {
        Pointer result = freerdp_get_build_config();
        return result.getString(0);
    }

    static int detectKeyboardLayoutFromSystemLocale() {
        IntByReference layoutId = new IntByReference();
        int result = freerdp_detect_keyboard_layout_from_system_locale(layoutId);
        if (result != 0) {
            return -1;
        }
        return layoutId.getValue();
    }

    public static int detectKeyboardLayout(Locale locale) {
        String tag = locale.toLanguageTag().replace('-', '_');
        // long result = freerdp_detect_keyboard_layout_from_locale(tag);
        long localeId = freerdp_get_locale_id_from_string(tag);
        if (localeId <= 0) {
            return detectKeyboardLayoutFromSystemLocale();
        }
        UInt layout = freerdp_get_keyboard_default_layout_for_locale(new UInt(localeId));
        int result = layout.intValue();
        if (result == 0) {
            return detectKeyboardLayoutFromSystemLocale();
        }
        return result;
    }

    public static int detectKeyboardLayout() {
        return detectKeyboardLayout(Locale.getDefault());
    }
}

package pt.cjmach.jfrdp.ui;

import com.sun.jna.Pointer;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferShort;
import java.awt.image.Raster;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import pt.cjmach.jfrdp.lib.FreeRdp;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;
import pt.cjmach.jfrdp.lib.GdiDC;
import pt.cjmach.jfrdp.lib.GdiRgn;
import pt.cjmach.jfrdp.lib.GdiWnd;
import pt.cjmach.jfrdp.lib.PixelFormat;
import pt.cjmach.jfrdp.lib.RdpClient;
import pt.cjmach.jfrdp.lib.RdpContext;
import pt.cjmach.jfrdp.lib.RdpGdi;
import pt.cjmach.jfrdp.lib.RdpInput;
import pt.cjmach.jfrdp.lib.RdpPointer;
import pt.cjmach.jfrdp.lib.RdpSettings;
import pt.cjmach.jfrdp.lib.RdpUpdate;
import pt.cjmach.jfrdp.lib.ResizeWindowChangeEventArgs;
import pt.cjmach.jfrdp.lib.SettingsKeys;

/**
 *
 * @author mach
 */
public class RdpDisplay extends JPanel {

    private static Pattern RAW_CODE_PATTERN = Pattern.compile(".+rawCode=(\\d+).+");
    
    private final RdpClient client;
    private final FreeRdp freeRdp;
    private BufferedImage surface;
    private int surfaceFormat;
    private int surfaceNumPixelComponents;
    private boolean scaling = false;
    private double scale = 1.0;
    private boolean connected;
    private double offsetX = 0.0;
    private double offsetY = 0.0;

    private RdpPointer pointer;
    private final Map<Pointer, Cursor> cachedCursors;
    
    private final pConnectCallback postConnect;
    private final pPostDisconnect postDisconnect;
    private pBeginPaint beginPaint;
    private pEndPaint endPaint;
    private pDesktopResize desktopResize;
    private pPointerNew pointerNew;
    private pPointerFree pointerFree;
    private pPointerSet pointerSet;
    private pPointerSetDefault pointerSetDefault;
    private pPointerSetNull pointerSetNull;
    
    private ComponentListener parentComponentListener;

    public RdpDisplay() {
        this(createClient());
    }

    private RdpDisplay(RdpClient client) {
        this.client = client;
        this.freeRdp = client.getFreeRdp();

        this.postConnect = this::onPostConnect;
        this.postDisconnect = this::onPostDisconnect;
        this.beginPaint = this::onBeginPaint;
        this.endPaint = this::onEndPaint;
        this.desktopResize = this::onDesktopResize;
        this.pointerNew = this::onPointerNew;
        this.pointerFree = this::onPointerFree;
        this.pointerSet = this::onPointerSet;
        this.pointerSetDefault = this::onPointerSetDefault;
        this.pointerSetNull = this::onPointerSetNull;

        freeRdp.setDisplayControlCapsSet(() -> {
            setDesktopResizeSupported(true);
        });
        freeRdp.setPostConnect(this.postConnect);
        freeRdp.setPostDisconnect(this.postDisconnect);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setOpaque(true);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                onEnter();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                onKeyPressed(ke);
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                onKeyReleased(ke);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                onKeyTyped(e);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                onMouseButtonEvent(me, true);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                onMouseButtonEvent(me, false);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                onMouseEvent(RdpInput.MOUSE_MOVE, me.getX(), me.getY());
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                onMouseEvent(RdpInput.MOUSE_MOVE, me.getX(), me.getY());
            }
        });
        addMouseWheelListener((MouseWheelEvent mwe) -> {
            onMouseScroll(mwe);
        });

        cachedCursors = new HashMap<>();
    }

    boolean allowLowLevelKeyEvent(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_ALT:
            case KeyEvent.VK_ALT_GRAPH:
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_CANCEL:
            case KeyEvent.VK_CLEAR:
            case KeyEvent.VK_CONTEXT_MENU:
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_END:
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_F1:
            case KeyEvent.VK_F2:
            case KeyEvent.VK_F3:
            case KeyEvent.VK_F4:
            case KeyEvent.VK_F5:
            case KeyEvent.VK_F6:
            case KeyEvent.VK_F7:
            case KeyEvent.VK_F8:
            case KeyEvent.VK_F9:
            case KeyEvent.VK_F10:
            case KeyEvent.VK_F11:
            case KeyEvent.VK_F12:
            case KeyEvent.VK_F13:
            case KeyEvent.VK_F14:
            case KeyEvent.VK_F15:
            case KeyEvent.VK_F16:
            case KeyEvent.VK_F17:
            case KeyEvent.VK_F18:
            case KeyEvent.VK_F19:
            case KeyEvent.VK_F20:
            case KeyEvent.VK_F21:
            case KeyEvent.VK_F22:
            case KeyEvent.VK_F23:
            case KeyEvent.VK_F24:
            case KeyEvent.VK_HELP:
            case KeyEvent.VK_HOME:
            case KeyEvent.VK_INSERT:
            case KeyEvent.VK_KANA:
            case KeyEvent.VK_KP_DOWN:
            case KeyEvent.VK_KP_LEFT:
            case KeyEvent.VK_KP_RIGHT:
            case KeyEvent.VK_KP_UP:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_META:
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_PAUSE:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_SHIFT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_WINDOWS:
                return true;
                
            default:
                return false;
        }
    }

    private void createSurface() {
        RdpGdi gdi = freeRdp.getContext().getGdi();
        Dimension size = new Dimension(gdi.getWidth(), gdi.getHeight());
        setPreferredSize(size);
        setSize(size);

        Pointer primaryBuffer = gdi.getPrimaryBuffer();
        if (primaryBuffer == Pointer.NULL) {
            return;
        }

        DataBuffer dataBuffer;
        int bufferLength = gdi.getWidth() * gdi.getHeight() * surfaceNumPixelComponents;
        if (surfaceNumPixelComponents == Short.BYTES) {
            // we must create a DataBufferShort when surfaceNumPixelComponents == 2.
            // For other cases we can use a DataBufferByte.
            short[] buffer = new short[bufferLength / Short.BYTES];
            primaryBuffer.read(0, buffer, 0, buffer.length);
            dataBuffer = new DataBufferShort(buffer, buffer.length);
        } else {
            byte[] buffer = new byte[bufferLength];
            primaryBuffer.read(0, buffer, 0, bufferLength);
            dataBuffer = new DataBufferByte(buffer, bufferLength);
        }
        surface = new BufferedImage(gdi.getWidth(), gdi.getHeight(), surfaceFormat);
        surface.setData(Raster.createRaster(surface.getSampleModel(), dataBuffer, new Point()));
    }

    public boolean disconnect() {
        return client.getContext().abortConnect();
    }

    public int connect() {
        return client.start();
    }

    public boolean isDesktopResizeEnabled() {
        return getSettings().getBoolean(SettingsKeys.Bool.DESKTOP_RESIZE);
    }

    public void setDeskopResizeEnabled(boolean enabled) {
        getSettings().setBoolean(SettingsKeys.Bool.DESKTOP_RESIZE, enabled);
    }

    public int getColorDepth() {
        return (int) getSettings().getUInt32(SettingsKeys.UInt32.COLOR_DEPTH);
    }

    public void setColorDepth(int depth) {
        getSettings().setUInt32(SettingsKeys.UInt32.COLOR_DEPTH, depth);
    }

    public RdpSettings getSettings() {
        return client.getContext().getSettings();
    }

    boolean onBeginPaint(Pointer context) {
        GdiDC dc = freeRdp.getContext().getGdi().getPrimary().getDC();
        if (dc == null) {
            return false;
        }
        GdiWnd wnd = dc.getWnd();
        if (wnd == null) {
            return false;
        }
        wnd.getInvalid().setNull(true);
        wnd.setNInvalid(0);
        return true;
    }

    boolean onDesktopResize(Pointer context) {
        RdpContext rdpContext = freeRdp.getContext();
        RdpSettings settings = rdpContext.getSettings();
        if (rdpContext.getGdi().resize(
                settings.getUInt32(SettingsKeys.UInt32.DESKTOP_WIDTH),
                settings.getUInt32(SettingsKeys.UInt32.DESKTOP_HEIGHT))) {
            createSurface();
            return true;
        }
        return false;
    }

    boolean onEndPaint(Pointer context) {
        GdiDC dc = freeRdp.getContext().getGdi().getPrimary().getDC();
        if (dc == null) {
            return false;
        }
        GdiWnd wnd = dc.getWnd();
        if (wnd == null) {
            return false;
        }
        GdiRgn[] cinvalid = wnd.getCInvalid();
        for (GdiRgn rgn : cinvalid) {
            if (!rgn.isNull() && rgn.getW() > 0 && rgn.getH() > 0) {
                repaint(rgn.getX(), rgn.getY(), rgn.getW(), rgn.getH());
            }
        }
        return true;
    }

    boolean onEnter() {
        if (!connected) {
            return true;
        }

        Toolkit kit = Toolkit.getDefaultToolkit();
        int syncFlags = 0;
        if (kit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            syncFlags |= RdpInput.KEYBOARD_SYNC_CAPS_LOCK;
        }
        if (kit.getLockingKeyState(KeyEvent.VK_NUM_LOCK)) {
            syncFlags |= RdpInput.KEYBOARD_SYNC_NUM_LOCK;
        }
        if (kit.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK)) {
            syncFlags |= RdpInput.KEYBOARD_SYNC_SCROLL_LOCK;
        }
        if (kit.getLockingKeyState(KeyEvent.VK_KANA_LOCK)) {
            syncFlags |= RdpInput.KEYBOARD_SYNC_KANA_LOCK;
        }
        return freeRdp.getContext().getInput().sendFocusInEvent(syncFlags);
    }

    boolean onKeyPressed(KeyEvent ke) {
        if (!connected) {
            return true;
        }

        if (allowLowLevelKeyEvent(ke.getKeyCode())) {
            int rawCode = getKeyEventRawCode(ke);
            return freeRdp.getContext().getInput().sendKey(rawCode, true);
        }
        return true;
    }

    boolean onKeyReleased(KeyEvent ke) {
        if (!connected) {
            return true;
        }

        if (allowLowLevelKeyEvent(ke.getKeyCode())) {
            int rawCode = getKeyEventRawCode(ke);
            return freeRdp.getContext().getInput().sendKey(rawCode, false);
        }
        return true;
    }

    boolean onKeyTyped(KeyEvent e) {
        if (!connected) {
            return true;
        }

        RdpInput input = freeRdp.getContext().getInput();
        int keyChar = e.getKeyChar();
        switch (keyChar) {
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_ESCAPE:
                break;

            default:
                return input.sendUnicodeKey(true, keyChar)
                        && input.sendUnicodeKey(false, keyChar);
        }
        return true;
    }

    boolean onMouseButtonEvent(MouseEvent e, boolean down) {
        if (!connected) {
            return true;
        }

        int flags = 0;
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                flags |= RdpInput.MOUSE_BUTTON1;
                break;

            case MouseEvent.BUTTON2:
                flags |= RdpInput.MOUSE_BUTTON3;
                break;

            case MouseEvent.BUTTON3:
                flags |= RdpInput.MOUSE_BUTTON2;
                break;

            default:
                return false;
        }
        if (down) {
            flags |= RdpInput.MOUSE_DOWN;
        }
        return onMouseEvent(flags, e.getX(), e.getY());
    }

    boolean onMouseEvent(int e, int x, int y) {
        return freeRdp.getContext().getInput().sendMouseEvent(e, x, y, scaling, scale, offsetX, offsetY);
    }

    boolean onMouseScroll(MouseWheelEvent e) {
        if (!connected) {
            return true;
        }

        int flags = RdpInput.MOUSE_WHEEL;
        if (e.getWheelRotation() > 0) {
            flags |= RdpInput.MOUSE_WHEEL_NEGATIVE;
        }
        return onMouseEvent(flags, e.getX(), e.getY());
    }

    void onParentComponentResized(ComponentEvent e) {
        RdpContext context = freeRdp.getContext();
        RdpGdi gdi = context.getGdi();
        if (gdi == null) {
            return;
        }
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        if (isDesktopResizeEnabled()) {
            RdpSettings settings = context.getSettings();
            if ((settings.getUInt32(SettingsKeys.UInt32.DESKTOP_WIDTH) != width
                    || settings.getUInt32(SettingsKeys.UInt32.DESKTOP_HEIGHT) != height)
                    && freeRdp.getDisplayControl() != null) {
                freeRdp.getDisplayControl().resizeDisplay(width, height);
            }
        }
    }

    boolean onPointerNew(Pointer context, Pointer pointer) {
        if (cachedCursors.containsKey(pointer)) {
            return true;
        }
        RdpPointer p = new RdpPointer(pointer);
        int width = p.getWidth();
        int height = p.getHeight();
        int size = width * height * Integer.BYTES;
        byte[] ptrData = new byte[size];
        PixelFormat format = PixelFormat.ABGR32;
        boolean result = p.copy(ptrData, format);
        if (result) {
            int x = p.getX();
            int y = p.getY();

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            DataBufferByte dataBuffer = new DataBufferByte(ptrData, size);
            image.setData(Raster.createRaster(image.getSampleModel(), dataBuffer, new Point()));
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(x, y), pointer.toString());
            cachedCursors.put(pointer, cursor);
        }
        return result;
    }

    void onPointerFree(Pointer context, Pointer pointer) {
        cachedCursors.remove(pointer);
    }

    boolean onPointerSet(Pointer context, Pointer pointer) {
        if (cachedCursors.containsKey(pointer)) {
            setCursor(cachedCursors.get(pointer));
        }
        return true;
    }

    boolean onPointerSetNull(Pointer context) {
        setCursor(Cursor.getDefaultCursor());
        return true;
    }

    boolean onPointerSetDefault(Pointer context) {
        setCursor(Cursor.getDefaultCursor());
        return true;
    }

    boolean onPostConnect(Pointer instance) {
        PixelFormat colorFormat;
        switch (getColorDepth()) {
            case 32:
                colorFormat = PixelFormat.ABGR32;
                surfaceFormat = BufferedImage.TYPE_4BYTE_ABGR;
                surfaceNumPixelComponents = Integer.BYTES;
                break;

            case 24:
                colorFormat = PixelFormat.BGR24;
                surfaceFormat = BufferedImage.TYPE_3BYTE_BGR;
                surfaceNumPixelComponents = 3;
                break;

            case 16:
                colorFormat = PixelFormat.RGB16;
                surfaceFormat = BufferedImage.TYPE_USHORT_565_RGB;
                surfaceNumPixelComponents = Short.BYTES;
                break;

            case 15:
                colorFormat = PixelFormat.RGB15;
                surfaceFormat = BufferedImage.TYPE_USHORT_555_RGB;
                surfaceNumPixelComponents = Short.BYTES;
                break;

            default:
                colorFormat = PixelFormat.BGR24;
                surfaceFormat = BufferedImage.TYPE_3BYTE_BGR;
                surfaceNumPixelComponents = 3;
                break;
        }
        if (!freeRdp.gdiInit(colorFormat)) {
            return false;
        }
        parentComponentListener = new ParentComponentListener();
        getParent().addComponentListener(parentComponentListener);

        RdpContext context = freeRdp.getContext();
        RdpUpdate update = context.getUpdate();
        update.setBeginPaint(this.beginPaint);
        update.setEndPaint(this.endPaint);
        update.setDesktopResize(this.desktopResize);

        RdpSettings settings = context.getSettings();
        ResizeWindowChangeEventArgs e = new ResizeWindowChangeEventArgs(
                (int) settings.getUInt32(SettingsKeys.UInt32.DESKTOP_WIDTH),
                (int) settings.getUInt32(SettingsKeys.UInt32.DESKTOP_HEIGHT)
        );
        context.getPubSub().onResizeWindow(freeRdp.getContext(), e);

        createSurface();

        pointer = new RdpPointer();
        pointer.Free = pointerFree;
        pointer.New = pointerNew;
        pointer.Set = pointerSet;
        pointer.SetNull = pointerSetNull;
        pointer.SetDefault = pointerSetDefault;
        context.registerPointer(pointer);

        connected = true;

        return true;
    }

    void onPostDisconnect(Pointer instance) {

        connected = false;

        freeRdp.gdiFree();
        getParent().removeComponentListener(parentComponentListener);
        parentComponentListener = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        RdpGdi gdi = freeRdp.getContext().getGdi();
        if (connected && gdi != null) {
            if (surface == null || gdi.getWidth() != surface.getWidth()
                    || gdi.getWidth() != surface.getHeight()) {
                createSurface();
            }
            g.drawImage(surface, 0, 0, this);
        }
    }

    private void setDesktopResizeSupported(boolean supported) {
        if (supported && isDesktopResizeEnabled()) {
            int width = getParent().getWidth();
            int height = getParent().getHeight();
            freeRdp.getDisplayControl().resizeDisplay(width, height);
        }
    }

    private static RdpClient createClient() {
        RdpClient newClient = new RdpClient();
        RdpContext context = newClient.getContext();
        RdpSettings settings = context.getSettings();

        /* Security settings */
        settings.setBoolean(SettingsKeys.Bool.RDP_SECURITY, true);
        settings.setBoolean(SettingsKeys.Bool.TLS_SECURITY, true);
        settings.setBoolean(SettingsKeys.Bool.NLA_SECURITY, true);
        settings.setUInt32(SettingsKeys.UInt32.ENCRYPTION_LEVEL, RdpSettings.ENCRYPTION_LEVEL_CLIENT_COMPATIBLE);
        settings.setBoolean(SettingsKeys.Bool.USE_RDP_SECURITY_LAYER, true);
        settings.setBoolean(SettingsKeys.Bool.NEGOTIATE_SECURITY_LAYER, true);

        settings.setBoolean(SettingsKeys.Bool.ALLOW_FONT_SMOOTHING, true);
        settings.setBoolean(SettingsKeys.Bool.DESKTOP_RESIZE, true);
        settings.setBoolean(SettingsKeys.Bool.DYNAMIC_RESOLUTION_UPDATE, true);
        settings.setBoolean(SettingsKeys.Bool.SUPPORT_DISPLAY_CONTROL, true);
        settings.setBoolean(SettingsKeys.Bool.REMOTE_FX_CODEC, true);
        settings.setUInt32(SettingsKeys.UInt32.COLOR_DEPTH, getToolkitPixelSize());
        settings.setBoolean(SettingsKeys.Bool.REDIRECT_CLIPBOARD, true);
        settings.setBoolean(SettingsKeys.Bool.REDIRECT_HOME_DRIVE, true);
        settings.setBoolean(SettingsKeys.Bool.SUPPORT_GRAPHICS_PIPELINE, true);
        settings.setBoolean(SettingsKeys.Bool.SOFTWARE_GDI, true);
        
        String buildConfig = FreeRdp.getBuildConfig();
        if (buildConfig.contains("WITH_GFX_H264=ON")) {
            settings.setBoolean(SettingsKeys.Bool.GFX_H264, true);
            settings.setBoolean(SettingsKeys.Bool.GFX_AVC444, true);
        } else {
            settings.setBoolean(SettingsKeys.Bool.GFX_H264, false);
            settings.setBoolean(SettingsKeys.Bool.GFX_AVC444, false);
        }
        
        int keyboardLayout = FreeRdp.detectKeyboardLayout();
        if (keyboardLayout > 0) {
            settings.setUInt32(SettingsKeys.UInt32.KEYBOARD_LAYOUT, keyboardLayout);
        }
        return newClient;
    }

    static int getToolkitPixelSize() {
        return Toolkit.getDefaultToolkit().getColorModel().getPixelSize();
    }

    static int getKeyEventRawCode(KeyEvent ke) {
        String str = ke.toString();
        Matcher matcher = RAW_CODE_PATTERN.matcher(str);
        if (matcher.matches()) {
            String rawCode = matcher.group(1);
            return Integer.parseInt(rawCode);
        }
        return 0;
    }

    class ParentComponentListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            onParentComponentResized(e);
        }
    }
}

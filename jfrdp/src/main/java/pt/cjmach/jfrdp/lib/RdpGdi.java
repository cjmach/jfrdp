/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.cjmach.jfrdp.lib;

import pt.cjmach.jfrdp.lib.types.UInt;
import pt.cjmach.jfrdp.lib.types.UShort;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import static pt.cjmach.jfrdp.lib.FreeRdpLibrary.*;

/**
 *
 * @author mach
 */
@FieldOrder({"context", "width", "height", "stride", "dstFormat", "cursor_x",
    "cursor_y", "hdc", "primary", "drawing", "bitmap_size", "bitmap_stride",
    "primary_buffer", "palette", "image", "free", "inGfxFrame", "graphicsReset",
    "suppressOutput", "outputSurfaceId", "frameId", "gfx", "video", "geometry",
    "log"})
public class RdpGdi extends Structure {

    public Pointer context;

    public int width;
    public int height;
    public UInt stride;
    public UInt dstFormat;
    public UInt cursor_x;
    public UInt cursor_y;

    public Pointer hdc;
    public Pointer primary;
    public Pointer drawing;
    public UInt bitmap_size;
    public UInt bitmap_stride;
    public Pointer primary_buffer;
    public GdiPalette palette;
    public Pointer image;
    public Pointer free;

    public boolean inGfxFrame;
    public boolean graphicsReset;
    public boolean suppressOutput;
    public UShort outputSurfaceId;
    public UInt frameId;
    public Pointer gfx;
    public Pointer video;
    public Pointer geometry;

    public Pointer log;

    private GdiBitmap primaryObj;

    public RdpGdi() {
    }

    public RdpGdi(Pointer gdi) {
        super(gdi);
        read();
    }

    public int getHeight() {
        return (int) readField("height");
    }

    public GdiBitmap getPrimary() {
        Pointer ptr = (Pointer) readField("primary");
        if (ptr == Pointer.NULL) {
            return null;
        }
        if (primaryObj == null || !primaryObj.getPointer().equals(ptr)) {
            primaryObj = new GdiBitmap(ptr);
        }
        return primaryObj;
    }

    public Pointer getPrimaryBuffer() {
        return (Pointer) readField("primary_buffer");
    }

    public int getWidth() {
        return (int) readField("width");
    }

    public boolean graphicsPipelineInit(Pointer gfx) {
        return gdi_graphics_pipeline_init(getPointer(), gfx);
    }

    public void graphicsPipelineUninit(Pointer gfx) {
        gdi_graphics_pipeline_uninit(getPointer(), gfx);
    }

    public boolean resize(long width, long height) {
        return gdi_resize(getPointer(), new UInt(width), new UInt(height));
    }

    public void videoControlInit(Pointer video) {
        gdi_video_control_init(getPointer(), video);
    }

    public void videoControlUninit(Pointer video) {
        gdi_video_control_uninit(getPointer(), video);
    }

    public void videoDataInit(Pointer video) {
        gdi_video_data_init(getPointer(), video);
    }

    public void videoDataUninit(Pointer video) {
        gdi_video_data_uninit(getPointer(), video);
    }

    public void videoGeometryInit(Pointer geom) {
        gdi_video_geometry_init(getPointer(), geom);
    }

    public void videoGeometryUninit(Pointer geom) {
        gdi_video_geometry_uninit(getPointer(), geom);
    }
}

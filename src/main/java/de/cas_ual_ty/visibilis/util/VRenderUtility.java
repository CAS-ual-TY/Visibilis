package de.cas_ual_ty.visibilis.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.visibilis.node.Node;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class VRenderUtility
{
    public static void drawTextCentered(FontRenderer fontRenderer, int x, int y, int w, String text, float color[])
    {
        text = fontRenderer.trimStringToWidth(text, w);
        int wT = fontRenderer.getStringWidth(text);
        fontRenderer.drawString(text, x + (w - wT) / 2, y, VRenderUtility.colorToInt(color));
    }
    
    /**
     * Since inputs and outputs share a line, only the higher amount of inputs or outputs is often needed.
     * 
     * @return The amount of inputs or outputs (whatever is higher) + 1 for the header + 1 for the footer.
     */
    public static int getVerticalAmt(Node node)
    {
        return (Math.max(node.getInputAmt(), node.getOutputAmt()) + 1);
    }
    
    /**
     * Bit shift an array of 3 floats to an int (alpha on max)
     */
    public static int colorToInt(float[] color)
    {
        int r = (int)(color[0] * 255F);
        int g = (int)(color[1] * 255F);
        int b = (int)(color[2] * 255F);
        int a = 255;
        
        int colorInt = (a << 24);
        colorInt = colorInt | (r << 16);
        colorInt = colorInt | (g << 8);
        colorInt = colorInt | (b);
        
        return colorInt;
    }
    
    /**
     * Cut everything off outside the given rectangle. Call this, then the all the draw code, then {@link #scissorEnd()} for cleanup.
     * 
     * @param x
     *            Pos X of the rectangle.
     * @param y
     *            Pos Y of the rectangle.
     * @param w
     *            Width of the rectangle.
     * @param h
     *            Height of the rectangle.
     */
    public static void scissorStart(MainWindow sr, int x, int y, int w, int h)
    {
        RenderSystem.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        // * scaleFactor due to the automatic resizing depending on window size (or the GUI size settings)
        // All the derparoundery with the Y position because Minecraft 0,0 is at the top left, but lwjgl 0,0 is at the bottom left
        GL11.glScissor((int)(x * sr.getGuiScaleFactor()), (int)((sr.getScaledHeight() - y - h) * sr.getGuiScaleFactor()), (int)(w * sr.getGuiScaleFactor()), (int)(h * sr.getGuiScaleFactor()));
    }
    
    /**
     * Scissor cleanup. Call {@link #innerStart(ScaledResolution, int, int, int, int)}, then all the draw code, then this.
     */
    public static void scissorEnd()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        RenderSystem.popMatrix();
    }
    
    /**
     * Apply zoom
     */
    public static void applyZoom(float zoom)
    {
        RenderSystem.scalef(zoom, zoom, 1); // Apply zoom, 2x zoom means 2x size of prints, so this is fine
    }
    
    /**
     * {@link #drawLine(int, int, int, int, byte, byte, byte, byte)} but with width of 1F.
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float r, float g, float b, float a)
    {
        VRenderUtility.drawLine(x1, y1, x2, y2, 1F, r, g, b, a);
    }
    
    /**
     * Draws a solid color line with the specified coordinates, color and width.
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float lineWidth, float r, float g, float b, float a)
    {
        GlStateManager.lineWidth(lineWidth);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        // Prep time
        GlStateManager.enableBlend(); // We do need blending
        GlStateManager.disableTexture(); // We dont need textures
        
        // Make sure alpha is working
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Set the color!
        RenderSystem.color4f(r, g, b, a);
        
        // Start drawing
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        
        // Add vertices
        bufferbuilder.pos(x1, y1, 0.0D).endVertex(); // P1
        bufferbuilder.pos(x2, y2, 0.0D).endVertex(); // P2
        
        // End drawing
        tessellator.draw();
        
        // Cleanup time
        GlStateManager.enableTexture(); // Turn textures back on
        GlStateManager.disableBlend(); // Turn blending uhh... back off?
    }
    
    /**
     * Draws a gradient color line with the specified coordinates, color, alpha and line width.
     */
    public static void drawGradientLine(int x1, int y1, int x2, int y2, float lineWidth, float alpha, float[] color1, float[] color2)
    {
        VRenderUtility.drawGradientLine(x1, y1, x2, y2, lineWidth, color1[0], color1[1], color1[2], alpha, color2[0], color2[1], color2[2], alpha);
    }
    
    /**
     * Draws a gradient color line with the specified coordinates, color (including alpha!) and line width.
     */
    public static void drawGradientLine(int x1, int y1, int x2, int y2, float lineWidth, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2)
    {
        GlStateManager.lineWidth(lineWidth);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        
        RenderSystem.disableAlphaTest();
        
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        
        bufferbuilder.pos(x1, y1, 0.0D).color(r1, g1, b1, a1).endVertex();
        bufferbuilder.pos(x2, y2, 0.0D).color(r2, g2, b2, a2).endVertex();
        
        tessellator.draw();
        
        RenderSystem.shadeModel(GL11.GL_FLAT);
        
        RenderSystem.enableAlphaTest();
        
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
    
    /**
     * With background, for without see {@link #drawRectWithText(FontRenderer, int, int, int, int, int, float[], int, String, float[])}
     */
    public static void drawRectWithText(FontRenderer fontRenderer, int x, int y, int w, int h, float[] colorBackground, int marginRect, float[] colorRect, int marginText, String text, float[] colorText)
    {
        VRenderUtility.drawRect(x, y, w, h, colorBackground[0], colorBackground[1], colorBackground[2]);
        VRenderUtility.drawRectWithText(fontRenderer, x, y, w, h, marginRect, colorRect, marginText, text, colorText);
    }
    
    /**
     * Without background, for with see {@link #drawRectWithText(FontRenderer, int, int, int, int, float[], int, float[], int, String, float[])}
     */
    public static void drawRectWithText(FontRenderer fontRenderer, int x, int y, int w, int h, int marginRect, float[] colorRect, int marginText, String text, float[] colorText)
    {
        // Draw inner colored rectangle
        VRenderUtility.drawRect(x + marginRect, y + marginRect, w - 2 * marginRect, h - 2 * marginRect, colorRect[0], colorRect[1], colorRect[2]);
        
        text = fontRenderer.trimStringToWidth(text, w - 2 * marginText); // Trim the text in case it is too big
        fontRenderer.drawString(text, x + marginText, y + marginText, VRenderUtility.colorToInt(colorText)); // Draw the trimmed text, maybe add shadow?
    }
    
    /**
     * {@link #drawRect(int, int, int, int, float, float, float)} but with margin
     */
    public static void drawRect(int x, int y, int w, int h, int marginRect, float[] colorRect)
    {
        // Draw inner colored rectangle
        VRenderUtility.drawRect(x + marginRect, y + marginRect, w - 2 * marginRect, h - 2 * marginRect, colorRect);
    }
    
    public static void drawRect(int x, int y, int w, int h, float[] colorRect, float alpha)
    {
        VRenderUtility.drawRect(x, y, w, h, colorRect[0], colorRect[1], colorRect[2], alpha);
    }
    
    public static void drawRect(int x, int y, int w, int h, float[] colorRect)
    {
        // Draw inner colored rectangle
        VRenderUtility.drawRect(x, y, w, h, colorRect[0], colorRect[1], colorRect[2]);
    }
    
    /**
     * {@link #drawRect(int, int, int, int, float, float, float, float)} but with alpha on max.
     */
    public static void drawRect(int x, int y, int w, int h, float r, float g, float b)
    {
        // TODO low: write separate code for rects without alpha, maybe...
        VRenderUtility.drawRect(x, y, w, h, r, g, b, 1F);
    }
    
    /**
     * (This is the almost-equivalent of the vanilla Gui class's drawRect method. The differences are no without bit shifting, and width/height instead of max x/y.) Draws a solid color rectangle with the specified coordinates and color.
     * 
     * @see net.minecraft.client.gui.Gui#drawRect(int, int, int, int, int)
     */
    public static void drawRect(int x, int y, int w, int h, float r, float g, float b, float a)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        // Prep time
        GlStateManager.enableBlend(); // We do need blending
        GlStateManager.disableTexture(); // We dont need textures
        
        // Make sure alpha is working
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Set the color!
        RenderSystem.color4f(r, g, b, a);
        
        // Start drawing
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        
        // Add vertices
        bufferbuilder.pos(x, y + h, 0.0D).endVertex(); // BL
        bufferbuilder.pos(x + w, y + h, 0.0D).endVertex(); // BR
        bufferbuilder.pos(x + w, y, 0.0D).endVertex(); // TR
        bufferbuilder.pos(x, y, 0.0D).endVertex(); // TL
        
        // End drawing
        tessellator.draw();
        
        // Cleanup time
        GlStateManager.enableTexture(); // Turn textures back on
        GlStateManager.disableBlend(); // Turn blending uhh... back off?
    }
    
    /**
     * Draw a border. The given rect is the outside, the border width is subtracted from it.
     */
    public static void drawBorderInside(int x, int y, int w, int h, int borderWidth, float r, float g, float b, float a)
    {
        VRenderUtility.drawBorder(x + borderWidth, y + borderWidth, w - borderWidth * 2, h - borderWidth * 2, borderWidth, r, g, b, a);
    }
    
    /**
     * Draw a border. The given rect is the inside, the border width is added to it.
     */
    public static void drawBorder(int x, int y, int w, int h, int borderWidth, float r, float g, float b, float a)
    {
        int x2 = x + w;
        int y2 = y + h;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        // Prep time
        GlStateManager.enableBlend(); // We do need blending
        GlStateManager.disableTexture(); // We dont need textures
        
        // Make sure alpha is working
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Set the color!
        RenderSystem.color4f(r, g, b, a);
        
        // Start drawing
        bufferbuilder.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION);
        
        // Add vertices
        bufferbuilder.pos(x2 + borderWidth, y - borderWidth, 0.0D).endVertex(); // TR Outer
        bufferbuilder.pos(x2, y, 0.0D).endVertex(); // TR Inner
        bufferbuilder.pos(x2 + borderWidth, y2 + borderWidth, 0.0D).endVertex(); // BR Outer
        bufferbuilder.pos(x2, y2, 0.0D).endVertex(); // BR Inner
        bufferbuilder.pos(x - borderWidth, y2 + borderWidth, 0.0D).endVertex(); // BL Outer
        bufferbuilder.pos(x, y2, 0.0D).endVertex(); // BL Inner
        bufferbuilder.pos(x - borderWidth, y - borderWidth, 0.0D).endVertex(); // TL Outer
        bufferbuilder.pos(x, y, 0.0D).endVertex(); // TL Inner
        bufferbuilder.pos(x2 + borderWidth, y - borderWidth, 0.0D).endVertex(); // TR Outer
        bufferbuilder.pos(x2, y, 0.0D).endVertex(); // TR Inner
        
        // End drawing
        tessellator.draw();
        
        // Cleanup time
        GlStateManager.enableTexture(); // Turn textures back on
        GlStateManager.disableBlend(); // Turn blending uhh... back off?
    }
    
    /**
     * Check if the given mouse coordinates are inside given rectangle
     */
    public static boolean isCoordInsideRect(double mouseX, double mouseY, double x, double y, double w, double h)
    {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }
    
    public static float[] mixColors(float[] color1, float[] color2)
    {
        float r = (color1[0] + color2[0]) * 0.5F;
        float g = (color1[1] + color2[1]) * 0.5F;
        float b = (color1[2] + color2[2]) * 0.5F;
        
        return new float[] { r, g, b };
    }
    
    // Maybe temporary, maybe not
    public static class Rectangle
    {
        public int l, r, t, b, x, y, w, h;
        
        private Rectangle()
        {
        }
        
        public Rectangle setXYWH(int x, int y, int w, int h)
        {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            
            this.updateLRTB();
            
            return this;
        }
        
        public Rectangle setLRTB(int l, int r, int t, int b)
        {
            this.l = l;
            this.r = r;
            this.t = t;
            this.b = b;
            
            this.updateXYWH();
            
            return this;
        }
        
        public void updateLRTB()
        {
            this.l = this.x;
            this.t = this.y;
            this.r = this.x + this.w;
            this.b = this.y + this.h;
        }
        
        public void updateXYWH()
        {
            this.x = this.l;
            this.y = this.t;
            this.w = this.r - this.l;
            this.h = this.b - this.t;
        }
        
        public boolean isCoordInside(float x, float y)
        {
            return VRenderUtility.isCoordInsideRect(x, y, this.x, this.y, this.w, this.h);
        }
        
        public void render(float[] color)
        {
            VRenderUtility.drawRect(this.x, this.y, this.w, this.h, color);
        }
        
        public void render(float r, float g, float b)
        {
            VRenderUtility.drawRect(this.x, this.y, this.w, this.h, r, g, b);
        }
        
        public static Rectangle fromXYWH(int x, int y, int w, int h)
        {
            return new Rectangle().setXYWH(x, y, w, h);
        }
        
        public static Rectangle fromLRTB(int l, int r, int t, int b)
        {
            return new Rectangle().setLRTB(l, r, t, b);
        }
    }
}

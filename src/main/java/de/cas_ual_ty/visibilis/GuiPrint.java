package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class GuiPrint extends GuiScreen
{
    // The height of the header or an output/input NOT THE ENTIRE NODE WIDTH AS THEY HAVE DIFFERENT SIZES
    public static int nodeHeight = 12;
    
    // The entire node width
    public static int nodeWidth = nodeHeight * 10;
    
    // The dot x/y size of node fields
    public static int nodeFieldDotSize = 4;
    
    // Transparency of connection lines
    public static float nodeFieldConnectionsAlpha = 0.5F;
    
    // Transparency of white box when hovering
    public static float hoverAlpha = 0.5F;
    
    // Backround of nodes
    public static float nodeBackground = 0.125F;
    
    protected Print print;
    
    public float zoom = 1F;
    
    protected ScaledResolution sr;
    
    // --- Mouse clicks on node or field -> temporarily stored here ---
    //
    // The thing clicked on
    protected Node mouseAttachedNode;
    protected NodeField mouseAttachedField;
    //
    // The position it was at when clicked on
    protected int attachedPrevX;
    protected int attachedPrevY;
    //
    // --- End temporarily stored things the user clicked on ---
    
    public GuiPrint(Print print)
    {
        this.print = print;
    }
    
    @Override
    public void initGui()
    {
        this.sr = new ScaledResolution(this.mc);
    }
    
    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen()
    {
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.disableLighting();
        
        int x = 4, y = 10, w = 900, h = 900; // TODO high, window size? Maybe plan layout before starting? Make static?
        
         GuiPrint.innerStart(this.sr, x, y, w, h);
         GuiPrint.applyZoomAndShift(this.zoom, 0, 0); //Inside of the matrix since you would otherwise "touch" everything outside of the matrix
        this.drawInner(mouseX, mouseY, partialTicks);
         GuiPrint.innerEnd();
        
        // Draw buttons and labels
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        GlStateManager.enableLighting();
    }
    
    public void drawInner(int mouseX, int mouseY, float partialTicks)
    {
        this.drawPrint(this.print);
        
        // --- Draw hovering start ---
        
        Object obj = this.getObjectHoveringInner(mouseX, mouseY);
        
        if (obj instanceof Node)
        {
            // Hovering over a node
            Node node = (Node) obj;
            
            // Draw white rect over node, half transparent
            GuiPrint.drawRect(this.print.posX + node.posX, this.print.posY + node.posY, nodeWidth, nodeHeight * GuiPrint.getVerticalAmt(node), 1F, 1F, 1F, hoverAlpha);
        }
        else if (obj instanceof NodeField)
        {
            // Hovering over a node field
            NodeField field = (NodeField) obj;
            
            // Draw white rect over node field, half transparent
            GuiPrint.drawRect(this.print.posX + field.node.posX + (field.isInput() ? 0 : nodeWidth / 2), this.print.posY + field.node.posY + nodeHeight * (field.id + 1), nodeWidth / 2, nodeHeight, 1F, 1F, 1F, hoverAlpha);
        }
        
        // --- Hovering end ---
    }
    
    /**
     * Draw a print and all its nodes and connections at the given coordinates
     */
    public void drawPrint(Print print)
    {
        int x;
        int y;
        
        // Loop through all nodes. Nodes at the end of the list will be drawn on top.
        for (Node node : print.getNodes())
        {
            x = node.posX + print.posX;
            y = node.posY + print.posX;
            this.drawNode(node, x, y);
        }
    }
    
    /**
     * Draw a node at the given coordinates (not the node's coordinates!) and all its node fields.
     */
    public void drawNode(Node node, int x, int y)
    {
        // --- Start drawing node itself ---
        
        // Draw entire node background
        GuiPrint.drawRect(x, y, nodeWidth, nodeHeight * GuiPrint.getVerticalAmt(node), nodeBackground, nodeBackground, nodeBackground);
        
        // #SelfExplainingCodeIsAMeme
        this.drawNodeHeader(node, x, y);
        
        // --- Done drawing node, now drawing fields (inputs and outputs) ---
        
        int i;
        NodeField field;
        
        // Draw inputs, i + 1 to draw below header
        for (i = 0; i < node.getInputAmt(); ++i)
        {
            field = node.getInput(i);
            this.drawNodeField(field, x, y + nodeHeight * (i + 1));
        }
        
        // Draw outputs, i + 1 to draw below header
        for (i = 0; i < node.getOutputAmt(); ++i)
        {
            field = node.getOutput(i);
            this.drawNodeField(field, x + nodeWidth / 2, y + nodeHeight * (i + 1)); // Outputs are on the right, so add
                                                                                    // half width of the node
        }
        
        // --- End drawing fields ---
    }
    
    /**
     * Draw a node header at the given coordinates (not the node's coordinates!).
     */
    public void drawNodeHeader(Node node, int x, int y)
    {
        // Draw the inner colored rectangle
        drawRect(x + 1, y + 1, nodeWidth - 2, nodeHeight - 2, node.getColor()[0], node.getColor()[1], node.getColor()[2]);
        
        // Draw the name
        String name = node.getUnlocalizedName(); // TODO lowest, translate
        name = this.fontRenderer.trimStringToWidth(name, nodeWidth - 4); // Trim the name in case it is too big
        this.fontRenderer.drawString(name, x + 2, y + 2, 0xFFFFFFFF); // Draw the trimmed name, maybe add shadow?
    }
    
    /**
     * Draw a node field at the given coordinates (not the node's coordinates!).
     */
    public void drawNodeField(NodeField field, int x, int y)
    {
        int width = nodeWidth / 2;
        
        int dotX, dotY; // Where to draw the dot
        int nameX, nameY; // Where to draw the name
        
        // width - dot - border
        // the dot is in the middle of a quad of size height x height, at the left/right of the field
        int nameW = width - nodeHeight;
        
        if (field.isInput())
        {
            // Input, so draw the dot on the left, the name on the right
            dotX = x;
            nameX = dotX + nodeHeight;
        }
        else
        {
            // Output, so draw the dot on the right, the name on the left
            nameX = x;
            dotX = x + width - nodeHeight;
        }
        
        DataType type = field.dataType;
        
        // Draw inner colored rectangle
        nameX += 1;
        nameY = y + 1;
        drawRect(nameX, nameY, nameW - 2, nodeHeight - 2, type.getColor()[0], type.getColor()[1], type.getColor()[2]);
        
        // Finally adjust dot position for its size
        dotX += (nodeHeight - nodeFieldDotSize) / 2;
        dotY = y + (nodeHeight - nodeFieldDotSize) / 2;
        
        // Draw connections
        this.drawNodeFieldConnections(field, dotX, dotY);
        
        // Draw dot on top
        drawRect(dotX, dotY, nodeFieldDotSize, nodeFieldDotSize, type.getColor()[0], type.getColor()[1], type.getColor()[2]);
        
        // Draw name
        String name = field.getUnlocalizedName(); // TODO lowest: translate
        name = this.fontRenderer.trimStringToWidth(name, nameW - 4); // Trim the name in case it is too big
        this.fontRenderer.drawString(name, nameX + 1, nameY + 1, 0xFFFFFFFF); // Draw the trimmed name, maybe add shadow?
    }
    
    /**
     * Draw a node field's connections of the given coordinates (not the node's coordinates!).
     */
    public void drawNodeFieldConnections(NodeField field, int dotX, int dotY)
    {
        DataType type1 = field.dataType;
        DataType type2;
        
        // Draw output -> input connections, not the other way around, for proper overlay order.
        if (field.isInput())
        {
            return;
        }
        
        // Retrieve all connections
        ArrayList<NodeField> connections = field.getConnectionsList();
        
        // Prepare variables so we dont create new ones in each loop
        int offX, offY;
        
        int x1, y1, x2, y2;
        
        // Loop through "destinations"
        for (NodeField dest : connections)
        {
            type2 = dest.dataType;
            
            // We are already at the dot position of the 1st field, so we can just take the
            // difference of the nodes themselves to get the 2nd dot's position
            offX = dest.node.posX - field.node.posX;
            offY = dest.node.posY - field.node.posY;
            
            // Our position is an output dot, so we need to shift to an input dot
            offX -= nodeWidth - nodeHeight;
            
            // Adjust Y, eg. 1st field might be the 2nd in order, the 2nd field might be the
            // 4th in order
            offY += (dest.id - field.id) * nodeHeight;
            
            // + half size so it starts in the middle
            x1 = nodeFieldDotSize / 2 + dotX;
            y1 = nodeFieldDotSize / 2 + dotY;
            x2 = x1 + offX;
            y2 = y1 + offY;
            
            // Now draw the line, half transparent
            drawGradientLine(x1, y1, x2, y2, this.getLineWidth(type1), type1.getColor()[0], type1.getColor()[1], type1.getColor()[2], nodeFieldConnectionsAlpha, type2.getColor()[0], type2.getColor()[1], type2.getColor()[2], nodeFieldConnectionsAlpha);
        }
    }
    
    /**
     * Returns the object the mouse is hovering over, can be a node or a node field
     */
    public Object getObjectHovering(int mouseX, int mouseY, int x, int y, int w, int h)
    {
        if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
        {
            // Inner
            return this.getObjectHoveringInner(mouseX, mouseY);
        }
        else
        {
            // Outer
        }
        
        return null;
    }
    
    /**
     * Returns the object the mouse is hovering over that is inside the inner part
     */
    protected Object getObjectHoveringInner(int mouseX, int mouseY)
    {
        Node node;
        float x, y, w, h;
        
        // Loop from back to front, as those are on top
        for (int i = this.print.nodes.size() - 1; i >= 0; --i)
        {
            node = this.print.nodes.get(i);
            
            // Entire node position and size, zoom and shift accounted for
            x = this.guiToPrint(node.posX + this.print.posX);
            y = this.guiToPrint(node.posY + this.print.posX);
            w = this.guiToPrint(nodeWidth);
            h = this.guiToPrint(nodeHeight * GuiPrint.getVerticalAmt(node));
            
            // Check if the mouse is on top of the entire node
            if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, w, this.guiToPrint(nodeHeight)))
                {
                    // Inside header -> return node itself
                    return node;
                }
                else
                {
                    // Not inside header -> node fields
                    
                    int j;
                    
                    if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, this.guiToPrint(nodeWidth / 2), h))
                    {
                        // Left side -> inputs
                        
                        for (j = 1; j <= node.getInputAmt(); ++j)
                        {
                            if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, this.guiToPrint(node.posY + nodeHeight * j), w, this.guiToPrint(nodeHeight)))
                            {
                                // inside this node field -> return it
                                return node.getInput(j - 1);
                            }
                        }
                        
                        // No node field found, which means there are more outputs than inputs -> mouse
                        // is below inputs, so return the node itself
                        return node;
                    }
                    else
                    {
                        // Right side -> outputs
                        
                        for (j = 1; j <= node.getOutputAmt(); ++j)
                        {
                            if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, this.guiToPrint(node.posY + nodeHeight * j), w, this.guiToPrint(nodeHeight)))
                            {
                                // inside this node field -> return it
                                return node.getOutput(j - 1);
                            }
                        }
                        
                        // No node field found, which means there are more inputs than outputs -> mouse
                        // is below outputs, so return the node itself
                        return node;
                    }
                }
            }
        }
        
        return null;
    }
    
    /**
     * Used to intercept key downs
     * 
     * @see de.cas_ual_ty.visibilis.handler.VEventHandlerClient#onEvent(KeyInputEvent)
     */
    public void onKeyInput(KeyInputEvent event)
    {
        // TODO scrolling, zooming
    }
    
    /**
     * Check if the given mouse coordinates are inside given rectangle
     */
    public static boolean isCoordInsideRect(float mouseX, float mouseY, float x, float y, float w, float h)
    {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }
    
    /**
     * Since inputs and outputs share a line, only the higher amount of inputs or outputs is often needed.
     * 
     * @return The amount of inputs or outputs (whatever is higher) + 1 for the header.
     */
    public static int getVerticalAmt(Node node)
    {
        if (node.getInputAmt() > node.getOutputAmt())
        {
            return node.getInputAmt() + 1;
        }
        else
        {
            return node.getOutputAmt() + 1;
        }
    }
    
    /**
     * Apply the zoom factor.
     */
    public float guiToPrint(int i)
    {
        return i * this.zoom; // TODO today: see below as well, separate method to adjust for the position of the print
    }
    
    /**
     * Remove the zoom factor.
     */
    public int printToGui(float f)
    {
        return Math.round(f / this.zoom);
    }
    
    public float getLineWidth(DataType type)
    {
        return this.zoom * (type == DataType.EXEC ? 2 : 1) * this.sr.getScaleFactor() * nodeFieldDotSize / 2;
    }
    
    /**
     * Cut everything off outside the given rectangle. Call this, then the all the draw code, then {@link #innerEnd()} for cleanup.
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
    public static void innerStart(ScaledResolution sr, int x, int y, int w, int h)
    {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        // * scaleFactor due to the automatic resizing depending on window size (or the
        // GUI size settings)
        // All the derparoundery with the Y position because Minecraft 0,0 is at the top
        // left, but lwjgl 0,0 is at the bottom left
        GL11.glScissor(x * sr.getScaleFactor(), (sr.getScaledHeight() - y - h) * sr.getScaleFactor(), w * sr.getScaleFactor(), h * sr.getScaleFactor());
        GL11.glPushMatrix();
    }
    
    /**
     * Scissor cleanup. Call {@link #innerStart(int, int, int, int)}, then all the draw code, then this.
     */
    public static void innerEnd()
    {
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
    
    /**
     * Move according to the scolling of the user and apply zoom
     */
    public static void applyZoomAndShift(float zoom, float x, float y) // TODO today: shift/move (scrolling)
    {
        GL11.glScalef(zoom, zoom, 1); // Apply zoom, 2x zoom means 2x size of prints, so this is fine
        GL11.glTranslatef(x, y, 0);
    }
    
    /**
     * {@link #drawLine(int, int, int, int, byte, byte, byte, byte)} but with quick setting of lineWidth
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float lineWidth, float r, float g, float b, float a)
    {
        GlStateManager.glLineWidth(lineWidth);
        drawLine(x1, y1, x2, y2, r, g, b, a);
    }
    
    /**
     * Draws a solid color line with the specified coordinates and color.
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float r, float g, float b, float a)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        // Prep time
        GlStateManager.enableBlend(); // We do need blending
        GlStateManager.disableTexture2D(); // We dont need textures
        
        // Make sure alpha is working
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Set the color!
        GlStateManager.color(r, g, b, a);
        
        // Start drawing
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        
        // Add vertices
        bufferbuilder.pos(x1, y1, 0.0D).endVertex(); // P1
        bufferbuilder.pos(x2, y2, 0.0D).endVertex(); // P2
        
        // End drawing
        tessellator.draw();
        
        // Cleanup time
        GlStateManager.enableTexture2D(); // Turn textures back on
        GlStateManager.disableBlend(); // Turn blending uhh... back off?
    }
    
    public static void drawGradientLine(int x1, int y1, int x2, int y2, float lineWidth, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2)
    {
        GlStateManager.glLineWidth(lineWidth);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        
        GlStateManager.disableAlpha();
        
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        
        bufferbuilder.pos(x1, y1, 0.0D).color(r1, g1, b1, a1).endVertex();
        bufferbuilder.pos(x2, y2, 0.0D).color(r2, g2, b2, a2).endVertex();
        
        tessellator.draw();
        
        GlStateManager.shadeModel(GL11.GL_FLAT);
        
        GlStateManager.enableAlpha();
        
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    /**
     * {@link #drawRect(int, int, int, int, byte, byte, byte, byte)} but without alpha.
     */
    public static void drawRect(int x, int y, int w, int h, float r, float g, float b)
    {
        // TODO low: write separate code for rects without alpha, maybe...
        drawRect(x, y, w, h, r, g, b, 1F);
    }
    
    /**
     * (This is the almost-equivalent of the vanilla Gui class's drawRect method. The differences are direct byte rgba input without bit shifting, and width/height instead of max x/y.) Draws a solid color rectangle with the specified coordinates and color.
     * 
     * @see net.minecraft.client.gui.Gui#drawRect(int, int, int, int, int)
     */
    public static void drawRect(int x, int y, int w, int h, float r, float g, float b, float a)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        // Prep time
        GlStateManager.enableBlend(); // We do need blending
        GlStateManager.disableTexture2D(); // We dont need textures
        
        // Make sure alpha is working
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Set the color!
        GlStateManager.color(r, g, b, a);
        
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
        GlStateManager.enableTexture2D(); // Turn textures back on
        GlStateManager.disableBlend(); // Turn blending uhh... back off?
    }
}

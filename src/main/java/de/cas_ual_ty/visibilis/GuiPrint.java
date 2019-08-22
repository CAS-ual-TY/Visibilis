package de.cas_ual_ty.visibilis;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
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
import net.minecraft.client.resources.I18n;

public class GuiPrint extends GuiScreen
{
    // The height of the header or an output/input NOT THE ENTIRE NODE WIDTH AS THEY HAVE DIFFERENT SIZES
    public static int nodeHeight = 12;
    
    // The entire node width
    public static int nodeWidth = GuiPrint.nodeHeight * 10;
    
    // The dot x/y size of node fields
    public static int nodeFieldDotSize = 4;
    
    // Transparency of connection lines
    public static float nodeFieldConnectionsAlpha = 0.5F;
    
    // Transparency of white box when hovering
    public static float hoverAlpha = 0.5F;
    
    // Background of nodes
    public static float nodeBackground = 0.125F;
    
    // Empty connection grey tone
    public static float nodeFieldDef = 0.5F;
    
    public static int scrollSpeedInner = 2;
    
    public Rectangle inner;
    
    protected Print print;
    
    protected ScaledResolution sr;
    
    // --- Mouse hovers/clicks on node or field -> temporarily stored here ---
    //
    // The thing clicked on (for input)
    protected Node mouseClickedNode;
    protected NodeField mouseClickedField;
    
    // The thing currently hovering on (for rendering)
    protected Node mouseHoveringNode;
    protected NodeField mouseHoveringField;
    //
    // true = Mouse clicked and is holding smth
    protected boolean clicked;
    //
    // The position it was at when clicked on
    protected int attachedPrevX;
    protected int attachedPrevY;
    //
    // --- End temporarily stored things the user clicked on ---
    
    public GuiPrint(Print print)
    {
        this.print = print;
        this.clicked = false;
    }
    
    @Override
    public void initGui()
    {
        this.inner = Rectangle.fromXYWH(0, 0, this.width, this.height);
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
        
        this.updateHoveringAndClicked(mouseX, mouseY); // Check for all hovering objects already, so it is done only once
        
        GuiPrint.scissorStart(this.sr, this.inner.x, this.inner.y, this.inner.w, this.inner.h);
        GuiPrint.applyZoom(this.print.zoom); // Inside of the matrix since you would otherwise "touch" everything outside of the matrix
        this.drawInner(mouseX, mouseY, partialTicks);
        GuiPrint.scissorEnd();
        
        // Draw buttons and labels
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        GlStateManager.enableLighting();
    }
    
    /**
     * Update the node or node field hovering over or previously clicked on
     */
    public void updateHoveringAndClicked(int mouseX, int mouseY)
    {
        Object obj = this.getObjectHovering(mouseX, mouseY, 0, 0, this.width, this.height);
        
        this.mouseHoveringNode = null;
        this.mouseHoveringField = null;
        
        if (obj instanceof Node)
        {
            this.mouseHoveringNode = (Node) obj;
        }
        else if (obj instanceof NodeField)
        {
            this.mouseHoveringField = (NodeField) obj;
        }
        
        if (this.clicked)
        {
            if (this.mouseClickedNode != null)
            {
                this.mouseClickedNode.posX = this.printToGuiRounded(mouseX) - this.print.posX;
                this.mouseClickedNode.posY = this.printToGuiRounded(mouseY) - this.print.posY;
            }
        }
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        // if focused on eg. a field where you can input stuff manually
        // - shift input there
        // - return
        
        if (keyCode == 1)
        {
            super.keyTyped(typedChar, keyCode);
            return;
        }
        
        if (keyCode == Keyboard.KEY_W || keyCode == Keyboard.KEY_UP)
        {
            this.print.posY += GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_S || keyCode == Keyboard.KEY_DOWN)
        {
            this.print.posY += GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_A || keyCode == Keyboard.KEY_LEFT)
        {
            this.print.posX += GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_D || keyCode == Keyboard.KEY_RIGHT)
        {
            this.print.posX += GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ADD)
        {
            this.print.zoom *= 2;
            
            if (this.print.zoom > 2)
            {
                this.print.zoom = 2;
            }
            else
            {
                // TODO Adjust print position so that the middle of the screen stays the middle when zooming
            }
        }
        if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_SUBTRACT)
        {
            this.print.zoom *= 0.5F;
            
            if (this.print.zoom < 0.125F)
            {
                this.print.zoom = 0.125F;
            }
            else
            {
                // TODO Adjust print position so that the middle of the screen stays the middle when zooming
            }
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
            Object obj = this.getObjectHovering(mouseX, mouseY, 0, 0, this.width, this.height);
            
            // --- if inside inner ---
            if (!this.clicked)
            {
                if (obj instanceof Node)
                {
                    this.clicked = true;
                    this.mouseClickedNode = (Node) obj;
                    this.attachedPrevX = this.getNodePosX(this.mouseClickedNode);
                    this.attachedPrevY = this.getNodePosY(this.mouseClickedNode);
                }
                else if (obj instanceof NodeField)
                {
                    this.clicked = true;
                    this.mouseClickedField = (NodeField) obj;
                }
            }
            else
            {
                if (this.mouseClickedField != null && obj instanceof NodeField)
                {
                    NodeField field = (NodeField) obj;
                    
                    NodeField.tryConnect(this.mouseClickedField, field);
                }
                
                this.clicked = false;
                this.mouseClickedNode = null;
                this.mouseClickedField = null;
            }
            
            // --- else ---
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void drawInner(int mouseX, int mouseY, float partialTicks)
    {
        GuiPrint.drawBorder(10, 10, this.width - 20, this.height - 20, 10, 0, 0, 0, 1);
        
        this.drawPrint(this.print);
        
        // --- Draw hovering/clicked start ---
        
        if (this.clicked)
        {
            // Objects that the player clicked on to be outlined
            
            if (this.mouseClickedNode != null)
            {
                // Outline clicked on node
                this.drawOutlineRect(this.attachedPrevX, this.attachedPrevY, GuiPrint.nodeWidth, GuiPrint.nodeHeight * GuiPrint.getVerticalAmt(this.mouseClickedNode));
            }
            if (this.mouseClickedField != null)
            {
                // Where to draw the 1st dot at
                int dotX = this.getDotPosX(this.mouseClickedField);
                int dotY = this.getDotPosY(this.mouseClickedField);
                
                if (this.mouseHoveringField != null && NodeField.canConnect(this.mouseClickedField, this.mouseHoveringField))
                {
                    this.drawHoverRect(this.getNodePosX(this.mouseHoveringField.node) + (this.mouseHoveringField.isInput() ? 0 : GuiPrint.nodeWidth / 2), this.getNodePosY(this.mouseHoveringField.node) + GuiPrint.nodeHeight * (this.mouseHoveringField.id + 1), GuiPrint.nodeWidth / 2, GuiPrint.nodeHeight);
                }
                
                // Node field was clicked on -> Render line from Dot -> Mouse
                this.drawConnectionLine(dotX + GuiPrint.nodeFieldDotSize / 2, dotY + GuiPrint.nodeFieldDotSize / 2, this.printToGuiRounded(mouseX), this.printToGuiRounded(mouseY), this.getLineWidth(this.mouseClickedField.dataType), this.mouseClickedField.dataType.getColor()[0], this.mouseClickedField.dataType.getColor()[1], this.mouseClickedField.dataType.getColor()[2], GuiPrint.nodeFieldConnectionsAlpha, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldConnectionsAlpha);
            }
        }
        else
        {
            // Nothing has been clicked on, render normal hovering rects
            
            if (this.mouseHoveringNode != null)
            {
                // Node hover rect
                this.drawHoverRect(this.getNodePosX(this.mouseHoveringNode), this.getNodePosY(this.mouseHoveringNode), GuiPrint.nodeWidth, GuiPrint.nodeHeight * GuiPrint.getVerticalAmt(this.mouseHoveringNode));
            }
            if (this.mouseHoveringField != null)
            {
                // Node field hover rect
                this.drawHoverRect(this.getNodePosX(this.mouseHoveringField.node) + (this.mouseHoveringField.isInput() ? 0 : GuiPrint.nodeWidth / 2), this.getNodePosY(this.mouseHoveringField.node) + GuiPrint.nodeHeight * (this.mouseHoveringField.id + 1), GuiPrint.nodeWidth / 2, GuiPrint.nodeHeight);
            }
        }
        
        // --- Hovering/clicked end ---
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
            x = this.getNodePosX(node);
            y = this.getNodePosY(node);
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
        GuiPrint.drawRect(x, y, GuiPrint.nodeWidth, GuiPrint.nodeHeight * GuiPrint.getVerticalAmt(node), GuiPrint.nodeBackground, GuiPrint.nodeBackground, GuiPrint.nodeBackground);
        
        // #SelfExplainingCodeIsAMeme
        this.drawNodeHeader(node, x, y);
        
        // --- Done drawing node, now drawing fields (inputs and outputs) ---
        
        int i;
        NodeField field;
        
        // Draw inputs, i + 1 to draw below header
        for (i = 0; i < node.getInputAmt(); ++i)
        {
            field = node.getInput(i);
            this.drawNodeField(field, x, y + GuiPrint.nodeHeight * (i + 1));
        }
        
        // Draw outputs, i + 1 to draw below header
        for (i = 0; i < node.getOutputAmt(); ++i)
        {
            field = node.getOutput(i);
            this.drawNodeField(field, x + GuiPrint.nodeWidth / 2, y + GuiPrint.nodeHeight * (i + 1)); // Outputs are on the right, so add
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
        GuiPrint.drawRect(x + 1, y + 1, GuiPrint.nodeWidth - 2, GuiPrint.nodeHeight - 2, node.getColor()[0], node.getColor()[1], node.getColor()[2]);
        
        // Draw the name
        String name = I18n.format(node.getUnlocalizedName());
        name = this.fontRenderer.trimStringToWidth(name, GuiPrint.nodeWidth - 4); // Trim the name in case it is too big
        this.fontRenderer.drawString(name, x + 2, y + 2, 0xFFFFFFFF); // Draw the trimmed name, maybe add shadow?
    }
    
    /**
     * Draw a node field at the given coordinates (not the node's coordinates!).
     */
    public void drawNodeField(NodeField field, int x, int y)
    {
        int width = GuiPrint.nodeWidth / 2;
        
        int dotX = this.getDotPosX(field), dotY = this.getDotPosY(field); // Where to draw the dot
        int nameX = x, nameY = y; // Where to draw the name
        
        // width - dot - border
        // the dot is in the middle of a quad of size height x height, at the left/right of the field
        int nameW = width - GuiPrint.nodeHeight;
        
        if (field.isInput())
        {
            // Input, so draw the dot on the left, the name on the right
            nameX += GuiPrint.nodeHeight;
        }
        
        DataType type = field.dataType;
        
        // Draw inner colored rectangle
        GuiPrint.drawRect(nameX + 1, nameY + 1, nameW - 2, GuiPrint.nodeHeight - 2, type.getColor()[0], type.getColor()[1], type.getColor()[2]);
        
        // Draw connections
        this.drawNodeFieldConnections(field, dotX, dotY);
        
        // Draw dot on top
        GuiPrint.drawRect(dotX, dotY, GuiPrint.nodeFieldDotSize, GuiPrint.nodeFieldDotSize, type.getColor()[0], type.getColor()[1], type.getColor()[2]);
        
        // Draw name
        String name = I18n.format(field.getUnlocalizedName());
        name = this.fontRenderer.trimStringToWidth(name, nameW - 4); // Trim the name in case it is too big
        this.fontRenderer.drawString(name, nameX + 2, nameY + 2, 0xFFFFFFFF); // Draw the trimmed name, maybe add shadow?
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
            offX -= GuiPrint.nodeWidth - GuiPrint.nodeHeight;
            
            // Adjust Y, eg. 1st field might be the 2nd in order, the 2nd field might be the
            // 4th in order
            offY += (dest.id - field.id) * GuiPrint.nodeHeight;
            
            // + half size so it starts in the middle
            x1 = GuiPrint.nodeFieldDotSize / 2 + dotX;
            y1 = GuiPrint.nodeFieldDotSize / 2 + dotY;
            x2 = x1 + offX;
            y2 = y1 + offY;
            
            // Now draw the line, half transparent
            this.drawConnectionLine(x1, y1, x2, y2, type1, type2);
        }
    }
    
    /**
     * Draw a single connection line with a gradient and width according to the data type(s).
     */
    public void drawConnectionLine(int x1, int y1, int x2, int y2, DataType type1, DataType type2)
    {
        this.drawConnectionLine(x1, y1, x2, y2, this.getLineWidth(type1), type1.getColor()[0], type1.getColor()[1], type1.getColor()[2], GuiPrint.nodeFieldConnectionsAlpha, type2.getColor()[0], type2.getColor()[1], type2.getColor()[2], GuiPrint.nodeFieldConnectionsAlpha);
    }
    
    /**
     * Draw a single connection line with a gradient and width according to the data type(s).
     */
    public void drawConnectionLine(int x1, int y1, int x2, int y2, float lineWidth, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2)
    {
        GuiPrint.drawGradientLine(x1, y1, x2, y2, lineWidth, r1, g1, b1, a1, r2, g2, b2, a2);
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
            x = this.getNodePosX2(node);
            y = this.getNodePosY2(node);
            w = this.guiToPrint(GuiPrint.nodeWidth);
            h = this.guiToPrint(GuiPrint.nodeHeight * GuiPrint.getVerticalAmt(node));
            
            // Check if the mouse is on top of the entire node
            if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, w, this.guiToPrint(GuiPrint.nodeHeight)))
                {
                    // Inside header -> return node itself
                    
                    return node;
                }
                else
                {
                    // Not inside header -> node fields
                    
                    int j;
                    
                    if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, y, this.guiToPrint(GuiPrint.nodeWidth / 2), h))
                    {
                        // Left side -> inputs
                        
                        for (j = 1; j <= node.getInputAmt(); ++j)
                        {
                            if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, this.guiToPrint(this.getNodePosY(node) + GuiPrint.nodeHeight * j), w, this.guiToPrint(GuiPrint.nodeHeight)))
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
                            if (GuiPrint.isCoordInsideRect(mouseX, mouseY, x, this.guiToPrint(this.getNodePosY(node) + GuiPrint.nodeHeight * j), w, this.guiToPrint(GuiPrint.nodeHeight)))
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
        return i * this.print.zoom;
    }
    
    /**
     * Apply zoom factor and round
     */
    public int guiToPrintRounded(int i)
    {
        return Math.round(this.guiToPrint(i));
    }
    
    /**
     * Remove zoom factor and round
     */
    public int printToGuiRounded(int i)
    {
        return Math.round(i / this.print.zoom);
    }
    
    /**
     * Print posX + Node posX
     */
    public int getNodePosX(Node n)
    {
        return this.print.posX + n.posX;
    }
    
    /**
     * Print posY + Node posY
     */
    public int getNodePosY(Node n)
    {
        return this.print.posY + n.posY;
    }
    
    /**
     * (Print posX + Node posX) * zoom
     */
    public float getNodePosX2(Node n)
    {
        return this.guiToPrint(this.print.posX + n.posX);
    }
    
    /**
     * (Print posY + Node posY) * zoom
     */
    public float getNodePosY2(Node n)
    {
        return this.guiToPrint(this.print.posY + n.posY);
    }
    
    /**
     * Get the node field dot posX
     */
    public int getDotPosX(NodeField field)
    {
        return this.getNodePosX(field.node) + (field.isOutput() ? GuiPrint.nodeWidth - GuiPrint.nodeHeight : 0) + (GuiPrint.nodeHeight - GuiPrint.nodeFieldDotSize) / 2;
    }
    
    /**
     * Get the node field dot posY
     */
    public int getDotPosY(NodeField field)
    {
        return this.getNodePosY(field.node) + GuiPrint.nodeHeight * (field.id + 1) + (GuiPrint.nodeHeight - GuiPrint.nodeFieldDotSize) / 2;
    }
    
    /**
     * Get the line width for the data type. Adjusts for current zoom, scaled resolution scale factor, and doubles if "eec" type
     */
    public float getLineWidth(DataType type)
    {
        return this.print.zoom * (type == DataType.EXEC ? 2 : 1) * this.sr.getScaleFactor() * GuiPrint.nodeFieldDotSize / 2;
    }
    
    /**
     * Draw the white rectangle when hovering over an object. This can later be changed to eg. an outline (wich is why I made this a method)
     */
    public void drawHoverRect(int x, int y, int w, int h)
    {
        GuiPrint.drawRect(x, y, w, h, 1F, 1F, 1F, GuiPrint.hoverAlpha);
    }
    
    /**
     * Draw the white border over an object.
     */
    public void drawOutlineRect(int x, int y, int w, int h)
    {
        GuiPrint.drawRect(x, y, w, 2, 1F, 1F, 1F, GuiPrint.hoverAlpha); // TOP, x -> x + w
        GuiPrint.drawRect(x, y + h - 2, w, 2, 1F, 1F, 1F, GuiPrint.hoverAlpha);// BOT, x -> x + w
        GuiPrint.drawRect(x, y + 2, 2, h - 4, 1F, 1F, 1F, GuiPrint.hoverAlpha);// LEFT, y + 2 -> y + h - 2
        GuiPrint.drawRect(x + w - 2, y + 2, 2, h - 4, 1F, 1F, 1F, GuiPrint.hoverAlpha);// RIGHT, y + 2 -> y + h - 2
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
    public static void scissorStart(ScaledResolution sr, int x, int y, int w, int h)
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
    public static void scissorEnd()
    {
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
    
    /**
     * Apply zoom
     */
    public static void applyZoom(float zoom)
    {
        GL11.glScalef(zoom, zoom, 1); // Apply zoom, 2x zoom means 2x size of prints, so this is fine
    }
    
    /**
     * {@link #drawLine(int, int, int, int, byte, byte, byte, byte)} but with quick setting of lineWidth
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float lineWidth, float r, float g, float b, float a)
    {
        GlStateManager.glLineWidth(lineWidth);
        GuiPrint.drawLine(x1, y1, x2, y2, r, g, b, a);
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
     * {@link #drawRect(int, int, int, int, float, float, float, float)} but without alpha.
     */
    public static void drawRect(int x, int y, int w, int h, float r, float g, float b)
    {
        // TODO low: write separate code for rects without alpha, maybe...
        GuiPrint.drawRect(x, y, w, h, r, g, b, 1F);
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
    
    /**
     * Draw a border. The given rect is the outside, the border width is subtracted from it.
     */
    public static void drawBorderInside(int x, int y, int w, int h, int borderWidth, float r, float g, float b, float a)
    {
        GuiPrint.drawBorder(x + borderWidth, y + borderWidth, w - borderWidth * 2, h - borderWidth * 2, borderWidth, r, g, b, a);
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
        GlStateManager.disableTexture2D(); // We dont need textures
        
        // Make sure alpha is working
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Set the color!
        GlStateManager.color(r, g, b, a);
        
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
        GlStateManager.enableTexture2D(); // Turn textures back on
        GlStateManager.disableBlend(); // Turn blending uhh... back off?
    }
    
    // Maybe temporary, maybe not
    public static class Rectangle
    {
        public int l, r, t, b, x, y, w, h;
        
        private Rectangle(int l, int r, int t, int b, int w, int h)
        {
            this.l = l;
            this.r = r;
            this.t = t;
            this.b = b;
            this.w = w;
            this.h = h;
            
            this.x = l;
            this.y = t;
        }
        
        public static Rectangle fromXYWH(int x, int y, int w, int h)
        {
            return new Rectangle(x, x + w, y, y + w, w, h);
        }
        
        public static Rectangle fromLRTB(int l, int r, int t, int b)
        {
            return new Rectangle(l, r, t, b, r - l, b - t);
        }
    }
}

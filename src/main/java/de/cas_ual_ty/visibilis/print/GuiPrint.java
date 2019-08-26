package de.cas_ual_ty.visibilis.print;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GuiPrint extends GuiScreen
{
    // Empty connection grey tone
    public static float nodeFieldDef = 0.5F;
    
    public static int scrollSpeedInner = 2;
    
    public IPrintHelper helper;
    
    public RenderUtility.Rectangle inner;
    
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
    // --- End temporarily stored things the user clicked on ---
    
    protected RenderUtility util;
    
    public GuiPrint(IPrintHelper helper)
    {
        this(helper, new RenderUtility());
    }
    
    public GuiPrint(IPrintHelper helper, RenderUtility util)
    {
        this.helper = helper;
        this.clicked = false;
        this.util = util;
        
        this.helper.onGuiOpen(this);
    }
    
    @Override
    public void initGui()
    {
        this.inner = RenderUtility.Rectangle.fromXYWH(0, 0, this.width, this.height);
        this.sr = new ScaledResolution(this.mc);
        this.updateLineWidth();
        
        this.helper.onGuiInit(this);
    }
    
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        
        this.helper.onGuiClose(this);
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
        
        RenderUtility.scissorStart(this.sr, this.inner.x, this.inner.y, this.inner.w, this.inner.h);
        RenderUtility.applyZoom(this.getPrint().zoom); // Inside of the matrix since you would otherwise "touch" everything outside of the matrix
        GlStateManager.translate(this.getPrint().posX, this.getPrint().posY, 0); // Move everything in the print by the print's position
        this.drawInner(mouseX, mouseY, partialTicks);
        RenderUtility.scissorEnd();
        
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
    }
    
    public void updateLineWidth()
    {
        this.util.nodeFieldConnectionsWidth = (this.util.nodeFieldDotSize / 2) * this.getPrint().zoom * this.sr.getScaleFactor();
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
            this.getPrint().posY -= GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_S || keyCode == Keyboard.KEY_DOWN)
        {
            this.getPrint().posY += GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_A || keyCode == Keyboard.KEY_LEFT)
        {
            this.getPrint().posX -= GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_D || keyCode == Keyboard.KEY_RIGHT)
        {
            this.getPrint().posX += GuiPrint.scrollSpeedInner;
        }
        if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ADD)
        {
            this.getPrint().zoom *= 2;
            
            if (this.getPrint().zoom > 2)
            {
                this.getPrint().zoom = 2;
            }
            else
            {
                // TODO low: Adjust print position so that the middle of the screen stays the middle when zooming
            }
            
            this.updateLineWidth();
        }
        if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_SUBTRACT)
        {
            this.getPrint().zoom *= 0.5F;
            
            if (this.getPrint().zoom < 0.125F)
            {
                this.getPrint().zoom = 0.125F;
            }
            else
            {
                // TODO low: Adjust print position so that the middle of the screen stays the middle when zooming
            }
            
            this.updateLineWidth();
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
                }
                else if (obj instanceof NodeField)
                {
                    this.clicked = true;
                    this.mouseClickedField = (NodeField) obj;
                }
            }
            else
            {
                if (this.mouseClickedNode != null)
                {
                    this.mouseClickedNode.posX = this.mouseXToPrint(mouseX);
                    this.mouseClickedNode.posY = this.mouseYToPrint(mouseY);
                }
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
        this.drawPrint(this.getPrint());
        
        // --- Draw hovering/clicked start ---
        
        if (this.clicked)
        {
            // Objects that the player clicked on to be outlined
            if (this.mouseClickedNode != null)
            {
                // Outline clicked on node
                
                this.drawOutlineRect(this.printToGuiRounded(mouseX) - this.getPrint().posX, this.printToGuiRounded(mouseY) - this.getPrint().posY, this.util.nodeWidth, this.util.getNodeTotalHeight(this.mouseClickedNode));
            }
            if (this.mouseClickedField != null)
            {
                // Where to draw the 1st dot at
                int dotX = this.getDotPosX(this.mouseClickedField);
                int dotY = this.getDotPosY(this.mouseClickedField);
                
                if (this.mouseHoveringField != null && NodeField.canConnect(this.mouseClickedField, this.mouseHoveringField))
                {
                    this.drawHoverRect(this.getNodePosX(this.mouseHoveringField.node) + (this.mouseHoveringField.isInput() ? 0 : this.util.nodeWidth / 2), this.getNodePosY(this.mouseHoveringField.node) + this.util.nodeHeight * (this.mouseHoveringField.id + 1), this.util.nodeWidth / 2, this.util.nodeHeight);
                }
                
                // Node field was clicked on -> Render line from Dot -> Mouse
                RenderUtility.drawGradientLine(dotX + this.util.nodeFieldDotSize / 2, dotY + this.util.nodeFieldDotSize / 2, this.printToGuiRounded(mouseX) - this.getPrint().posX, this.printToGuiRounded(mouseY) - this.getPrint().posY, this.util.getLineWidth(this.mouseClickedField.dataType), this.mouseClickedField.dataType.getColor()[0], this.mouseClickedField.dataType.getColor()[1], this.mouseClickedField.dataType.getColor()[2], this.util.nodeFieldConnectionsAlpha, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldDef, this.util.nodeFieldConnectionsAlpha);
            }
        }
        else
        {
            // Nothing has been clicked on, render normal hovering rects
            
            /*
             * if (this.mouseHoveringNode != null) // Moved to #drawNode { // Node hover rect this.drawHoverRect(this.getNodePosX(this.mouseHoveringNode), this.getNodePosY(this.mouseHoveringNode), this.util.nodeWidth, this.util.nodeHeight * this.util.getVerticalAmt(this.mouseHoveringNode)); } if (this.mouseHoveringField != null) { // Node field hover rect this.drawHoverRect(this.getNodePosX(this.mouseHoveringField.node) + (this.mouseHoveringField.isInput() ? 0 : this.util.nodeWidth / 2), this.getNodePosY(this.mouseHoveringField.node) + this.util.nodeHeight * (this.mouseHoveringField.id + 1), this.util.nodeWidth / 2, this.util.nodeHeight); }
             */
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
        
        for (Node node : print.getNodes())
        {
            x = this.getNodePosX(node);
            y = this.getNodePosY(node);
            this.util.drawNodeConnections(node, x, y);
        }
    }
    
    /**
     * Draw a node and render a white rectangle over it when hovering with the mouse
     */
    public void drawNode(Node node, int x, int y)
    {
        this.util.drawNode(node, x, y);
        
        if (!this.clicked)
        {
            if (node == this.mouseHoveringNode)
            {
                // Node hover rect
                this.util.drawNodeHover(node, x, y);
            }
            if (this.mouseHoveringField != null && node == this.mouseHoveringField.node)
            {
                // Node field hover rect
                this.util.drawNodeFieldHover(this.mouseHoveringField, x, y);
            }
        }
    }
    
    /**
     * Returns the object the mouse is hovering over, can be a node or a node field
     */
    public Object getObjectHovering(int mouseX, int mouseY, int x, int y, int w, int h)
    {
        if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
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
        for (int i = this.getPrint().nodes.size() - 1; i >= 0; --i)
        {
            node = this.getPrint().nodes.get(i);
            
            // Entire node position and size, zoom and shift accounted for
            x = this.getNodePosX2(node);
            y = this.getNodePosY2(node);
            w = this.guiToPrint(this.util.nodeWidth);
            h = this.guiToPrint(this.util.getNodeTotalHeight(node));
            
            // Check if the mouse is on top of the entire node
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, this.guiToPrint(this.util.nodeHeight)))
                {
                    // Inside header -> return node itself
                    
                    return node;
                }
                else
                {
                    // Not inside header -> node fields
                    
                    int j;
                    
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, this.guiToPrint(this.util.fieldWidth), h))
                    {
                        // Left side -> inputs
                        
                        for (j = 1; j <= node.getInputAmt(); ++j)
                        {
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, this.guiToPrint(this.getPrint().posY + this.getNodePosY(node) + this.util.nodeHeight * j), w, this.guiToPrint(this.util.nodeHeight)))
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
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, this.guiToPrint(this.getPrint().posY + this.getNodePosY(node) + this.util.nodeHeight * j), w, this.guiToPrint(this.util.nodeHeight)))
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
    
    public Print getPrint()
    {
        return this.helper.getPrint(this);
    }
    
    /**
     * Apply the zoom factor.
     */
    public float guiToPrint(int i)
    {
        return i * this.getPrint().zoom;
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
        return Math.round(i / this.getPrint().zoom);
    }
    
    public int mouseXToPrint(int mouseX)
    {
        return this.printToGuiRounded(mouseX) - this.getPrint().posX;
    }
    
    public int mouseYToPrint(int mouseY)
    {
        return this.printToGuiRounded(mouseY) - this.getPrint().posY;
    }
    
    /**
     * Print posX + Node posX
     */
    public int getNodePosX(Node n)
    {
        return /* this.getPrint().posX + */n.posX;
    }
    
    /**
     * Print posY + Node posY
     */
    public int getNodePosY(Node n)
    {
        return /* .getPrint().posY + */n.posY;
    }
    
    /**
     * (Print posX + Node posX) * zoom
     */
    public float getNodePosX2(Node n)
    {
        return this.guiToPrint(this.getPrint().posX + n.posX);
    }
    
    /**
     * (Print posY + Node posY) * zoom
     */
    public float getNodePosY2(Node n)
    {
        return this.guiToPrint(this.getPrint().posY + n.posY);
    }
    
    /**
     * Get the node field dot posX
     */
    public int getDotPosX(NodeField field)
    {
        return this.getNodePosX(field.node) + this.util.getFieldOffX(field) + this.util.getDotOffX(field);
    }
    
    /**
     * Get the node field dot posY
     */
    public int getDotPosY(NodeField field)
    {
        return this.getNodePosY(field.node) + this.util.getFieldOffY(field) + this.util.getDotOffY(field);
    }
    
    /**
     * Draw the white rectangle when hovering over an object. This can later be changed to eg. an outline (which is why I made this a method)
     */
    public void drawHoverRect(int x, int y, int w, int h)
    {
        RenderUtility.drawRect(x, y, w, h, 1F, 1F, 1F, this.util.hoverAlpha);
    }
    
    /**
     * Draw the white border over an object.
     */
    public void drawOutlineRect(int x, int y, int w, int h)
    {
        RenderUtility.drawRect(x, y, w, 2, 1F, 1F, 1F, this.util.hoverAlpha); // TOP, x -> x + w
        RenderUtility.drawRect(x, y + h - 2, w, 2, 1F, 1F, 1F, this.util.hoverAlpha);// BOT, x -> x + w
        RenderUtility.drawRect(x, y + 2, 2, h - 4, 1F, 1F, 1F, this.util.hoverAlpha);// LEFT, y + 2 -> y + h - 2
        RenderUtility.drawRect(x + w - 2, y + 2, 2, h - 4, 1F, 1F, 1F, this.util.hoverAlpha);// RIGHT, y + 2 -> y + h - 2
    }
}

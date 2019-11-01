package de.cas_ual_ty.visibilis.print.gui;

import org.lwjgl.input.Keyboard;

import de.cas_ual_ty.visibilis.datatype.DataTypeDynamic;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import de.cas_ual_ty.visibilis.print.GuiPrintOld;
import de.cas_ual_ty.visibilis.print.IPrintHelper;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.gui.MouseInteractionObject.MouseInteractionType;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class WindowPrint extends WindowBase
{
    public final MouseInteractionObject clickedObj = new MouseInteractionObject();
    
    public WindowPrint(PrintUILogic guiPrint, RenderUtility util, IPrintHelper helper)
    {
        super(guiPrint, util, helper);
        
        this.fieldInput = new GuiTextField(0, this.util.fontRenderer, 0, 0, 0, 0);
        this.fieldInput.setVisible(false);
    }
    
    @Override
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.updateMouseHoveringObj(mouseX, mouseY);
        
        if (!this.isHoverViable()) //Throw out the hover obj right away if it is not viable at this state
        {
            this.hoverObj.nothing();
        }
        
        GlStateManager.pushMatrix();
        
        GlStateManager.pushMatrix();
        RenderUtility.scissorStart(this.getSR(), this.dimensions.x, this.dimensions.y, this.dimensions.w, this.dimensions.h);
        RenderUtility.applyZoom(this.getPrint().zoom); // Inside of the matrix since you would otherwise "touch" everything outside of the matrix
        GlStateManager.translate(this.getPrint().posX, this.getPrint().posY, 0); // Move everything in the print by the print's position
        
        this.drawPrint(this.getPrint());
        this.drawInteractions(mouseX, mouseY, partialTicks);
        
        RenderUtility.scissorEnd();
        GlStateManager.popMatrix();
        
        // Draw outside of zoom, shift and scissor
        this.drawHoverText(mouseX, mouseY);
        
        GlStateManager.popMatrix();
    }
    
    @Override
    public void guiKeyTyped(char typedChar, int keyCode)
    {
        if (this.mouseOverDimensions)
        {
            if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ADD)
            {
                this.zoomIn();
            }
            else if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_SUBTRACT)
            {
                this.zoomOut();
            }
        }
    }
    
    @Override
    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (this.mouseOverDimensions)
        {
            if (this.clickedObj.isNothing())
            {
                if (this.hoverObj.type == MouseInteractionType.NODE_ACTION_EXPAND)
                {
                    this.hoverObj.node.expand();
                }
                else if (this.hoverObj.type == MouseInteractionType.NODE_ACTION_SHRINK)
                {
                    this.hoverObj.node.shrink();
                }
                else if (this.hoverObj.type == MouseInteractionType.NODE_HEADER
                                || this.hoverObj.type == MouseInteractionType.NODE_FIELD)
                {
                    this.setHoverToClicked();
                }
            }
        }
    }
    
    public void setHoverToClicked()
    {
        this.clickedObj.inherit(this.hoverObj);
    }
    
    public void updateMouseHoveringObj(int mouseX0, int mouseY0)
    {
        if(!this.dimensions.isCoordInside(mouseX0, mouseY0))
        {
            return;
        }
        
        float mouseX = this.printToGui(mouseX0);
        float mouseY = this.printToGui(mouseY0);
        
        Node node;
        float x, y, w, h; // Rect of node
        float h2; // Header height
        
        // Loop from back to front, as those are on top
        for (int i = this.getPrint().getNodes().size() - 1; i >= 0; --i)
        {
            node = this.getPrint().getNodes().get(i);
            
            // Entire node position and size, zoom and shift accounted for
            x = this.getAbsNodePosX(node);
            y = this.getAbsNodePosY(node);
            w = this.util.nodeWidth;
            h = this.util.getNodeTotalHeight(node);
            h2 = this.util.nodeHeight;
            
            // Check if the mouse is on top of the entire node
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h2))
                {
                    // Inside header -> return node itself
                    
                    this.hoverObj.nodeHeader(node);
                }
                else if (node.hasFooter() && RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y + h2 * (RenderUtility.getVerticalAmt(node) - 1), w, h2))
                {
                    // Left side
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, this.util.fieldWidth, h))
                    {
                        if (node.canExpand())
                        {
                            this.hoverObj.nodeActionExpand(node);
                        }
                    }
                    // Right Side
                    else
                    {
                        if (node.canShrink())
                        {
                            this.hoverObj.nodeActionShrink(node);
                        }
                    }
                }
                else
                {
                    // Not inside header & footer -> node fields
                    
                    int j;
                    
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, this.util.fieldWidth, h))
                    {
                        // Left side -> inputs
                        
                        for (j = 1; j <= node.getInputAmt(); ++j)
                        {
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y + this.util.nodeHeight * j, w, h2))
                            {
                                // inside this node field -> return it
                                
                                this.hoverObj.nodeField(node.getInput(j - 1));
                            }
                        }
                    }
                    else
                    {
                        // Right side -> outputs
                        
                        for (j = 1; j <= node.getOutputAmt(); ++j)
                        {
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y + this.util.nodeHeight * j, w, h2))
                            {
                                // inside this node field -> return it
                                
                                this.hoverObj.nodeField(node.getOutput(j - 1));
                            }
                        }
                    }
                }
                
                // No node field found.
                // Which basically means that it is next to one of the fields, depending on if there are more outputs or inputs
            }
            //Check if it is left of the node, over possible input enums
            //But these options only appear if a node field was already clicked
            else if(node == this.clickedObj.node && this.clickedObj.type == MouseInteractionType.NODE_FIELD && this.clickedObj.nodeField.isInput())
            {
                Input input = (Input) this.clickedObj.nodeField;
                
                if(input.dataType instanceof DataTypeEnum)
                {
                    DataTypeEnum dt = (DataTypeEnum) input.dataType;
                    
                    float h3;
                    
                    h3 = this.util.nodeHeight;
                    
                    y += this.util.getFieldOffY(input);
                    
                    // Loop through enums of the data type
                    for (int j = 0; j < dt.getEnumSize(); ++j)
                    {
                        //We are drawing towards the top
                        y -= j * h3;
                        
                        // If mouse is hovering over said rect, whiten it
                        if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
                        {
                            this.hoverObj.inputEnum(input, j);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Draw a print and all its nodes and connections at the given coordinates
     */
    public void drawPrint(Print print)
    {
        int x;
        int y;
        
        // Loop through all nodes. Nodes at the end of the list will be drawn on top, so front to back is fine
        for (Node node : print.getNodes())
        {
            x = node.posX;
            y = node.posY;
            this.drawNode(node, x, y);
        }
        
        // Loop through all the nodes again, but draw the connections on top now
        // TODO low: figure out a better way to draw the node connections, right now they are drawn after drawing *all* the nodes.
        for (Node node : print.getNodes())
        {
            x = node.posX;
            y = node.posY;
            this.util.drawNodeConnections(node, x, y);
        }
    }
    
    /**
     * Draw a node and render a white rectangle over it when hovering with the mouse
     */
    public void drawNode(Node node, int x, int y)
    {
        this.util.drawNode(node, x, y);
        this.util.drawNodeInputValues(node, x, y);
        
        this.drawNodeInteractions(node, x, y);
    }
    
    /**
     * Draw the node interactions (hovering rect over node or its fields)
     */
    public void drawNodeInteractions(Node node, int x, int y)
    {
        if (node == this.hoverObj.node)
        {
            if (this.hoverObj.type == MouseInteractionType.NODE_ACTION_EXPAND)
            {
                this.util.drawExpansionHover(node, x, y);
            }
            else if (this.hoverObj.type == MouseInteractionType.NODE_ACTION_SHRINK)
            {
                this.util.drawShrinkHover(node, x, y);
            }
            else if (this.hoverObj.type == MouseInteractionType.NODE_HEADER)
            {
                this.util.drawNodeHover(node, x, y);
            }
            else if (this.hoverObj.type == MouseInteractionType.NODE_FIELD)
            {
                this.util.drawNodeFieldHover(this.hoverObj.nodeField, x, y);
            }
        }
    }
    
    /**
     * Essentially, this is to draw every interaction last (last = on top).
     * Including but not excluded to: hovering, connections, enum/dyn data types
     */
    public void drawInteractions(int mouseX, int mouseY, float partialTicks)
    {
        // Something was already clicked on before / attached to mouse / focused
        if (!this.clickedObj.isNothing())
        {
            if (this.clickedObj.type == MouseInteractionType.NODE_HEADER)
            {
                // node on mouse
                this.util.drawNodeSelect(this.clickedObj.node, this.clickedObj.node.posX, this.clickedObj.node.posY);
                
                // Outline clicked on node
                this.util.drawOutlineRect(this.mouseXToPrintRounded(mouseX), this.mouseYToPrintRounded(mouseY), this.util.nodeWidth, this.util.getNodeTotalHeight(this.clickedObj.node));
            }
            if (this.clickedObj.type == MouseInteractionType.NODE_FIELD)
            {
                // field on mouse
                
                if (this.clickedObj.nodeField.isOutput())
                {
                    // output on mouse
                    
                    // Draw connection line: Dot to Mouse
                    
                    // Where to draw the 1st dot at
                    int dotX = this.getDotPosX(this.clickedObj.nodeField);
                    int dotY = this.getDotPosY(this.clickedObj.nodeField);
                    
                    if (this.hoverObj.type == MouseInteractionType.NODE_FIELD)
                    {
                        this.util.drawNodeFieldHover(this.hoverObj.nodeField, this.hoverObj.node.posX, this.hoverObj.node.posY);
                    }
                    
                    // Node field was clicked on -> Render line from Dot to Mouse
                    RenderUtility.drawGradientLine(dotX + this.util.nodeFieldDotSize / 2, dotY + this.util.nodeFieldDotSize / 2, this.mouseXToPrintRounded(mouseX), this.mouseYToPrintRounded(mouseY), this.util.getLineWidth(this.clickedObj.nodeField.dataType), this.clickedObj.nodeField.dataType.getColor()[0], this.clickedObj.nodeField.dataType.getColor()[1], this.clickedObj.nodeField.dataType.getColor()[2], this.util.nodeFieldConnectionsAlpha, GuiPrintOld.nodeFieldDef, GuiPrintOld.nodeFieldDef, GuiPrintOld.nodeFieldDef, this.util.nodeFieldConnectionsAlpha);
                }
                else
                {
                    //input on mouse
                    
                    Input input = (Input) this.clickedObj.nodeField;
                    
                    // Check if clicked input has an immediate value
                    if (input.hasDisplayValue())
                    {
                        // Enum data type?
                        if (input.dataType instanceof DataTypeDynamic)
                        {
                            // Draw the text box
                            this.fieldInput.drawTextBox();
                        }
                        // Dynamic data type?
                        else if (input.dataType instanceof DataTypeEnum)
                        {
                            int x, y, w, h;
                            
                            w = this.util.inputValueWidth;
                            h = this.util.nodeHeight;
                            
                            // "- w" because we want to draw these options to the left of the input.
                            x = input.node.posX - w;
                            y = input.node.posY + this.util.getFieldOffY(input);
                            
                            // Draw the enum options
                            this.util.drawInputEnums(input, x, y);
                            
                            if (this.hoverObj.type == MouseInteractionType.INPUT_ENUM)
                            {
                                this.util.drawHoverRect(x, y - h * this.hoverObj.inputEnumId, w, h);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void drawHoverText(int mouseX, int mouseY)
    {
        if (this.hoverObj.type == MouseInteractionType.NODE_HEADER)
        {
            this.util.drawNodeHoveringText(this.hoverObj.node, mouseX, mouseY);
        }
        else if (this.hoverObj.type == MouseInteractionType.NODE_FIELD)
        {
            this.util.drawNodeFieldHoveringText(this.hoverObj.nodeField, mouseX, mouseY);
        }
    }
    
    public void zoomIn()
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
        
        this.util.updateLineWidth(this.getPrint());
    }
    
    public void zoomOut()
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
        
        this.util.updateLineWidth(this.getPrint());
    }
    
    public boolean isHoverViable()
    {
        if (this.hoverObj.isNothing())
        {
            return false;
        }
        
        if (this.clickedObj.isNothing())
        {
            if (this.hoverObj.type == MouseInteractionType.NODE_FIELD)
            {
                if (this.hoverObj.nodeField.isOutput())
                {
                    return true;
                }
                else
                {
                    Input input = (Input) this.hoverObj.nodeField;
                    
                    return input.hasDisplayValue() && (input.dataType instanceof DataTypeDynamic || input.dataType instanceof DataTypeEnum);
                }
            }
            else if (this.hoverObj.type == MouseInteractionType.NODE_ACTION_EXPAND)
            {
                return this.hoverObj.node.canExpand();
            }
            else if (this.hoverObj.type == MouseInteractionType.NODE_ACTION_SHRINK)
            {
                return this.hoverObj.node.canShrink();
            }
        }
        else
        {
            return this.isHoverViableClicked();
        }
        
        return false;
    }
    
    public boolean isHoverViableClicked()
    {
        if (this.hoverObj.type == MouseInteractionType.NODE_FIELD)
        {
            return NodeField.canConnect(this.clickedObj.nodeField, this.hoverObj.nodeField);
        }
        
        return false;
    }
    
    /**
     * Remove zoom factor
     */
    public float printToGui(int i)
    {
        return ((float) i) / this.getPrint().zoom;
    }
    
    /**
     * Remove zoom factor and round
     */
    public int printToGuiRounded(int i)
    {
        return Math.round(this.printToGui(i));
    }
    
    /**
     * Print posX + Node posX
     */
    public int getAbsNodePosX(Node n)
    {
        return this.getPrint().posX + n.posX;
    }
    
    /**
     * Print posY + Node posY
     */
    public int getAbsNodePosY(Node n)
    {
        return this.getPrint().posY + n.posY;
    }
    
    /**
     * Remove zoom factor and remove print x offset
     */
    public float mouseXToPrint(int mouseX)
    {
        return this.printToGui(mouseX) - this.getPrint().posX;
    }
    
    /**
     * Remove zoom factor and remove print y offset
     */
    public float mouseYToPrint(int mouseY)
    {
        return this.printToGui(mouseY) - this.getPrint().posY;
    }
    
    /**
     * Remove zoom factor, round and remove print x offset
     */
    public int mouseXToPrintRounded(int mouseX)
    {
        return this.printToGuiRounded(mouseX) - this.getPrint().posX;
    }
    
    /**
     * Remove zoom factor, round and remove print y offset
     */
    public int mouseYToPrintRounded(int mouseY)
    {
        return this.printToGuiRounded(mouseY) - this.getPrint().posY;
    }
    
    /**
     * Get the node field dot posX
     */
    public int getDotPosX(NodeField field)
    {
        return field.node.posX + this.util.getFieldOffX(field) + this.util.getDotOffX(field);
    }
    
    /**
     * Get the node field dot posY
     */
    public int getDotPosY(NodeField field)
    {
        return field.node.posY + this.util.getFieldOffY(field) + this.util.getDotOffY(field);
    }
}

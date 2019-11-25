package de.cas_ual_ty.visibilis.print.ui.component;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.GlStateManager;

import de.cas_ual_ty.visibilis.datatype.DataTypeDynamic;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.ui.RenderUtility;
import de.cas_ual_ty.visibilis.print.ui.UiBase;
import de.cas_ual_ty.visibilis.print.ui.util.MouseInteractionObject;
import de.cas_ual_ty.visibilis.print.ui.util.MouseInteractionObject.EnumMouseInteractionType;
import de.cas_ual_ty.visibilis.print.ui.util.NodeActionWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class ComponentPrint extends Component
{
    /*
     * Okay, whats the idea here?
     * 
     * Step 1: Store the object hovered over in hoverObj no matter if viable/clickable or not
     * Step 2: Throw out the non viable (= non clickable ones)
     * Step 3: Do the interaction logic
     */
    
    public final MouseInteractionObject clickedObj = new MouseInteractionObject();
    
    public final TextFieldWidget fieldInput;
    
    public int tmpOffX;
    public int tmpOffY;
    
    public int keyPressed = -1;
    public int keyPressedDelay = 0;
    
    public NodeActionWidget rightClickMenu;
    
    public ComponentPrint(UiBase guiPrint)
    {
        super(guiPrint);
        
        this.fieldInput = new TextFieldWidget(this.getUtil().fontRenderer, 0, 0, 0, 0, "");
        this.fieldInput.setVisible(false);
        this.fieldInput.setFocused2(false);
        this.fieldInput.setCanLoseFocus(false);
        this.rightClickMenu = null;
    }
    
    @Override
    public void guiInitGui()
    {
        this.getUtil().updateLineWidth(this.getPrint());
    }
    
    @Override
    public void guiRender(int mouseX, int mouseY, float partialTicks)
    {
        // Background & show zoom on top left
        this.dimensions.render(0.5F, 0.25F, 0.25F);
        Minecraft.getInstance().fontRenderer.drawString(this.getPrint().zoom + "x", 1 + this.dimensions.x, 1 + this.dimensions.y, 0xFFFFFF);
        
        // Check what the mouse is over and properly hold it in hoverObj
        this.updateMouseHoveringObj(mouseX, mouseY);
        
        // Check if the hover is actually viable and throw it out if not
        if (!this.isHoverViable())
        {
            this.hoverObj.nothing();
        }
        
        // Scissor by dimensions rect, apply print zoom, translate by print coords
        GlStateManager.pushMatrix();
        RenderUtility.scissorStart(this.getSR(), this.dimensions.x, this.dimensions.y, this.dimensions.w, this.dimensions.h);
        RenderUtility.applyZoom(this.getPrint().zoom); // Inside of the matrix since you would otherwise "touch" everything outside of the matrix
        GlStateManager.translatef(this.getPrint().posX, this.getPrint().posY, 0); // Move everything in the print by the print's position
        
        // Draw the Print: All nodes, fields, connections
        this.drawPrint(this.getPrint());
        
        // Draw special interactions which need to be rendered last
        this.drawInteractions(mouseX, mouseY, partialTicks);
        
        RenderUtility.scissorEnd();
        GlStateManager.popMatrix();
    }
    
    // Draw outside of zoom, shift and scissor
    @Override
    public void guiPostRender(int mouseX, int mouseY, float partialTicks)
    {
        if (this.rightClickMenu != null)
        {
            this.rightClickMenu.guiRender(mouseX, mouseY, partialTicks);
        }
        this.drawHoverText(mouseX, mouseY);
    }
    
    @Override
    public void guiTick()
    {
        if (this.mouseOverDimensions)
        {
            if (!this.updateKeyPressedDelay())
            {
                return;
            }
            
            // Reset position to 0
            if (this.keyPressed == GLFW.GLFW_KEY_SPACE)
            {
                this.getPrint().posX = 0;
                this.getPrint().posY = 0;
            }
            // zoom in
            else if (this.keyPressed == GLFW.GLFW_KEY_KP_ADD)
            {
                this.zoomIn(this.uiBase.lastMousePosX, this.uiBase.lastMousePosY);
            }
            // zoom out
            else if (this.keyPressed == GLFW.GLFW_KEY_KP_SUBTRACT)
            {
                this.zoomOut(this.uiBase.lastMousePosX, this.uiBase.lastMousePosY);
            }
            // move print up
            if (this.keyPressed == GLFW.GLFW_KEY_W || this.keyPressed == GLFW.GLFW_KEY_UP)
            {
                this.getPrint().posY -= 8 * 2 / this.getPrint().zoom;
            }
            // move print down
            if (this.keyPressed == GLFW.GLFW_KEY_S || this.keyPressed == GLFW.GLFW_KEY_DOWN)
            {
                this.getPrint().posY += 8 * 2 / this.getPrint().zoom;
            }
            // move print left
            if (this.keyPressed == GLFW.GLFW_KEY_A || this.keyPressed == GLFW.GLFW_KEY_LEFT)
            {
                this.getPrint().posX -= 8 * 2 / this.getPrint().zoom;
            }
            // move print right
            if (this.keyPressed == GLFW.GLFW_KEY_D || this.keyPressed == GLFW.GLFW_KEY_RIGHT)
            {
                this.getPrint().posX += 8 * 2 / this.getPrint().zoom;
            }
        }
        super.guiTick();
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        if (this.fieldInput.getVisible())
        {
            if (keyCode == GLFW.GLFW_KEY_ENTER)
            {
                this.getProvider().saveChange();
                // Return clicked, unfocus and apply test
                this.setUnfocusTextField(this.clickedObj.input);
                return true;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.fieldInput.charTyped(typedChar, keyCode);
                return true;
            }
        }
        
        return super.charTyped(typedChar, keyCode);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        if (this.mouseOverDimensions)
        {
            if (modifiers == 0)
            {
                if (this.rightClickMenu != null)
                {
                    this.getProvider().saveChange();
                    this.rightClickMenu.mouseClicked(mouseX, mouseY, modifiers);
                    this.rightClickMenu = null;
                }
                else if (this.clickedObj.isNothing())
                {
                    // Nothing was selected before, now user clicks on something hovered over
                    
                    // User clicked on the header. This might be initiation of a click move.
                    if (this.hoverObj.type == EnumMouseInteractionType.NODE_HEADER)
                    {
                        this.getProvider().saveChange();
                        // Save off set of the mouse and the top left corner of the header (= the print position)
                        this.tmpOffX = this.mouseXToPrintRounded(mouseX) - this.hoverObj.node.posX;// - this.getPrint().posX;
                        this.tmpOffY = this.mouseYToPrintRounded(mouseY) - this.hoverObj.node.posY;// - this.getPrint().posY;
                        this.setHoverToClicked();
                        return true;
                    }
                    else if (this.hoverObj.type == EnumMouseInteractionType.OUTPUT)
                    {
                        this.setHoverToClicked();
                        return true;
                    }
                    else if (this.hoverObj.type == EnumMouseInteractionType.INPUT)
                    {
                        // Input was clicked
                        
                        if (this.hoverObj.input.hasConnections())
                        {
                            this.getProvider().saveChange();
                            // Cut all connections
                            this.hoverObj.input.cutConnections();
                        }
                        
                        if (this.hoverObj.input.hasDisplayValue())
                        {
                            // Check if you can put in an immediate value
                            
                            if (this.hoverObj.input.dataType instanceof DataTypeEnum)
                            {
                                //Immediate value of an enum, update clickedObj accordingly
                                this.clickedObj.inputEnum(this.hoverObj.input);
                            }
                            else if (this.hoverObj.input.dataType instanceof DataTypeDynamic)
                            {
                                //Dynamic immediate value, update clickedObj accordingly
                                this.clickedObj.inputDynamic(this.hoverObj.input);
                                this.setFocusTextField(this.clickedObj.input); // Enable text field already an set coords and size appropriately
                            }
                        }
                        
                        this.hoverObj.nothing(); // Dont know why I put this, maybe unnecessary idk
                        
                        return true;
                    }
                }
                else
                {
                    // Something was selected before, now the user clicked on something new
                    // => do the interaction
                    
                    // Output was selected, user now clicks on Input
                    if (this.clickedObj.type == EnumMouseInteractionType.OUTPUT && this.hoverObj.type == EnumMouseInteractionType.INPUT)
                    {
                        if (NodeField.canConnect(this.clickedObj.nodeField, this.hoverObj.nodeField))
                        {
                            this.getProvider().saveChange();
                            NodeField.connect(this.clickedObj.nodeField, this.hoverObj.nodeField);
                            return true;
                        }
                    }
                    // Dynamic input was selected before (text field focused)
                    else if (this.clickedObj.type == EnumMouseInteractionType.INPUT_DYNAMIC)
                    {
                        // If you click on the same input again, do not deselect (return) and transfer the interaction to the fieldInput itself
                        if (this.clickedObj.nodeField == this.hoverObj.nodeField)
                        {
                            this.fieldInput.mouseClicked(this.mouseXToPrintRounded(mouseX), this.mouseYToPrintRounded(mouseY), modifiers);
                            return true;
                        }
                        // You clicked on something different => deselect input
                        else
                        {
                            this.getProvider().saveChange();
                            this.setUnfocusTextField(this.clickedObj.input);
                        }
                    }
                    // Enum input was selected before, hoverObj CAN ONLY be one of the enum values (or nothing).
                    // If it is not nothing, set the enum value
                    else if (this.clickedObj.type == EnumMouseInteractionType.INPUT_ENUM && this.hoverObj.type == EnumMouseInteractionType.INPUT_ENUM_ID)
                    {
                        this.getProvider().saveChange();
                        this.clickedObj.input.setValue(((DataTypeEnum) this.clickedObj.input.dataType).getEnum(this.hoverObj.inputEnumId));
                    }
                    
                    // Deselect whatever you had selected before (a premature return statement stops this from happening)
                    this.clickedObj.nothing();
                }
            }
            else if (modifiers == 1)
            {
                if (this.hoverObj.type == EnumMouseInteractionType.NODE_HEADER && this.hoverObj.node.hasActions())
                {
                    this.clickedObj.nothing();
                    this.rightClickMenu = new NodeActionWidget(this, (int) Math.round(mouseX), (int) Math.round(mouseY), this.dimensions, this.getProvider().getActionsForNode(this.hoverObj.node));
                }
            }
        }
        
        return super.mouseClicked(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int modifiers, double deltaX, double deltaY)
    {
        // Clicked obj is the header, now the user drags
        // => move the node. Keep offset that was set before
        if (this.clickedObj.type == EnumMouseInteractionType.NODE_HEADER)
        {
            this.clickedObj.node.posX = this.mouseXToPrintRounded(mouseX) - this.tmpOffX;
            this.clickedObj.node.posY = this.mouseYToPrintRounded(mouseY) - this.tmpOffY;
            return true;
        }
        
        return super.mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int modifiers)
    {
        // Clicked obj is the header, since the action requires you to drag deselect it immediately on release
        if (this.clickedObj.type == EnumMouseInteractionType.NODE_HEADER)
        {
            this.clickedObj.nothing();
            return true;
        }
        
        return super.mouseReleased(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (this.fieldInput.getVisible())
        {
            if (keyCode == GLFW.GLFW_KEY_ENTER)
            {
                this.getProvider().saveChange();
                // Return clicked, unfocus and apply test
                this.setUnfocusTextField(this.clickedObj.input);
                return true;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.fieldInput.keyPressed(keyCode, scanCode, modifiers);
                return true;
            }
        }
        
        this.updateKeyPressed(keyCode, 0);
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        this.updateKeyPressed(-1, 0);
        
        if (this.fieldInput.getVisible())
        {
            if (keyCode == GLFW.GLFW_KEY_ENTER)
            {
                this.getProvider().saveChange();
                // Return clicked, unfocus and apply test
                this.setUnfocusTextField(this.clickedObj.input);
                return true;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.fieldInput.keyReleased(keyCode, scanCode, modifiers);
                return true;
            }
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean isolateInput()
    {
        return this.fieldInput.getVisible();
    }
    
    public void setHoverToClicked()
    {
        this.clickedObj.inherit(this.hoverObj);
    }
    
    public void updateKeyPressed(int keyCode, int delay)
    {
        this.keyPressed = keyCode;
        this.keyPressedDelay = delay;
    }
    
    public boolean updateKeyPressedDelay()
    {
        boolean ret = --this.keyPressedDelay <= 0;
        if (ret)
        {
            this.keyPressedDelay = 3;
        }
        return ret;
    }
    
    public void updateMouseHoveringObj(int mouseX0, int mouseY0)
    {
        this.hoverObj.nothing();
        
        // If the mouse is not inside the dimensions, there is nothing to hover over
        if (!this.dimensions.isCoordInside(mouseX0, mouseY0))
        {
            this.rightClickMenu = null;
            return;
        }
        
        if (this.rightClickMenu != null)
        {
            return;
        }
        
        // Adjust mouse to print position and zoom
        double mouseX = this.mouseXToPrint(mouseX0);
        double mouseY = this.mouseYToPrint(mouseY0);
        
        Node node;
        float x, y, w, h; // Rect of node
        
        w = this.getUtil().inputValueWidth;
        
        //Check if it is left of the node, over possible input enums
        //But these options only appear if a node field was already clicked
        if (this.clickedObj.type == EnumMouseInteractionType.INPUT_ENUM)
        {
            node = this.clickedObj.node;
            
            if (this.clickedObj.input.dataType instanceof DataTypeEnum)
            {
                x = node.posX - w;
                y = node.posY;
                h = this.getUtil().getNodeTotalHeight(node);
                
                DataTypeEnum dt = (DataTypeEnum) this.clickedObj.input.dataType;
                
                float h3;
                
                h3 = this.getUtil().nodeHeight;
                
                y += this.getUtil().getFieldOffY(this.clickedObj.input);
                
                // Loop through enums of the data type
                for (int j = 0; j < dt.getEnumSize(); ++j)
                {
                    //We are drawing towards the top
                    y -= j * h3;
                    
                    // If mouse is hovering over said rect, whiten it
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
                    {
                        this.hoverObj.inputEnumId(this.clickedObj.input, j);
                        return; // Enums have priority, return so it does not get overridden
                    }
                }
            }
            
            // if an input enum is selected, nothing else can be hovered over.
            // But, this gets thrown out in the isHoverViable() method.
            // So instead of putting a return here and always have nothing hovered, we check anyways.
            // This makes the code more extendable as you can now have more possible interactions by only overriding isHoverViable()
        }
        
        w = this.getUtil().nodeWidth;
        float h2; // Header height
        
        // Loop from back to front, as those are on top
        for (int i = this.getPrint().getNodes().size() - 1; i >= 0; --i)
        {
            node = this.getPrint().getNodes().get(i);
            
            // Entire node position and size, zoom and shift are already accounted for
            x = node.posX;
            y = node.posY;
            h = this.getUtil().getNodeTotalHeight(node);
            h2 = this.getUtil().nodeHeight;
            
            // Check if the mouse is on top of the entire node
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h2))
                {
                    // Inside header -> return node itself
                    
                    this.hoverObj.nodeHeader(node);
                    return;
                }
                else
                {
                    // Not inside header & footer -> node fields
                    
                    int j;
                    
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, this.getUtil().fieldWidth, h))
                    {
                        // Left side -> inputs
                        
                        for (j = 1; j <= node.getInputAmt(); ++j)
                        {
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y + this.getUtil().nodeHeight * j, w, h2))
                            {
                                // inside this node field -> return it
                                
                                this.hoverObj.input(node.getInput(j - 1));
                                return;
                            }
                        }
                    }
                    else
                    {
                        // Right side -> outputs
                        
                        for (j = 1; j <= node.getOutputAmt(); ++j)
                        {
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y + this.getUtil().nodeHeight * j, w, h2))
                            {
                                // inside this node field -> return it
                                
                                this.hoverObj.output(node.getOutput(j - 1));
                                return;
                            }
                        }
                    }
                }
                
                // No node field found.
                // Which basically means that it is next to one of the fields, depending on if there are more outputs or inputs
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
            this.getUtil().drawNodeConnections(node, x, y);
        }
    }
    
    /**
     * Draw a node and render a white rectangle over it when hovering with the mouse
     */
    public void drawNode(Node node, int x, int y)
    {
        this.getUtil().drawNode(node, x, y);
        this.getUtil().drawNodeInputValues(node, x, y);
        
        this.drawNodeInteractions(node, x, y);
    }
    
    /**
     * Draw the node interactions (hovering rect over node or its fields)
     */
    public void drawNodeInteractions(Node node, int x, int y)
    {
        if (node == this.hoverObj.node)
        {
            if (this.hoverObj.type == EnumMouseInteractionType.NODE_HEADER)
            {
                this.getUtil().drawNodeHover(node, x, y);
            }
            else if (this.hoverObj.type == EnumMouseInteractionType.OUTPUT || this.hoverObj.type == EnumMouseInteractionType.INPUT)
            {
                this.getUtil().drawNodeFieldHover(this.hoverObj.nodeField, x, y);
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
            if (this.clickedObj.type == EnumMouseInteractionType.OUTPUT)
            {
                // output on mouse
                
                // Draw connection line: Dot to Mouse
                
                // Where to draw the 1st dot at
                int dotX = this.getDotPosX(this.clickedObj.nodeField);
                int dotY = this.getDotPosY(this.clickedObj.nodeField);
                
                // Node field was clicked on -> Render line from Dot to Mouse
                RenderUtility.drawGradientLine(dotX + this.getUtil().nodeFieldDotSize / 2, dotY + this.getUtil().nodeFieldDotSize / 2, this.mouseXToPrintRounded(mouseX), this.mouseYToPrintRounded(mouseY), this.getUtil().getLineWidth(this.clickedObj.nodeField.dataType), this.clickedObj.nodeField.dataType.getColor()[0], this.clickedObj.nodeField.dataType.getColor()[1], this.clickedObj.nodeField.dataType.getColor()[2], this.getUtil().nodeFieldConnectionsAlpha, this.clickedObj.nodeField.dataType.getColor()[0], this.clickedObj.nodeField.dataType.getColor()[1], this.clickedObj.nodeField.dataType.getColor()[2], this.getUtil().nodeFieldConnectionsAlpha);
            }
            else if (this.clickedObj.type == EnumMouseInteractionType.INPUT_DYNAMIC)
            {
                // Draw the text box
                this.fieldInput.render(mouseX, mouseY, partialTicks);
            }
            else if (this.clickedObj.type == EnumMouseInteractionType.INPUT_ENUM)
            {
                int x, y, w, h;
                
                w = this.getUtil().inputValueWidth;
                h = this.getUtil().nodeHeight;
                
                // "- w" because we want to draw these options to the left of the input.
                x = this.clickedObj.input.node.posX - w;
                y = this.clickedObj.input.node.posY + this.getUtil().getFieldOffY(this.clickedObj.input);
                
                // Draw the enum options
                this.getUtil().drawInputEnums(this.clickedObj.input, x, y);
                
                if (this.hoverObj.type == EnumMouseInteractionType.INPUT_ENUM_ID)
                {
                    this.getUtil().drawHoverRect(x, y - h * this.hoverObj.inputEnumId, w, h);
                }
            }
        }
    }
    
    public void drawHoverText(int mouseX, int mouseY)
    {
        if (this.hoverObj.type == EnumMouseInteractionType.NODE_HEADER)
        {
            this.getUtil().drawNodeHoveringText(this.hoverObj.node, mouseX, mouseY);
        }
        else if (this.hoverObj.type == EnumMouseInteractionType.OUTPUT || this.hoverObj.type == EnumMouseInteractionType.INPUT)
        {
            this.getUtil().drawNodeFieldHoveringText(this.hoverObj.nodeField, mouseX, mouseY);
        }
    }
    
    public void zoomIn(double mouseX, double mouseY)
    {
        this.getPrint().zoom *= 2;
        
        if (this.getPrint().zoom > 2)
        {
            this.getPrint().zoom = 2;
        }
        else
        {
            this.getPrint().posX -= (mouseX) / this.getPrint().zoom;
            this.getPrint().posY -= (mouseY) / this.getPrint().zoom;
        }
        
        this.getUtil().updateLineWidth(this.getPrint());
    }
    
    public void zoomOut(double mouseX, double mouseY)
    {
        this.getPrint().zoom *= 0.5F;
        
        if (this.getPrint().zoom < 0.125F)
        {
            this.getPrint().zoom = 0.125F;
        }
        else
        {
            // We need the previous zoom here and 0.5F. current zoom is previous zoom * 0.5F.
            this.getPrint().posX += (mouseX * 0.5F) / this.getPrint().zoom;
            this.getPrint().posY += (mouseY * 0.5F) / this.getPrint().zoom;
        }
        
        this.getUtil().updateLineWidth(this.getPrint());
    }
    
    public void setUnfocusTextField(Input input)
    {
        if (input != null)
        {
            DataTypeDynamic dt = (DataTypeDynamic) input.dataType;
            
            // Can the string in the text box be applied to the input? Is it viable?
            if (dt.canParseString(this.fieldInput.getText()))
            {
                // Parse string and apply the parsed value to the input.
                input.setValue(dt.stringToValue(this.fieldInput.getText()));
            }
        }
        
        this.fieldInput.setVisible(false);
        this.fieldInput.setFocused2(false);
        this.clickedObj.nothing();
    }
    
    public void setFocusTextField(Input input)
    {
        DataTypeDynamic dt = (DataTypeDynamic) input.dataType;
        
        // Make it visible
        this.fieldInput.setVisible(true);
        
        // Adjust position to node field accordingly
        this.fieldInput.x = input.node.posX + this.getUtil().getFieldOffX(input);
        this.fieldInput.y = input.node.posY + this.getUtil().getFieldOffY(input);
        
        // Adjust size to node field accordingly
        this.fieldInput.setWidth(this.getUtil().fieldWidth);
        this.fieldInput.setHeight(this.getUtil().nodeHeight);
        
        // Set the text to the current immediate value the input is holding
        this.fieldInput.setText(dt.valueToString(input.getSetValue()));
        
        // Set it to be focused, otherwise it does not accept input
        this.fieldInput.setFocused2(true);
        
        // Set the string validator for immediate input feedback
        this.fieldInput.setValidator(dt.getValidator());
    }
    
    /**
     * Check current clicked and hovering state and throw out (= return <b>false</b>) those that are not viable.
     */
    public boolean isHoverViable()
    {
        /*
         * Default: false
         * Find all viable cases and return true
         */
        
        if (this.hoverObj.isNothing())
        {
            //Hover over nothing is viable
            return true;
        }
        
        if (this.clickedObj.isNothing())
        {
            // Nothing is clicked, but something is hovered over
            
            if (this.hoverObj.type == EnumMouseInteractionType.INPUT)
            {
                // Hovering over an input is viable, if it
                // - has connections (to disconnect)
                // - has an immediate value (and it can actually be put in - the data type is properly representable in this gui)
                return this.hoverObj.input.hasConnections() || (this.hoverObj.input.hasDisplayValue() && (this.hoverObj.input.dataType instanceof DataTypeDynamic || this.hoverObj.input.dataType instanceof DataTypeEnum));
            }
            
            // If nothing of the above is the case, then simply return true...
            // ... if the user is hovering over it, as output and header do not have any conditions
            // To be a viable hover
            return this.hoverObj.type == EnumMouseInteractionType.OUTPUT
                            || this.hoverObj.type == EnumMouseInteractionType.NODE_HEADER;
        }
        else
        {
            // Something is clicked on, and something is hovered over
            
            return this.isHoverViableClicked();
        }
    }
    
    public boolean isHoverViableClicked()
    {
        if (this.clickedObj.type == EnumMouseInteractionType.OUTPUT && this.hoverObj.type == EnumMouseInteractionType.INPUT)
        {
            return NodeField.canConnect(this.clickedObj.output, this.hoverObj.input);
        }
        else if (this.clickedObj.type == EnumMouseInteractionType.INPUT_ENUM && this.hoverObj.type == EnumMouseInteractionType.INPUT_ENUM_ID)
        {
            return true;
        }
        else if (this.clickedObj.type == EnumMouseInteractionType.INPUT_DYNAMIC)
        {
            return this.clickedObj.nodeField == this.hoverObj.nodeField;
        }
        
        return false;
    }
    
    /**
     * Remove zoom factor
     */
    public double printToGui(double i)
    {
        return i / this.getPrint().zoom;
    }
    
    /**
     * Remove zoom factor and round
     */
    public int printToGuiRounded(double i)
    {
        return (int) Math.round(this.printToGui(i));
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
    public double mouseXToPrint(double mouseX)
    {
        return this.printToGui(mouseX) - this.getPrint().posX;
    }
    
    /**
     * Remove zoom factor and remove print y offset
     */
    public double mouseYToPrint(double mouseY)
    {
        return this.printToGui(mouseY) - this.getPrint().posY;
    }
    
    /**
     * Remove zoom factor, round and remove print x offset
     */
    public int mouseXToPrintRounded(double mouseX)
    {
        return this.printToGuiRounded(mouseX) - this.getPrint().posX;
    }
    
    /**
     * Remove zoom factor, round and remove print y offset
     */
    public int mouseYToPrintRounded(double mouseY)
    {
        return this.printToGuiRounded(mouseY) - this.getPrint().posY;
    }
    
    /**
     * Get the node field dot posX
     */
    public int getDotPosX(NodeField field)
    {
        return field.node.posX + this.getUtil().getFieldOffX(field) + this.getUtil().getDotOffX(field);
    }
    
    /**
     * Get the node field dot posY
     */
    public int getDotPosY(NodeField field)
    {
        return field.node.posY + this.getUtil().getFieldOffY(field) + this.getUtil().getDotOffY(field);
    }
}

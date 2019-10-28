package de.cas_ual_ty.visibilis.print;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.cas_ual_ty.visibilis.datatype.DataTypeDynamic;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import de.cas_ual_ty.visibilis.node.NodeGroupsHelper;
import de.cas_ual_ty.visibilis.node.Output;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

public class GuiPrint extends GuiScreen
{
    // Empty connection grey tone
    public static float nodeFieldDef = 0.5F;
    
    public static int scrollSpeedInner = 2;
    
    public static int scrollSpeedNodeList = 24;
    
    public IPrintHelper helper;
    
    public RenderUtility.Rectangle inner;
    public RenderUtility.Rectangle nodeList;
    public RenderUtility.Rectangle nodeListSearch;
    
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
    
    protected GuiTextField fieldInput;
    
    protected int listOffset;
    protected GuiTextField listSearch;
    
    public GuiPrint(IPrintHelper helper)
    {
        this(helper, new RenderUtility());
    }
    
    public GuiPrint(IPrintHelper helper, RenderUtility util)
    {
        super();
        this.helper = helper;
        this.clicked = false;
        this.util = util;
        
        //Initialize text field and set it to be invisible. Here instead of in #initGui() as position and size are adjusted when activated
        this.fieldInput = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer, 0, 0, 0, 0);
        this.fieldInput.setVisible(false);
        
        this.helper.onGuiOpen(this);
        
        this.listOffset = 0;
    }
    
    @Override
    public void initGui()
    {
        this.sr = new ScaledResolution(this.mc);
        
        this.inner = RenderUtility.Rectangle.fromXYWH(0, 0, this.width - this.util.fieldWidth, this.height);
        this.nodeListSearch = RenderUtility.Rectangle.fromXYWH(this.sr.getScaledWidth() - this.util.fieldWidth, 0, this.util.fieldWidth, this.util.nodeHeight);
        this.nodeList = RenderUtility.Rectangle.fromXYWH(this.sr.getScaledWidth() - this.util.fieldWidth, this.nodeListSearch.h, this.util.fieldWidth, this.sr.getScaledHeight() - this.nodeListSearch.h);
        
        this.listSearch = new GuiTextField(1, Minecraft.getMinecraft().fontRenderer, this.nodeListSearch.x + 1, this.nodeListSearch.y + 1, this.nodeListSearch.w - 2, this.nodeListSearch.h - 2);
        this.listSearch.setVisible(true);
        this.listSearch.setEnabled(true);
        
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
        
        this.inner.render(.5f, 0, 0);
        this.nodeList.render(.5f, .5f, .5f);
        
        //Check for all hovering objects already, so it is done only once. Nothing is being rendered here
        this.updateHovering(mouseX, mouseY);
        
        //Draw node list
        this.drawNodeList(mouseX, mouseY, partialTicks);
        
        //Draw inner
        this.drawInner(mouseX, mouseY, partialTicks);
        
        // Draw buttons and labels
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        GlStateManager.enableLighting();
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        /*
         * Useful:
         * https://minecraft.gamepedia.com/Key_codes
         * http://legacy.lwjgl.org/javadoc/constant-values.html
         */
        
        if (keyCode == Keyboard.KEY_ESCAPE)
        {
            super.keyTyped(typedChar, keyCode);
            return;
        }
        
        if (this.fieldInput.getVisible())
        {
            if (keyCode == Keyboard.KEY_RETURN)
            {
                // Return clicked, unfocus and apply test
                this.setUnfocusTextField();
                return;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.fieldInput.textboxKeyTyped(typedChar, keyCode);
                return;
            }
        }
        else if (this.listSearch.isFocused())
        {
            if (keyCode == Keyboard.KEY_RETURN)
            {
                this.listSearch.setFocused(false);
                return;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.listSearch.textboxKeyTyped(typedChar, keyCode);
                return;
            }
        }
        
        //Moving and zooming:
        
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
        if (keyCode == Keyboard.KEY_PRIOR)
        {
            this.listOffset += GuiPrint.scrollSpeedNodeList;
        }
        if (keyCode == Keyboard.KEY_NEXT)
        {
            this.listOffset -= GuiPrint.scrollSpeedNodeList;
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
            this.listSearch.setFocused(false);
            
            // Get whatever the player just clicked on
            Object obj = this.getHovering(mouseX, mouseY);
            
            if (this.inner.isCoordInside(mouseX, mouseY))
            {
                //We are inside inner, so the clicked on obj is a node or node field
                
                // Nothing was clicked on/active before
                if (!this.clicked)
                {
                    // Inner: nothing on mouse (attached)
                    
                    // Nothing was clicked on before, but now a node has been clicked on
                    if (obj instanceof Node)
                    {
                        // Clicked on a node while nothing is attached to the mouse: Attach the node
                        this.clicked = true;
                        this.mouseClickedNode = (Node) obj;
                    }
                    // Nothing was clicked on before, but now a node field has been clicked on
                    else if (obj instanceof NodeField)
                    {
                        NodeField field = (NodeField) obj;
                        
                        // (Hover over method and read:)
                        if (this.getCanClickField(field))
                        {
                            // If it is an input an connected, cut the connections, then proceed with eg. showing enums
                            if (field.isInput() && field.hasConnections())
                            {
                                field.cutConnections();
                            }
                            
                            // Mark field as clicked on
                            this.clicked = true;
                            this.mouseClickedField = field;
                            
                            // Check if it is an input to see if there is direct access to the value
                            if (this.mouseClickedField.isInput())
                            {
                                Input input = (Input) this.mouseClickedField;
                                
                                // (hover over and read desc:)
                                this.tryFocusTextField(input);
                            }
                        }
                    }
                }
                else
                {
                    // Inner: something on mouse
                    
                    if (this.mouseClickedNode != null)
                    {
                        // Inner: node on mouse
                        
                        // Set new node position
                        this.mouseClickedNode.posX = this.mouseXToPrintRounded(mouseX);
                        this.mouseClickedNode.posY = this.mouseYToPrintRounded(mouseY);
                    }
                    else if (this.mouseClickedField != null)
                    {
                        // Inner: field on mouse
                        
                        if (this.mouseClickedField instanceof Input)
                        {
                            // Inner: input on mouse
                            
                            Input input = (Input) this.mouseClickedField;
                            
                            // Input can have an immediate value
                            if (input.hasDisplayValue())
                            {
                                if (input.dataType instanceof DataTypeDynamic)
                                {
                                    // Dynamic data type
                                    
                                    // Deselect text field and apply its value
                                    this.setUnfocusTextField(input);
                                }
                                else if (input.dataType instanceof DataTypeEnum)
                                {
                                    // Enum data type
                                    
                                    DataTypeEnum dt = (DataTypeEnum) input.dataType;
                                    
                                    int x, y, w, h;
                                    
                                    w = this.util.inputValueWidth;
                                    h = this.util.nodeHeight;
                                    
                                    x = input.node.posX - w;
                                    y = input.node.posY + this.util.getFieldOffY(input);
                                    
                                    // Convert GUI mouse coords to match print offset and zoom
                                    float mouseX1 = this.mouseXToPrint(mouseX);
                                    float mouseY1 = this.mouseYToPrint(mouseY);
                                    
                                    // Loop through all enums of the data type
                                    for (int i = 0; i < dt.getEnumSize(); ++i)
                                    {
                                        y -= i * h;
                                        
                                        // Check if mouse is on current enum index
                                        if (RenderUtility.isCoordInsideRect(mouseX1, mouseY1, x, y, w, h))
                                        {
                                            // Set the input value to clicked enum and break the loop as we are done
                                            input.setValue(dt.getEnum(i));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            // output on mouse
                            
                            if (obj instanceof NodeField)
                            {
                                // Inner: field & field interaction
                                
                                NodeField field = (NodeField) obj;
                                
                                if (this.mouseClickedField.isOutput() && field.isInput())
                                {
                                    NodeField.tryConnect((Output) this.mouseClickedField, (Input) field, true);
                                }
                            }
                        }
                    }
                    
                    // Reminder: we are in inner and something was clicked on before
                    // So now either nothing was clicked on which results in whatever was clicked on before to be unfocused...
                    // ... or something was clicked on which results in whatever was clicked on before to be unfocused, as well.
                    
                    // (So either way we deselect/unfocus whatever was clicked on before:)
                    
                    this.unfocusClicked();
                }
            }
            else if (this.nodeList.isCoordInside(mouseX, mouseY))
            {
                int y = this.listOffset;
                int w = this.util.nodeWidth;
                int h;
                
                for (Node node : this.getAvailableNodesList())
                {
                    h = this.util.getNodeTotalHeight(node);
                    
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, this.nodeList.x, this.nodeList.y + y / 2, w / 2, h / 2))
                    {
                        this.getPrint().addNode(node.setPosition(-this.getPrint().posX, -this.getPrint().posY));
                    }
                    
                    y += h + 2;
                }
            }
            else if (this.nodeListSearch.isCoordInside(mouseX, mouseY))
            {
                if (this.listSearch.mouseClicked(mouseX, mouseY, mouseButton))
                {
                    this.listSearch.setFocused(true);
                }
            }
            
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void drawNodeList(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.pushMatrix();
        RenderUtility.scissorStart(this.sr, this.nodeList.x, this.nodeList.y, this.nodeList.w, this.nodeList.h);
        GlStateManager.translate(this.nodeList.x, this.nodeList.y, 0); // Move everything in the print by the print's position
        RenderUtility.applyZoom(0.5F);
        
        int x = 0;
        int y = this.listOffset;
        int w = this.util.nodeWidth;
        int h;
        
        for (Node node : this.getAvailableNodesList())
        {
            this.util.drawNode(node, x, y);
            
            h = this.util.getNodeTotalHeight(node);
            
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, this.nodeList.x, this.nodeList.y + y / 2, w / 2, h / 2))
            {
                this.util.drawHoverRect(x, y, w, h);
            }
            
            y += h + 2;
        }
        
        y -= 2 + this.listOffset;
        
        int offset = 0;//this.util.nodeHeight;
        
        int topRect = this.nodeList.t + offset;
        int botRect = (this.nodeList.b - y) - offset; //TODO properly fit botRect. Right now you can scroll down until the bottom of the nodes list hits roughly the middle of the screen instead of the bottom - nodeHeight.
        
        // listOffset + y / 2 < b
        // <=>
        // listOffset < b - y / 2
        
        if (this.listOffset < botRect) //Bottom of list is below window
        {
            this.listOffset = botRect;
        }
        if (this.listOffset > topRect) //Top of list is above window
        {
            this.listOffset = topRect;
        }
        
        RenderUtility.scissorEnd();
        GlStateManager.popMatrix();
        
        this.listSearch.drawTextBox();
    }
    
    public void drawInner(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.pushMatrix();
        
        GlStateManager.pushMatrix();
        RenderUtility.scissorStart(this.sr, this.inner.x, this.inner.y, this.inner.w, this.inner.h);
        RenderUtility.applyZoom(this.getPrint().zoom); // Inside of the matrix since you would otherwise "touch" everything outside of the matrix
        GlStateManager.translate(this.getPrint().posX, this.getPrint().posY, 0); // Move everything in the print by the print's position
        
        this.drawPrint(this.getPrint());
        this.drawInnerInteractions(mouseX, mouseY, partialTicks);
        
        RenderUtility.scissorEnd();
        GlStateManager.popMatrix();
        
        if (this.mouseHoveringNode != null)
        {
            this.util.drawNodeHoveringText(this, this.mouseHoveringNode, mouseX, mouseY);
        }
        
        if (this.mouseHoveringField != null)
        {
            this.util.drawNodeFieldHoveringText(this, this.mouseHoveringField, mouseX, mouseY);
        }
        
        GlStateManager.popMatrix();
    }
    
    public ArrayList<Node> getAvailableNodesList()
    {
        return this.cutNodeListToSearch(this.helper.getAvailableNodes(this));
    }
    
    public ArrayList<Node> cutNodeListToSearch(ArrayList<Node> list)
    {
        String text = this.listSearch.getText().trim();
        
        if (!StringUtils.isNullOrEmpty(text))
        {
            ArrayList<Node> list2 = (ArrayList<Node>) list.clone();
            
            for (Node node : list2)
            {
                if (!NodeGroupsHelper.INSTANCE.isTextMatchingNode(node, text))
                {
                    list.remove(node);
                }
            }
        }
        
        return list;
    }
    
    /**
     * Update the node or node field hovering over
     */
    public void updateHovering(int mouseX, int mouseY)
    {
        Object obj = this.getHovering(mouseX, mouseY);
        
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
    
    /**
     * Returns the object the mouse is hovering over, can be a node or a node field
     */
    public Object getHovering(int mouseX, int mouseY)
    {
        if (this.inner.isCoordInside(mouseX, mouseY))
        {
            // Inner
            return this.getHoveringInner(mouseX, mouseY);
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
    protected Object getHoveringInner(int mouseX0, int mouseY0)
    {
        float mouseX = this.printToGui(mouseX0);
        float mouseY = this.printToGui(mouseY0);
        
        Node node;
        float x, y, w, h; // Rect of node
        float h2; // Header height
        
        // Loop from back to front, as those are on top
        for (int i = this.getPrint().nodes.size() - 1; i >= 0; --i)
        {
            node = this.getPrint().nodes.get(i);
            
            // Entire node position and size, zoom and shift accounted for
            x = this.getNodePosX2(node);
            y = this.getNodePosY2(node);
            w = this.util.nodeWidth;
            h = this.util.getNodeTotalHeight(node);
            h2 = this.util.nodeHeight;
            
            // Check if the mouse is on top of the entire node
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h2))
                {
                    // Inside header -> return node itself
                    return node;
                }
                else
                {
                    // Not inside header -> node fields
                    
                    int j;
                    
                    if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, this.util.fieldWidth, h))
                    {
                        // Left side -> inputs
                        
                        for (j = 1; j <= node.getInputAmt(); ++j)
                        {
                            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y + this.util.nodeHeight * j, w, h2))
                            {
                                // inside this node field -> return it
                                return node.getInput(j - 1);
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
                                return node.getOutput(j - 1);
                            }
                        }
                    }
                }
                
                // No node field found. Now check if it is inside the rect excluding values
                // Which basically means that it is next to one of the fields, depending on if there are more outputs or inputs
                
                /*
                if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
                {
                    // return node; // Only return node when hovering above header
                }
                */
            }
        }
        
        return null;
    }
    
    /**
     * Set everything to default again. Unclick anything that was clicked on previously.
     */
    public void unfocusClicked()
    {
        this.clicked = false;
        this.mouseClickedNode = null;
        this.mouseClickedField = null;
    }
    
    /**
     * Safe to use. Check if the input is allowed to hold an immediate value and if it is a dynamic data type.
     * If yes set focus on the text field and adjust it to this input.
     * @see #setFocusTextField(Input)
     */
    public void tryFocusTextField(Input input)
    {
        // Check if this input is allowed to hold an immediate value
        if (input.hasDisplayValue())
        {
            // Check if it is a dynamic data type to activate and adjust the text field
            // (enum data type needs no further adjusting, it is already being rendered and handled)
            if (input.dataType instanceof DataTypeDynamic)
            {
                this.setFocusTextField(input);
            }
        }
    }
    
    /**
     * Set focus on the text field. Data type of the input <b>must</b> be dynamic so not a safe method.
     * Use {@link #tryFocusTextField(Input)} instead for safety.
     */
    public void setFocusTextField(Input input)
    {
        DataTypeDynamic dt = (DataTypeDynamic) input.dataType;
        
        // Make it visible
        this.fieldInput.setVisible(true);
        
        // Adjust position to node field accordingly
        this.fieldInput.x = input.node.posX + this.util.getFieldOffX(input);
        this.fieldInput.y = input.node.posY + this.util.getFieldOffY(input);
        
        // Adjust size to node field accordingly
        this.fieldInput.width = this.util.fieldWidth;
        this.fieldInput.height = this.util.nodeHeight;
        
        // Set the text to the current immediate value the input is holding
        this.fieldInput.setText(dt.valueToString(input.getSetValue()));
        
        // Set it to be focused, otherwise it does not accept input
        this.fieldInput.setFocused(true);
        
        // Set the string validator for immediate input feedback
        this.fieldInput.setValidator(dt.getValidator());
    }
    
    /**
     * {@link #setUnfocusTextField(Input)} but without params. Uses {@link #mouseClickedField} and casts it to call said method.
     * Then calls {@link #unfocusClicked()} as this method is intended to be used outside the input hover/clicking methods.
     */
    public void setUnfocusTextField()
    {
        Input input = (Input) this.mouseClickedField;
        this.setUnfocusTextField(input);
        this.unfocusClicked();
    }
    
    /**
     * Unfocus the text field that was focused on and try to apply the written value. Data type of the input <b>must</b> be dynamic so it is unsafe.
     * @param input
     * @see #setUnfocusTextField()
     */
    // I made this to avoid unnecessary casting at all times
    public void setUnfocusTextField(Input input)
    {
        DataTypeDynamic dt = (DataTypeDynamic) input.dataType;
        
        // Can the string in the text box be applied to the input? Is it viable?
        if (dt.canParseString(this.fieldInput.getText()))
        {
            // Parse string and apply the parsed value to the input.
            input.setValue(dt.stringToValue(this.fieldInput.getText()));
        }
        
        this.fieldInput.setVisible(false);
    }
    
    /**
     * Update line width according to current print zoom.
     */
    public void updateLineWidth()
    {
        this.util.nodeFieldConnectionsWidth = (this.util.nodeFieldDotSize / 2) * this.getPrint().zoom * this.sr.getScaleFactor();
    }
    
    /**
     * Essentially, this is to draw every interaction last (last = on top).
     * Including but not excluded to: hovering, connections, enum/dyn data types
     */
    public void drawInnerInteractions(int mouseX, int mouseY, float partialTicks)
    {
        // Something was already clicked on before / attached to mouse / focused
        if (this.clicked)
        {
            if (this.mouseClickedNode != null)
            {
                // node on mouse
                
                // Outline clicked on node
                this.drawOutlineRect(this.mouseXToPrintRounded(mouseX), this.mouseYToPrintRounded(mouseY), this.util.nodeWidth, this.util.getNodeTotalHeight(this.mouseClickedNode));
            }
            if (this.mouseClickedField != null)
            {
                // field on mouse
                
                if (this.mouseClickedField.isOutput())
                {
                    // output on mouse
                    
                    // Draw connection line: Dot to Mouse
                    
                    // Where to draw the 1st dot at
                    int dotX = this.getDotPosX(this.mouseClickedField);
                    int dotY = this.getDotPosY(this.mouseClickedField);
                    
                    if (this.mouseHoveringField != null && NodeField.canConnect(this.mouseClickedField, this.mouseHoveringField))
                    {
                        this.drawHoverRect(this.mouseHoveringField.node.posX + (this.mouseHoveringField.isInput() ? 0 : this.util.nodeWidth / 2), this.mouseHoveringField.node.posY + this.util.nodeHeight * (this.mouseHoveringField.id + 1), this.util.nodeWidth / 2, this.util.nodeHeight);
                    }
                    
                    // Node field was clicked on -> Render line from Dot to Mouse
                    RenderUtility.drawGradientLine(dotX + this.util.nodeFieldDotSize / 2, dotY + this.util.nodeFieldDotSize / 2, this.mouseXToPrintRounded(mouseX), this.mouseYToPrintRounded(mouseY), this.util.getLineWidth(this.mouseClickedField.dataType), this.mouseClickedField.dataType.getColor()[0], this.mouseClickedField.dataType.getColor()[1], this.mouseClickedField.dataType.getColor()[2], this.util.nodeFieldConnectionsAlpha, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldDef, GuiPrint.nodeFieldDef, this.util.nodeFieldConnectionsAlpha);
                }
                else
                {
                    //input on mouse
                    
                    Input input = (Input) this.mouseClickedField;
                    
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
                            // Draw the enum options
                            
                            DataTypeEnum dt = (DataTypeEnum) this.mouseClickedField.dataType;
                            
                            int x, y, w, h;
                            
                            w = this.util.inputValueWidth;
                            h = this.util.nodeHeight;
                            
                            // "- w" because we want to draw these options to the left of the input.
                            x = this.mouseClickedField.node.posX - w;
                            y = this.mouseClickedField.node.posY + this.util.getFieldOffY(this.mouseClickedField);
                            
                            String s;
                            
                            // Loop through enums of the data type
                            for (int i = 0; i < dt.getEnumSize(); ++i)
                            {
                                // Get the string representation of the enum
                                s = dt.valueToString(dt.getEnum(i));
                                
                                y -= i * h;
                                
                                // Draw the rect with the enum as text
                                this.util.drawRectWithText(x, y, w, h, dt.getColor(), s, dt.getTextColor());
                                
                                // If mouse is hovering over said rect, whiten it
                                if (RenderUtility.isCoordInsideRect(this.mouseXToPrint(mouseX), this.mouseYToPrint(mouseY), x, y, w, h))
                                {
                                    this.util.drawHoverRect(x, y, w, h);
                                }
                            }
                        }
                    }
                }
            }
        }
        /*
         * else
         * {
         * // Nothing has been clicked on, render normal hovering rects
         * 
         * // Moved to #drawNode
         * }
         */
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
        if (!this.clicked)
        {
            // nothing on mouse
            
            if (node == this.mouseHoveringNode)
            {
                // node below mouse
                
                // Node hover rect
                this.util.drawNodeHover(node, x, y);
            }
            if (this.mouseHoveringField != null && node == this.mouseHoveringField.node)
            {
                // field below mouse
                
                if (this.getCanClickField(this.mouseHoveringField))
                {
                    // Node field hover rect
                    this.util.drawNodeFieldHover(this.mouseHoveringField, x, y);
                }
            }
        }
    }
    
    /**
     * Check if clicks on node fields are currently legal, depending on if something was clicked on before, the kind of  (dyn/enum) data type and if it is an input or output.
     * Used for hover checks and mouse click checks.
     */
    public boolean getCanClickField(NodeField field)
    {
        return !this.clicked
                        ?
                        //Nothing is attached on mouse or previously clicked on
                        //Return true if it is an output or a dynamic data type or an enum data type OR it is connected
                        (field.isOutput() || (field.dataType instanceof DataTypeDynamic) || (field.dataType instanceof DataTypeEnum)) || field.hasConnections()
                        :
                        //Something has been clicked on before
                        //Return true if the clicked on field is an output and the currently attached field can be connected to it.
                        (this.mouseClickedField instanceof Output && NodeField.canConnect(this.mouseClickedField, field));
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
     * Print posX + Node posX
     */
    public int getNodePosX2(Node n)
    {
        return this.getPrint().posX + n.posX;
    }
    
    /**
     * Print posY + Node posY
     */
    public int getNodePosY2(Node n)
    {
        return this.getPrint().posY + n.posY;
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

package de.cas_ual_ty.visibilis.print.ui;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeGroupsHelper;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VRenderUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;

public class PrintRenderer
{
    public final Screen gui;
    public final FontRenderer fontRenderer;
    
    /** The height of the header or an output/input NOT THE ENTIRE NODE HEIGHT AS THEY HAVE DIFFERENT SIZES */
    public int nodeHeight;
    
    /** The entire node width */
    public int nodeWidth;
    
    /** Half node width */
    public int fieldWidth;
    
    /** The dot x/y size of node fields */
    public int nodeFieldDotSize;
    
    /** Transparency of connection lines */
    public float nodeFieldConnectionsAlpha;
    
    /** Line width for connection lines */
    public float nodeFieldConnectionsWidth;
    
    /** The margin of the colored rectangles to the outside ({@link #nodeHeight}) text gets rendered inside of */
    public int nodeRectMargin;
    
    /** The margin of the text to the colored rectangles */
    public int nodeTextMargin;
    
    /** Color of the box when hovering over an object */
    public float[] hoverColor;
    
    /** Color of the box drawn over an object that is selected */
    public float[] selectColor;
    
    /** Transparency of *insert color above* box when hovering over an object */
    public float hoverAlpha;
    
    /** Moves the dot further to the edge by this ammount */
    public int dotOffset;
    
    /** Background color of nodes */
    public float[] nodeBackground;
    
    /** Width of the box in front of inputs with fixed values */
    public int inputValueWidth;
    
    /** Color of a dot if it is unconnected but also has a fixed value */
    public float[] unneededDot;
    
    /** Color of the background rect of all actions on a node */
    public float[] actionColor;
    
    /** Color of the text on top of the background rect of all actions on a node */
    public float[] actionColorText;
    
    public float[] printBackround;
    
    /** node, output, input, print, as translated string */
    public String tNode, tOut, tIn, tPrint, tRecalc;
    
    public PrintRenderer(Screen gui)
    {
        this.gui = gui;
        this.fontRenderer = Minecraft.getInstance().fontRenderer;
        
        this.tNode = "visibilis.node";
        this.tOut = "visibilis.output";
        this.tIn = "visibilis.input";
        this.tPrint = "visibilis.print";
        this.tRecalc = "visibilis.recalc";
        
        this.setVarsAndColors();
    }
    
    public void setVarsAndColors()
    {
        this.nodeHeight = 12;
        this.nodeFieldDotSize = 4;
        this.nodeFieldConnectionsAlpha = 0.5F;
        this.nodeRectMargin = 1;
        this.hoverColor = new float[] { 1F, 1F, 1F };
        this.selectColor = new float[] { 0F, 0F, 0F };
        this.hoverAlpha = 0.5F;
        this.nodeBackground = new float[] { 0.125F, 0.125F, 0.125F };
        this.dotOffset = 0;
        this.unneededDot = new float[] { 0.5F, 0.5F, 0.5F };
        this.actionColor = new float[] { 0.25F, 0.25F, 0.25F };
        this.actionColorText = new float[] { 1F, 1F, 1F };
        this.printBackround = new float[] { 0.625F, 0.625F, 0.625F };
        
        this.genVars();
    }
    
    public void genVars()
    {
        this.nodeWidth = this.nodeHeight * 10;
        this.fieldWidth = this.nodeWidth / 2;
        this.nodeFieldConnectionsWidth = this.nodeFieldDotSize * 2;
        this.nodeTextMargin = (this.nodeHeight - (this.fontRenderer.FONT_HEIGHT)) / 2 + this.fontRenderer.FONT_HEIGHT % 2;
        this.inputValueWidth = this.fieldWidth - this.nodeHeight;
    }
    
    /**
     * Draw a print and all its nodes and connections at their coordinates.
     */
    public void drawPrint(Print print)
    {
        for(Node node : print.getNodes())
        {
            this.drawNode(node, node.getPosX() + print.getPosX(), node.getPosY() + print.getPosY());
        }
        
        for(Node node : print.getNodes())
        {
            this.drawNodeConnections(node, node.getPosX() + print.getPosX(), node.getPosY() + print.getPosY());
        }
    }
    
    /**
     * Draw a node at the given coordinates (not the node's coordinates!) and all its node fields. Total width is {@link #nodeWidth}, total height is {@link #getNodeTotalHeight(Node)}.
     * 
     * @param background
     *            The background color
     * @param rect
     *            The color of the rectangle the text is in
     * @param text
     *            The text color
     */
    public void drawNode(Node node, int x, int y)
    {
        // --- Start drawing node itself ---
        
        if(node.isDynamic())
        {
            this.drawDynamicNodeBackground(node, x, y);
        }
        
        // Background
        this.drawNodeBackground(node, x, y);
        
        // #SelfExplainingCodeIsAMeme
        this.drawNodeHeader(node, x, y);
        
        // --- Done drawing node, now drawing fields (inputs and outputs) ---
        
        // Draw inputs, i + 1 to draw below header
        for(NodeField<?> field : node.getInputsIterable())
        {
            this.drawNodeField(field, x, y + this.getFieldOffY(field));
        }
        
        // Draw outputs, i + 1 to draw below header
        for(NodeField<?> field : node.getOutputsIterable())
        {
            this.drawNodeField(field, x + this.fieldWidth, y + this.getFieldOffY(field)); // Outputs are on the right, so add half width of the node
        }
        
        // --- End drawing fields ---
    }
    
    public void drawDynamicNodeBackground(Node node, int x, int y)
    {
        VRenderUtility.drawRect(x, y, this.nodeWidth, this.getNodeTotalHeight(node), -1, VRenderUtility.mixColors(VDataTypes.EXEC.getColor(), VUtility.COLOR_DEFAULT_WHITE));
    }
    
    /**
     * Draw an appropriately sized background (rectangle) for a node and all its node fields at the given coordinates (not the node's coordinates!). Total width is {@link #nodeWidth}, total height is {@link #getNodeTotalHeight(Node)}.
     */
    public void drawNodeBackground(Node node, int x, int y)
    {
        // Draw entire node background
        VRenderUtility.drawRect(x, y, this.nodeWidth, this.getNodeTotalHeight(node), this.nodeBackground);
    }
    
    /**
     * Draw a node header (colored rectangle and name) of a node at the given coordinates (not the node's coordinates!). Total width is {@link #nodeWidth}, total height is {@link #nodeHeight}.
     */
    public void drawNodeHeader(Node node, int x, int y)
    {
        // Draw the inner colored rectangle
        // Draw the name
        String name = node.getName();
        name = this.fontRenderer.trimStringToWidth(name, this.nodeWidth - 2 * this.nodeTextMargin); // Trim the name in case it is too big
        VRenderUtility.drawRectWithText(this.fontRenderer, x, y, this.nodeWidth, this.nodeHeight, this.nodeRectMargin, node.getColor(), this.nodeTextMargin, name, node.getTextColor());
    }
    
    /**
     * Draw a node field (colored dot, rectangle and name) at the given coordinates (not the node's coordinates!). Total width is {@link #nodeWidth}, total height is {@link #nodeHeight}. Dot width and height are {@link #nodeFieldDotSize}.
     */
    public void drawNodeField(NodeField<?> field, int x, int y)
    {
        // Where to draw the dot
        int dotX = this.getDotOffX(field) + x;
        int dotY = this.getDotOffY(field) + y;
        
        // Where to draw the name
        int nameX = x;
        int nameY = y;
        
        // width - dot - border
        // the dot is in the middle of a quad of size height x height, at the left/right of the field
        int nameW = this.fieldWidth - this.nodeHeight;
        
        if(field.isInput())
        {
            // Input, so draw the dot on the left, the name on the right
            nameX += this.nodeHeight;
        }
        
        // Draw dot on top
        this.drawNodeFieldDot(field, dotX, dotY);
        
        // Draw name
        // Draw inner colored rectangle
        String name = field.getNameTranslated();
        name = this.fontRenderer.trimStringToWidth(name, nameW - 2 * this.nodeTextMargin); // Trim the name in case it is too big
        VRenderUtility.drawRectWithText(this.fontRenderer, nameX, nameY, nameW, this.nodeHeight, this.nodeRectMargin, field.getDataType().getColor(), this.nodeTextMargin, name, field.getDataType().getTextColor());
    }
    
    /**
     * Draw a node field dot (colored dot) at the given coordinates (not the node's coordinates!). Dot width and height are {@link #nodeFieldDotSize}.
     */
    public void drawNodeFieldDot(NodeField<?> field, int x, int y)
    {
        VRenderUtility.drawRect(x, y, this.nodeFieldDotSize, this.nodeFieldDotSize, field.getDataType().getColor());
    }
    
    /**
     * Draw a node's connections (with gradient according to their data types) starting at the given coordinates (not the node's coordinates!) with dot offset being applied onto them. Alpha is {@link #nodeFieldConnectionsAlpha}.
     */
    public void drawNodeConnections(Node node, int x, int y)
    {
        // Draw output -> input connections, not the other way around, so its not done twice
        for(NodeField<?> field : node.getOutputsIterable())
        {
            this.drawNodeFieldConnections(field, x + this.fieldWidth, y + this.getFieldOffY(field));
        }
    }
    
    /**
     * Draw a node field's connections (with gradient according to their data types) starting at the given coordinates (not the node's coordinates!) with dot offset being applied onto them. Alpha is {@link #nodeFieldConnectionsAlpha}.
     */
    public void drawNodeFieldConnections(NodeField<?> field, int x, int y)
    {
        // Retrieve all connections
        ArrayList<NodeField<?>> connections = field.getConnectionsList();
        
        // Loop through "destinations"
        for(NodeField<?> dest : connections)
        {
            this.drawConnection(field, dest, x, y);
        }
    }
    
    public <A> void drawInputEnums(NodeField<A> field, int x, int y)
    {
        // Draw the enum options
        
        DataTypeEnum<A> dt = (DataTypeEnum<A>)field.getDataType();
        
        int w, h;
        
        w = this.inputValueWidth;
        h = this.nodeHeight;
        
        String s;
        
        // Loop through enums of the data type
        for(int i = 0; i < dt.getEnumSize(); ++i)
        {
            // Get the string representation of the enum
            s = dt.valueToString(dt.getEnum(i));
            
            y -= i * h;
            
            // Draw the rect with the enum as text
            this.drawRectWithText(x, y, w, h, dt.getColor(), s, dt.getTextColor());
        }
    }
    
    /**
     * Draw a node field's single connection (with gradient according to their data types) starting at the given coordinates (not the node's coordinates!) with dot offset being applied onto them. Alpha is {@link #nodeFieldConnectionsAlpha}.
     */
    public <A, B> void drawConnection(NodeField<A> field, NodeField<B> dest, int x, int y)
    {
        // The middle of the dot
        int dotX = this.getDotOffX(field) + x + this.nodeFieldDotSize / 2;
        int dotY = this.getDotOffY(field) + y + this.nodeFieldDotSize / 2;
        
        // Prepare variables so we dont create new ones in each loop
        int offX, offY;
        
        int x1, y1, x2, y2;
        
        DataType<A> type1 = field.getDataType();
        DataType<B> type2 = dest.getDataType();
        
        // We are already at the dot position of the 1st field, so we can just take the
        // difference of the nodes themselves to get the 2nd dot's position
        offX = dest.getNode().getPosX() - field.getNode().getPosX();
        offY = dest.getNode().getPosY() - field.getNode().getPosY();
        
        // Our position is an output dot, so we need to shift to an input dot
        offX -= this.nodeWidth - this.nodeHeight;
        
        // Adjust Y, eg. 1st field might be the 2nd in order, the 2nd field might be the
        // 4th in order
        offY += (dest.getId() - field.getId()) * this.nodeHeight;
        
        // + half size so it starts in the middle
        x1 = dotX;
        y1 = dotY;
        x2 = x1 + offX;
        y2 = y1 + offY;
        
        // Now draw the line, half transparent
        VRenderUtility.drawGradientLine(x1, y1, x2, y2, this.getLineWidth(type1), this.nodeFieldConnectionsAlpha, type1.getColor(), type2.getColor());
    }
    
    /**
     * Draw a rectangle with the color {@link #hoverColor} and alpha {@link #hoverAlpha} over the node
     */
    public void drawNodeHover(Node node, int x, int y)
    {
        this.drawHoverRect(x, y, this.nodeWidth, this.getNodeTotalHeight(node));
    }
    
    /**
     * Draw a rectangle with the color {@link #selectColor} and alpha {@link #hoverAlpha} over the node
     */
    public void drawNodeSelect(Node node, int x, int y)
    {
        this.drawSelectRect(x, y, this.nodeWidth, this.getNodeTotalHeight(node));
    }
    
    /**
     * Draw a rectangle with the color {@link #hoverColor} and alpha {@link #hoverAlpha} over the node field
     */
    public void drawNodeFieldHover(NodeField<?> field, int x, int y)
    {
        this.drawHoverRect(x + this.getFieldOffX(field), y + this.getFieldOffY(field), this.fieldWidth, this.nodeHeight);
    }
    
    public void drawExpansionHover(Node node, int x, int y)
    {
        this.drawHoverRect(x, y + (VRenderUtility.getVerticalAmt(node)) * this.nodeHeight, this.fieldWidth, this.nodeHeight);
    }
    
    public void drawShrinkHover(Node node, int x, int y)
    {
        this.drawHoverRect(x + this.fieldWidth, y + (VRenderUtility.getVerticalAmt(node)) * this.nodeHeight, this.fieldWidth, this.nodeHeight);
    }
    
    /**
     * Draw the white border over an object.
     */
    public void drawOutlineRect(int x, int y, int w, int h)
    {
        VRenderUtility.drawRect(x, y, w, 2, 1F, 1F, 1F, this.hoverAlpha); // TOP, x -> x + w
        VRenderUtility.drawRect(x, y + h - 2, w, 2, 1F, 1F, 1F, this.hoverAlpha);// BOT, x -> x + w
        VRenderUtility.drawRect(x, y + 2, 2, h - 4, 1F, 1F, 1F, this.hoverAlpha);// LEFT, y + 2 -> y + h - 2
        VRenderUtility.drawRect(x + w - 2, y + 2, 2, h - 4, 1F, 1F, 1F, this.hoverAlpha);// RIGHT, y + 2 -> y + h - 2
    }
    
    /**
     * Draw a rectangle with the color {@link #hoverColor} and alpha {@link #hoverAlpha}
     */
    public void drawHoverRect(int x, int y, int w, int h)
    {
        VRenderUtility.drawRect(x, y, w, h, this.hoverColor, this.hoverAlpha);
    }
    
    /**
     * Draw a rectangle with the color {@link #selectColor} and alpha {@link #hoverAlpha}
     */
    public void drawSelectRect(int x, int y, int w, int h)
    {
        VRenderUtility.drawRect(x, y, w, h, this.selectColor, this.hoverAlpha);
    }
    
    public void drawNodeInputValues(Node node, int x, int y)
    {
        for(Input<?> in : node.getInputsIterable())
        {
            if(in.hasDisplayValue())
            {
                this.drawInputValue(in, x, y + this.getFieldOffY(in), true);
            }
        }
    }
    
    public <A> void drawInputValue(Input<A> input, int x, int y, boolean overrideDot)
    {
        int width = this.inputValueWidth;
        
        if(overrideDot)
        {
            width = this.fieldWidth;
        }
        else
        {
            x += this.fieldWidth - width;
        }
        
        this.drawRectWithText(x, y, width, this.nodeHeight, input.getDataType().getColor(), TextFormatting.UNDERLINE + input.getDataType().valueToString(input.getSetValue()), input.getDataType().getTextColor());
    }
    
    /**
     * BOLD + Node-Name
     * Node-Tags
     * 
     * Node-Desc
     * 
     * GOLD Input1-Name
     * GOLD Input2-Name
     * ...
     * 
     * DARK_RED Output1-Name
     * DARK_RED Output2-Name
     * ...
     */
    public void drawNodeHoveringText(Node node, int x, int y)
    {
        ArrayList<String> lines = new ArrayList<>();
        
        lines.add(TextFormatting.BOLD.toString() + node.getName() + TextFormatting.RESET + " - " + I18n.format(this.tNode));
        
        int i;
        
        ArrayList<String> tagsList = NodeGroupsHelper.INSTANCE.getTagsForNode(node);
        
        if(tagsList.size() > 0)
        {
            String tags = "";
            
            for(i = 0; i < tagsList.size(); ++i)
            {
                tags += tagsList.get(i);
                
                if(i < tagsList.size() - 1)
                {
                    tags += ", ";
                }
            }
            
            lines.add(tags);
        }
        
        String desc = node.getDesc();
        
        if(!StringUtils.isNullOrEmpty(desc) && !desc.equals(node.getUnlocalizedDesc()))
        {
            lines.add("");
            lines.add(desc);
        }
        
        NodeField<?> f;
        
        if(node.getInputAmt() > 0)
        {
            lines.add("");
            
            for(i = 0; i < node.getInputAmt(); ++i)
            {
                f = node.getInput(i);
                lines.add(TextFormatting.GOLD.toString() + f.getNameTranslated() + TextFormatting.RESET + " - " + I18n.format(this.tIn));
            }
        }
        
        if(node.getOutputAmt() > 0)
        {
            lines.add("");
            
            for(i = 0; i < node.getOutputAmt(); ++i)
            {
                f = node.getOutput(i);
                lines.add(TextFormatting.DARK_RED.toString() + f.getNameTranslated() + TextFormatting.RESET + " - " + I18n.format(this.tOut));
            }
        }
        
        this.gui.renderTooltip(lines, x, y);
    }
    
    public void drawNodeFieldHoveringText(NodeField<?> field, int x, int y)
    {
        ArrayList<String> lines = new ArrayList<>();
        
        lines.add(TextFormatting.BOLD.toString() + (field.isOutput() ? TextFormatting.DARK_RED.toString() : TextFormatting.GOLD.toString()) + field.getNameTranslated() + TextFormatting.RESET + " - " + (field.isOutput() ? I18n.format(this.tOut) : I18n.format(this.tIn)));
        
        String desc;
        
        if(field.doesForceDynamic())
        {
            desc = I18n.format(this.tRecalc);
            lines.add("");
            lines.add(desc);
        }
        
        desc = field.getDesc();
        
        if(!StringUtils.isNullOrEmpty(desc) && !desc.equals(field.getUnlocalizedDesc()))
        {
            lines.add("");
            lines.add(desc);
        }
        
        this.gui.renderTooltip(lines, x, y);
    }
    
    public void drawRectWithText(int x, int y, int w, int h, float[] colorRect, String text, float[] colorText)
    {
        this.drawRectWithText(x, y, w, h, this.nodeRectMargin, colorRect, this.nodeTextMargin, text, colorText);
    }
    
    public void drawRectWithText(int x, int y, int w, int h, int marginRect, float[] colorRect, int marginText, String text, float[] colorText)
    {
        VRenderUtility.drawRectWithText(this.fontRenderer, x, y, w, h, this.nodeBackground, marginRect, colorRect, marginText, text, colorText);
    }
    
    /**
     * Get the x offset of the node field to the node, depends on {@link NodeField#isOutput()}.
     */
    public int getFieldOffX(NodeField<?> field)
    {
        return field.isOutput() ? this.fieldWidth : 0;
    }
    
    /**
     * Get the y offset of the node field to the node (header accounted for), depends on {@link NodeField#id}.
     */
    public int getFieldOffY(NodeField<?> field)
    {
        return this.nodeHeight * (field.getId() + 1);
    }
    
    /**
     * Get the x/y offset of the node field's dot to the {@link #nodeHeight}^2 quad (the margin) at the edge of the node field.
     * 
     * @see PrintRenderer#getFieldOffX(NodeField)
     */
    public int getDotOffToHeight(NodeField<?> field)
    {
        return (this.nodeHeight - this.nodeFieldDotSize) / 2;
    }
    
    /**
     * Get the x offset of the node field's dot to the node field.
     */
    public int getDotOffX(NodeField<?> field)
    {
        return (field.isOutput() ? (this.fieldWidth + this.dotOffset - (this.getDotOffToHeight(field) + this.nodeFieldDotSize)) : (this.getDotOffToHeight(field)) - this.dotOffset);
    }
    
    /**
     * Get the y offset of the node field's dot to the node field.
     */
    public int getDotOffY(NodeField<?> field)
    {
        return this.getDotOffToHeight(field);
    }
    
    /**
     * {@link #nodeHeight} * {@link VRenderUtility#getVerticalAmt(Node)}
     */
    public int getNodeTotalHeight(Node node)
    {
        return this.nodeHeight * VRenderUtility.getVerticalAmt(node);
    }
    
    /**
     * Get the line width for the data type. Doubles if "exec" type
     */
    public float getLineWidth(DataType<?> type)
    {
        return (type == VDataTypes.EXEC ? 2 : 1) * this.nodeFieldConnectionsWidth;
    }
    
    /**
     * Update line width according to current print zoom.
     */
    public void updateLineWidth(Print print)
    {
        this.nodeFieldConnectionsWidth = (float)((this.nodeFieldDotSize / 2) * print.getZoom() * Minecraft.getInstance().getMainWindow().getGuiScaleFactor());
    }
}

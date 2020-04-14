package de.cas_ual_ty.visibilis.print.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeGroupsHelper;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;

public class RenderUtility
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
    
    /** node, output, input, print, as translated string */
    public String tNode, tOut, tIn, tPrint, tExpand, tShrink, tRecalc;
    
    public RenderUtility(Screen gui)
    {
        this.gui = gui;
        this.fontRenderer = Minecraft.getInstance().fontRenderer;
        
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
        
        this.tNode = I18n.format("visibilis.node");
        this.tOut = I18n.format("visibilis.output");
        this.tIn = I18n.format("visibilis.input");
        this.tPrint = I18n.format("visibilis.print");
        this.tExpand = I18n.format("visibilis.action_expand");
        this.tShrink = I18n.format("visibilis.action_shrink");
        this.tRecalc = I18n.format("visibilis.recalc");
        
        this.genVars();
    }
    
    public RenderUtility genVars()
    {
        this.nodeWidth = this.nodeHeight * 10;
        this.fieldWidth = this.nodeWidth / 2;
        this.nodeFieldConnectionsWidth = this.nodeFieldDotSize * 2;
        this.nodeTextMargin = (this.nodeHeight - (this.fontRenderer.FONT_HEIGHT)) / 2 + this.fontRenderer.FONT_HEIGHT % 2;
        this.inputValueWidth = this.fieldWidth - this.nodeHeight;
        
        return this;
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
        
        // Background
        this.drawNodeBackground(node, x, y);
        
        // #SelfExplainingCodeIsAMeme
        this.drawNodeHeader(node, x, y);
        
        // --- Done drawing node, now drawing fields (inputs and outputs) ---
        
        int i;
        NodeField<?> field;
        
        // Draw inputs, i + 1 to draw below header
        for(i = 0; i < node.getInputAmt(); ++i)
        {
            field = node.getInput(i);
            this.drawNodeField(field, x, y + this.getFieldOffY(field));
        }
        
        // Draw outputs, i + 1 to draw below header
        for(i = 0; i < node.getOutputAmt(); ++i)
        {
            field = node.getOutput(i);
            this.drawNodeField(field, x + this.fieldWidth, y + this.getFieldOffY(field)); // Outputs are on the right, so add half width of the node
        }
        
        // --- End drawing fields ---
    }
    
    /**
     * Draw an appropriately sized background (rectangle) for a node and all its node fields at the given coordinates (not the node's coordinates!). Total width is {@link #nodeWidth}, total height is {@link #getNodeTotalHeight(Node)}.
     */
    public void drawNodeBackground(Node node, int x, int y)
    {
        // Draw entire node background
        RenderUtility.drawRect(x, y, this.nodeWidth, this.getNodeTotalHeight(node), this.nodeBackground[0], this.nodeBackground[1], this.nodeBackground[2]);
    }
    
    /**
     * Draw a node header (colored rectangle and name) of a node at the given coordinates (not the node's coordinates!). Total width is {@link #nodeWidth}, total height is {@link #nodeHeight}.
     */
    public void drawNodeHeader(Node node, int x, int y)
    {
        // Draw the inner colored rectangle
        RenderUtility.drawRect(x + this.nodeRectMargin, y + this.nodeRectMargin, this.nodeWidth - 2 * this.nodeRectMargin, this.nodeHeight - 2 * this.nodeRectMargin, node.getColor()[0], node.getColor()[1], node.getColor()[2]);
        
        // Draw the name
        String name = node.getName();
        name = this.fontRenderer.trimStringToWidth(name, this.nodeWidth - 2 * this.nodeTextMargin); // Trim the name in case it is too big
        this.fontRenderer.drawString(name, x + this.nodeTextMargin, y + this.nodeTextMargin, RenderUtility.colorToInt(node.getTextColor())); // Draw the trimmed name, maybe add shadow?
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
        
        // Draw inner colored rectangle
        RenderUtility.drawRect(nameX + this.nodeRectMargin, nameY + this.nodeRectMargin, nameW - 2 * this.nodeRectMargin, this.nodeHeight - 2 * this.nodeRectMargin, field.getDataType().getColor()[0], field.getDataType().getColor()[1], field.getDataType().getColor()[2]);
        
        // Draw dot on top
        this.drawNodeFieldDot(field, dotX, dotY);
        
        // Draw name
        String name = field.getNameTranslated();
        name = this.fontRenderer.trimStringToWidth(name, nameW - 2 * this.nodeTextMargin); // Trim the name in case it is too big
        this.fontRenderer.drawString(name, nameX + this.nodeTextMargin, nameY + this.nodeTextMargin, RenderUtility.colorToInt(field.getDataType().getTextColor())); // Draw the trimmed name, maybe add shadow?
    }
    
    /**
     * Draw a node field dot (colored dot) at the given coordinates (not the node's coordinates!). Dot width and height are {@link #nodeFieldDotSize}.
     */
    public void drawNodeFieldDot(NodeField<?> field, int x, int y)
    {
        RenderUtility.drawRect(x, y, this.nodeFieldDotSize, this.nodeFieldDotSize, field.getDataType().getColor()[0], field.getDataType().getColor()[1], field.getDataType().getColor()[2]);
    }
    
    /**
     * Draw a node's connections (with gradient according to their data types) starting at the given coordinates (not the node's coordinates!) with dot offset being applied onto them. Alpha is {@link #nodeFieldConnectionsAlpha}.
     */
    public void drawNodeConnections(Node node, int x, int y)
    {
        NodeField<?> field;
        
        // Draw output -> input connections, not the other way around, so its not done twice
        for(int i = 0; i < node.getOutputAmt(); ++i)
        {
            field = node.getOutput(i);
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
        RenderUtility.drawGradientLine(x1, y1, x2, y2, this.getLineWidth(type1), type1.getColor()[0], type1.getColor()[1], type1.getColor()[2], this.nodeFieldConnectionsAlpha, type2.getColor()[0], type2.getColor()[1], type2.getColor()[2], this.nodeFieldConnectionsAlpha);
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
        this.drawHoverRect(x, y + (RenderUtility.getVerticalAmtMinusOne(node)) * this.nodeHeight, this.fieldWidth, this.nodeHeight);
    }
    
    public void drawShrinkHover(Node node, int x, int y)
    {
        this.drawHoverRect(x + this.fieldWidth, y + (RenderUtility.getVerticalAmtMinusOne(node)) * this.nodeHeight, this.fieldWidth, this.nodeHeight);
    }
    
    /**
     * Draw the white border over an object.
     */
    public void drawOutlineRect(int x, int y, int w, int h)
    {
        RenderUtility.drawRect(x, y, w, 2, 1F, 1F, 1F, this.hoverAlpha); // TOP, x -> x + w
        RenderUtility.drawRect(x, y + h - 2, w, 2, 1F, 1F, 1F, this.hoverAlpha);// BOT, x -> x + w
        RenderUtility.drawRect(x, y + 2, 2, h - 4, 1F, 1F, 1F, this.hoverAlpha);// LEFT, y + 2 -> y + h - 2
        RenderUtility.drawRect(x + w - 2, y + 2, 2, h - 4, 1F, 1F, 1F, this.hoverAlpha);// RIGHT, y + 2 -> y + h - 2
    }
    
    /**
     * Draw a rectangle with the color {@link #hoverColor} and alpha {@link #hoverAlpha}
     */
    public void drawHoverRect(int x, int y, int w, int h)
    {
        RenderUtility.drawRect(x, y, w, h, this.hoverColor[0], this.hoverColor[1], this.hoverColor[2], this.hoverAlpha);
    }
    
    /**
     * Draw a rectangle with the color {@link #selectColor} and alpha {@link #hoverAlpha}
     */
    public void drawSelectRect(int x, int y, int w, int h)
    {
        RenderUtility.drawRect(x, y, w, h, this.selectColor[0], this.selectColor[1], this.selectColor[2], this.hoverAlpha);
    }
    
    public void drawNodeInputValues(Node node, int x, int y)
    {
        for(int i = 0; i < node.getInputAmt(); ++i)
        {
            Input<?> in = node.getInput(i);
            
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
        
        lines.add(TextFormatting.BOLD.toString() + node.getName() + TextFormatting.RESET + " - " + this.tNode);
        
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
                lines.add(TextFormatting.GOLD.toString() + f.getNameTranslated() + TextFormatting.RESET + " - " + this.tIn);
            }
        }
        
        if(node.getOutputAmt() > 0)
        {
            lines.add("");
            
            for(i = 0; i < node.getOutputAmt(); ++i)
            {
                f = node.getOutput(i);
                lines.add(TextFormatting.DARK_RED.toString() + f.getNameTranslated() + TextFormatting.RESET + " - " + this.tOut);
            }
        }
        
        this.gui.renderTooltip(lines, x, y);
    }
    
    public void drawNodeFieldHoveringText(NodeField<?> field, int x, int y)
    {
        ArrayList<String> lines = new ArrayList<>();
        
        lines.add(TextFormatting.BOLD.toString() + (field.isOutput() ? TextFormatting.DARK_RED.toString() : TextFormatting.GOLD.toString()) + field.getNameTranslated() + TextFormatting.RESET + " - " + (field.isOutput() ? this.tOut : this.tIn));
        
        String desc;
        
        if(field.doesTriggerRecalculation())
        {
            desc = this.tRecalc;
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
        RenderUtility.drawRectWithText(this.fontRenderer, x, y, w, h, this.nodeBackground, marginRect, colorRect, marginText, text, colorText);
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
     * @see RenderUtility#getFieldOffX(NodeField)
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
     * {@link #nodeHeight} * {@link #getVerticalAmt(Node)}
     */
    public int getNodeTotalHeight(Node node)
    {
        return this.nodeHeight * RenderUtility.getVerticalAmt(node);
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
    
    public static void drawTextCentered(FontRenderer fontRenderer, int x, int y, int w, String text, float color[])
    {
        text = fontRenderer.trimStringToWidth(text, w);
        int wT = fontRenderer.getStringWidth(text);
        fontRenderer.drawString(text, x + (w - wT) / 2, y, RenderUtility.colorToInt(color));
    }
    
    /**
     * With background, for without see {@link #drawRectWithText(FontRenderer, int, int, int, int, int, float[], int, String, float[])}
     */
    public static void drawRectWithText(FontRenderer fontRenderer, int x, int y, int w, int h, float[] colorBackground, int marginRect, float[] colorRect, int marginText, String text, float[] colorText)
    {
        RenderUtility.drawRect(x, y, w, h, colorBackground[0], colorBackground[1], colorBackground[2]);
        RenderUtility.drawRectWithText(fontRenderer, x, y, w, h, marginRect, colorRect, marginText, text, colorText);
    }
    
    /**
     * Without background, for with see {@link #drawRectWithText(FontRenderer, int, int, int, int, float[], int, float[], int, String, float[])}
     */
    public static void drawRectWithText(FontRenderer fontRenderer, int x, int y, int w, int h, int marginRect, float[] colorRect, int marginText, String text, float[] colorText)
    {
        // Draw inner colored rectangle
        RenderUtility.drawRect(x + marginRect, y + marginRect, w - 2 * marginRect, h - 2 * marginRect, colorRect[0], colorRect[1], colorRect[2]);
        
        text = fontRenderer.trimStringToWidth(text, w - 2 * marginText); // Trim the text in case it is too big
        fontRenderer.drawString(text, x + marginText, y + marginText, RenderUtility.colorToInt(colorText)); // Draw the trimmed text, maybe add shadow?
    }
    
    /**
     * {@link #drawRect(int, int, int, int, float, float, float)} but with margin
     */
    public static void drawRect(int x, int y, int w, int h, int marginRect, float[] colorRect)
    {
        // Draw inner colored rectangle
        RenderUtility.drawRect(x + marginRect, y + marginRect, w - 2 * marginRect, h - 2 * marginRect, colorRect[0], colorRect[1], colorRect[2]);
    }
    
    /**
     * Since inputs and outputs share a line, only the higher amount of inputs or outputs is often needed.
     * 
     * @return The amount of inputs or outputs (whatever is higher) + 1 for the header + 1 for the footer.
     */
    public static int getVerticalAmt(Node node)
    {
        return RenderUtility.getVerticalAmtMinusOne(node);
    }
    
    public static int getVerticalAmtMinusOne(Node node)
    {
        if(node.getInputAmt() > node.getOutputAmt())
        {
            return node.getInputAmt() + 1;
        }
        else
        {
            return node.getOutputAmt() + 1;
        }
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
        RenderUtility.drawLine(x1, y1, x2, y2, 1F, r, g, b, a);
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
        RenderUtility.drawGradientLine(x1, y1, x2, y2, lineWidth, color1[0], color1[1], color1[2], alpha, color2[0], color2[1], color2[2], alpha);
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
     * {@link #drawRect(int, int, int, int, float, float, float, float)} but with alpha on max.
     */
    public static void drawRect(int x, int y, int w, int h, float r, float g, float b)
    {
        // TODO low: write separate code for rects without alpha, maybe...
        RenderUtility.drawRect(x, y, w, h, r, g, b, 1F);
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
        RenderUtility.drawBorder(x + borderWidth, y + borderWidth, w - borderWidth * 2, h - borderWidth * 2, borderWidth, r, g, b, a);
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
            return RenderUtility.isCoordInsideRect(x, y, this.x, this.y, this.w, this.h);
        }
        
        public void render(float r, float g, float b)
        {
            RenderUtility.drawRect(this.x, this.y, this.w, this.h, r, g, b);
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
    
    public static float[] mixColors(float[] color1, float[] color2)
    {
        float r = (color1[0] + color2[0]) * 0.5F;
        float g = (color1[1] + color2[1]) * 0.5F;
        float b = (color1[2] + color2[2]) * 0.5F;
        
        return new float[] { r, g, b };
    }
}

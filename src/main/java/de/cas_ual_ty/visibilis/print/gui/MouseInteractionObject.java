package de.cas_ual_ty.visibilis.print.gui;

import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import net.minecraft.client.gui.GuiTextField;

public class MouseInteractionObject
{
    /*
     * Helper class to help with whatever the mouse is hovering over at the current moment
     */
    
    public MouseInteractionType type;
    
    public Node node;
    public NodeField nodeField;
    public GuiTextField textField;
    public Input input;
    public int inputEnumId;
    
    public MouseInteractionObject()
    {
        this.reset();
    }
    
    private void reset()
    {
        this.type = MouseInteractionType.NOTHING;
        
        this.node = null;
        this.nodeField = null;
        this.textField = null;
        this.input = null;
        this.inputEnumId = -1;
    }
    
    public void inherit(MouseInteractionObject obj)
    {
        this.type = obj.type;
        
        this.node = obj.node;
        this.nodeField = obj.nodeField;
        this.textField = obj.textField;
        
        obj.nothing();
    }
    
    public void nothing()
    {
        this.reset();
    }
    
    public boolean isNothing()
    {
        return this.type == MouseInteractionType.NOTHING;
    }
    
    public void node(Node node)
    {
        this.reset();
        this.node = node;
        this.type = MouseInteractionType.NODE;
    }
    
    public void nodeHeader(Node node)
    {
        this.reset();
        this.node = node;
        this.type = MouseInteractionType.NODE_HEADER;
    }
    
    public void nodeField(NodeField nodeField)
    {
        this.reset();
        this.node = nodeField.node;
        this.nodeField = nodeField;
        this.type = MouseInteractionType.NODE_FIELD;
    }
    
    public void nodeActionExpand(Node node)
    {
        this.reset();
        this.node = this.nodeField.node;
        this.node = node;
        this.type = MouseInteractionType.NODE_ACTION_EXPAND;
    }
    
    public void nodeActionShrink(Node node)
    {
        this.reset();
        this.node = node;
        this.type = MouseInteractionType.NODE_ACTION_SHRINK;
    }
    
    public void textField(GuiTextField textField)
    {
        this.reset();
        this.textField = textField;
        this.type = MouseInteractionType.TEXT_FIELD;
    }
    
    public void inputEnum(Input input, int enumId)
    {
        this.reset();
        this.input = input;
        this.inputEnumId = enumId;
    }
    
    public static enum MouseInteractionType
    {
        NOTHING, NODE, NODE_HEADER, NODE_FIELD, NODE_ACTION_EXPAND, NODE_ACTION_SHRINK, TEXT_FIELD, INPUT_ENUM
    }
}

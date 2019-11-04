package de.cas_ual_ty.visibilis.print.gui.component;

import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import de.cas_ual_ty.visibilis.node.Output;
import net.minecraft.client.gui.GuiTextField;

public class MouseInteractionObject
{
    /*
     * Helper class to help with whatever the mouse is hovering over at the current moment
     */
    
    public MouseInteractionType type;
    
    public Node node;
    public NodeField nodeField;
    public Output output;
    public Input input;
    public GuiTextField textField;
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
        this.output = null;
        this.input = null;
        this.textField = null;
        this.inputEnumId = -1;
    }
    
    public void inherit(MouseInteractionObject obj)
    {
        this.type = obj.type;
        
        this.node = obj.node;
        this.nodeField = obj.nodeField;
        this.output = obj.output;
        this.input = obj.input;
        this.textField = obj.textField;
        this.inputEnumId = obj.inputEnumId;
        
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
    
    public void output(NodeField nodeField)
    {
        this.reset();
        
        if (nodeField.isOutput())
        {
            this.output((Output) nodeField);
        }
    }
    
    public void output(Output output)
    {
        this.reset();
        this.node = output.node;
        this.nodeField = output;
        this.output = output;
        this.type = MouseInteractionType.OUTPUT;
    }
    
    public void input(NodeField nodeField)
    {
        this.reset();
        
        if (nodeField.isInput())
        {
            this.input((Input) nodeField);
        }
    }
    
    public void input(Input input)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.type = MouseInteractionType.INPUT;
    }
    
    public void nodeActionExpand(Node node)
    {
        this.reset();
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
    
    public void inputEnum(Input input)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.type = MouseInteractionType.INPUT_ENUM;
    }
    
    public void inputDynamic(Input input)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.type = MouseInteractionType.INPUT_DYNAMIC;
    }
    
    public void inputEnumId(Input input, int enumId)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.inputEnumId = enumId;
        this.type = MouseInteractionType.INPUT_ENUM_ID;
    }
    
    public static enum MouseInteractionType
    {
        NOTHING, NODE, NODE_HEADER, INPUT, OUTPUT, NODE_ACTION_EXPAND, NODE_ACTION_SHRINK, TEXT_FIELD, INPUT_ENUM, INPUT_DYNAMIC, INPUT_ENUM_ID
    }
}

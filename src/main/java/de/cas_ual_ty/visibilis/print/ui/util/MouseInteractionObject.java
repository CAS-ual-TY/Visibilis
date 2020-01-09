package de.cas_ual_ty.visibilis.print.ui.util;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class MouseInteractionObject
{
    /*
     * Helper class to help with whatever the mouse is hovering over at the current moment
     */
    
    public EnumMouseInteractionType type;
    
    public Node node;
    public NodeField<?> nodeField;
    public Output<?> output;
    public Input<?> input;
    public TextFieldWidget textField;
    public int inputEnumId;
    
    public MouseInteractionObject()
    {
        this.reset();
    }
    
    private void reset()
    {
        this.type = EnumMouseInteractionType.NOTHING;
        
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
        return this.type == EnumMouseInteractionType.NOTHING;
    }
    
    public void node(Node node)
    {
        this.reset();
        this.node = node;
        this.type = EnumMouseInteractionType.NODE;
    }
    
    public void nodeHeader(Node node)
    {
        this.reset();
        this.node = node;
        this.type = EnumMouseInteractionType.NODE_HEADER;
    }
    
    public void output(NodeField<?> nodeField)
    {
        this.reset();
        
        if (nodeField.isOutput())
        {
            this.output((Output<?>) nodeField);
        }
    }
    
    public void output(Output<?> output)
    {
        this.reset();
        this.node = output.node;
        this.nodeField = output;
        this.output = output;
        this.type = EnumMouseInteractionType.OUTPUT;
    }
    
    public void input(NodeField<?> nodeField)
    {
        this.reset();
        
        if (nodeField.isInput())
        {
            this.input((Input<?>) nodeField);
        }
    }
    
    public void input(Input<?> input)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.type = EnumMouseInteractionType.INPUT;
    }
    
    public void textField(TextFieldWidget textField)
    {
        this.reset();
        this.textField = textField;
        this.type = EnumMouseInteractionType.TEXT_FIELD;
    }
    
    public void inputEnum(Input<?> input)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.type = EnumMouseInteractionType.INPUT_ENUM;
    }
    
    public void inputDynamic(Input<?> input)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.type = EnumMouseInteractionType.INPUT_DYNAMIC;
    }
    
    public void inputEnumId(Input<?> input, int enumId)
    {
        this.reset();
        this.node = input.node;
        this.nodeField = input;
        this.input = input;
        this.inputEnumId = enumId;
        this.type = EnumMouseInteractionType.INPUT_ENUM_ID;
    }
    
    public static enum EnumMouseInteractionType
    {
        NOTHING, NODE, NODE_HEADER, INPUT, OUTPUT, TEXT_FIELD, INPUT_ENUM, INPUT_DYNAMIC, INPUT_ENUM_ID
    }
}

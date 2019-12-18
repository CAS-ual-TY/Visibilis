package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.node.general.NodeX;

public abstract class NodeExpandableNumber extends NodeX
{
    public Number value;
    
    public NodeExpandableNumber()
    {
        super();
        this.addOutput(new Output<Number>(this, DataType.NUMBER, "out1"));
        this.addInput(this.createDynamicInput());
    }
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<Number>(this, DataType.NUMBER, "in1");
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == 0)
        {
            return (B) this.value;
        }
        
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return DataType.NUMBER.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return DataType.NUMBER.getTextColor();
    }
}

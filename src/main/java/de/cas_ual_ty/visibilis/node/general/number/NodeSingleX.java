package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.node.general.NodeX;

public abstract class NodeSingleX<A> extends NodeX
{
    public A value;
    
    public NodeSingleX()
    {
        super();
        this.addOutput(new Output<A>(this, this.getDataType(), "out1"));
        this.addInput(new Input<A>(this, this.getDataType(), "in1"));
        this.addInput(new Input<A>(this, this.getDataType(), "in1"));
    }
    
    public abstract DataType getDataType();
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getDataType(), "in1");
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
        return this.getDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getDataType().getTextColor();
    }
}

package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.node.general.NodeP;

public abstract class NodeSingleP<A> extends NodeP
{
    public A[] values;
    
    public NodeSingleP()
    {
        super();
        this.addOutput(new Output<A>(this, this.getDataType(), "out1"));
        this.addInput(new Input<A>(this, this.getDataType(), "in1"));
    }
    
    public abstract DataType getDataType();
    
    @Override
    public Output createDynamicOutput()
    {
        return new Output<A>(this, this.getDataType(), "out1");
    }
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getDataType(), "in1");
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index >= 0 && index < this.expansion)
        {
            return (B) this.values[index];
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

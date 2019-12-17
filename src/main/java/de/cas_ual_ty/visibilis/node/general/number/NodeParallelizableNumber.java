package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.node.general.NodeParallelizable;

public abstract class NodeParallelizableNumber extends NodeParallelizable
{
    public Number value;
    public Number[] values;
    
    public NodeParallelizableNumber()
    {
        super();
        this.addOutput(this.createDynamicOutput());
        this.addInput(this.createDynamicInput());
    }
    
    @Override
    public Output createDynamicOutput()
    {
        return new Output<Number>(this, DataType.NUMBER, "out1");
    }
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<Number>(this, DataType.NUMBER, "in1");
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (!this.parallelized && index == 0)
        {
            return (B) this.value;
        }
        else if (this.parallelized && index >= 0 && index < this.values.length)
        {
            return (B) this.values[index];
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

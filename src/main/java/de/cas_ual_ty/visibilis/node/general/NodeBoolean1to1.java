package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeBoolean1to1 extends Node
{
    public final Output<Boolean> out1;
    public final Input<Boolean> in1;
    
    public boolean value;
    
    public NodeBoolean1to1()
    {
        super();
        this.out1 = new Output<>(this, DataType.BOOLEAN, "out1");
        this.in1 = new Input<>(this, DataType.BOOLEAN, "in1");
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        if (!this.canCalculate(this.in1.getValue()))
        {
            return false;
        }
        
        this.value = this.calculate(this.in1.getValue());
        
        return true;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
     * 
     * @param in1
     *            The first input
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(boolean in1)
    {
        return true;
    }
    
    /**
     * Calculate the result using the 2 input numbers.
     * 
     * @param in1
     *            The first input
     * @param in2
     *            The 2nd input
     * @return The result.
     */
    protected abstract boolean calculate(boolean in1);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.getId())
        {
            return (B) (Boolean) this.value;
        }
        
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return DataType.BOOLEAN.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return DataType.BOOLEAN.getTextColor();
    }
}

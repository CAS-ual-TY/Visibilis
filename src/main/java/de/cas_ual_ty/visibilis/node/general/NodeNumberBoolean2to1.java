package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumberBoolean2to1 extends Node
{
    public final Output<Boolean> out1;
    public final Input<Number> in1;
    public final Input<Number> in2;
    
    public Boolean value;
    
    public NodeNumberBoolean2to1()
    {
        super();
        this.addOutput(this.out1 = new Output<>(this, DataType.BOOLEAN, "out1"));
        this.addInput(this.in1 = new Input<>(this, DataType.NUMBER, "in1"));
        this.addInput(this.in2 = new Input<>(this, DataType.NUMBER, "in2"));
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        if (!this.canCalculate(this.in1.getValue(), this.in2.getValue()))
        {
            return false;
        }
        
        this.value = this.calculate(this.in1.getValue(), this.in2.getValue());
        
        return true;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
     * 
     * @param in1
     *            The first input
     * @param in2
     *            The 2nd input
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(Number in1, Number in2)
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
    protected abstract Boolean calculate(Number in1, Number in2);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.getId())
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

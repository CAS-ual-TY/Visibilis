package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class NodeNumber1to1 extends Node
{
    public final Output<Number> out1;
    public final Input<Number> in1;
    
    public Number value;
    
    public NodeNumber1to1(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Number>(this, DataType.NUMBER, "out1");
        this.in1 = new Input<Number>(this, DataType.NUMBER, "in1");
    }
    
    public NodeNumber1to1()
    {
        this(1, 1);
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
    protected boolean canCalculate(Number in1)
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
    protected abstract Number calculate(Number in1);
    
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

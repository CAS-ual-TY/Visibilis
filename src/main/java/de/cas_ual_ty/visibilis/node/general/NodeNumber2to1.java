package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.VDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class NodeNumber2to1 extends Node
{
    public final Output<Number> out1;
    public final Input<Number> in1;
    public final Input<Number> in2;
    
    public Number value;
    
    public NodeNumber2to1(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Number>(0, this, VDataType.NUMBER, "number");
        this.in1 = new Input<Number>(0, this, VDataType.NUMBER, "number");
        this.in2 = new Input<Number>(1, this, VDataType.NUMBER, "number");
    }
    
    public NodeNumber2to1()
    {
        this(1, 2);
    }
    
    @Override
    public boolean doCalculate()
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
    protected abstract Number calculate(Number in1, Number in2);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.id)
        {
            return (B) this.value;
        }
        
        return null;
    }
}

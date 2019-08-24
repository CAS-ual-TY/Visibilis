package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class NodeBoolean2to1 extends Node
{
    public final Output<Boolean> out1;
    public final Input<Boolean> in1;
    public final Input<Boolean> in2;
    
    public boolean value;
    
    public NodeBoolean2to1(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Boolean>(0, this, DataType.BOOLEAN, "out1");
        this.in1 = new Input<Boolean>(0, this, DataType.BOOLEAN, "in1");
        this.in2 = new Input<Boolean>(1, this, DataType.BOOLEAN, "in2");
    }
    
    public NodeBoolean2to1()
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
    protected boolean canCalculate(boolean in1, boolean in2)
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
    protected abstract boolean calculate(boolean in1, boolean in2);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.id)
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
}

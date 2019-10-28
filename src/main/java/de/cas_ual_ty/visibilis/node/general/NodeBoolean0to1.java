package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class NodeBoolean0to1 extends Node
{
    public final Output<Boolean> out1;
    
    public boolean value;
    
    public NodeBoolean0to1(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Boolean>(0, this, DataType.BOOLEAN, "out1");
    }
    
    public NodeBoolean0to1()
    {
        this(1, 0);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.value = this.getValue();
        return true;
    }
    
    /**
     * @return The static value.
     */
    protected abstract boolean getValue();
    
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
    
    @Override
    public float[] getTextColor()
    {
        return DataType.BOOLEAN.getTextColor();
    }
}

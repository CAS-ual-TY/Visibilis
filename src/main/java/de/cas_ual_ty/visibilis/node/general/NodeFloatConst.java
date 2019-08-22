package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class NodeFloatConst extends Node
{
    public final Output<Float> out1;
    
    public float value;
    
    public NodeFloatConst(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Float>(0, this, DataType.FLOAT, "out1");
    }
    
    public NodeFloatConst()
    {
        this(1, 0);
    }
    
    @Override
    public boolean doCalculate()
    {
        this.value = this.getValue();
        return true;
    }
    
    /**
     * @return The static value.
     */
    protected abstract float getValue();
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.id)
        {
            return (B) (Float) this.value;
        }
        
        return null;
    }
}

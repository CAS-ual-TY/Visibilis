package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeGenericConstant<B> extends Node
{
    public Output<B> out1B;
    
    public NodeGenericConstant()
    {
        super();
        this.addOutput(this.out1B = new Output<>(this, this.getDataType(), "out1"));
    }
    
    public abstract DataType<B> getDataType();
    
    protected abstract B getConstant();
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out1B ? VUtility.cast(this.getConstant()) : null;
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

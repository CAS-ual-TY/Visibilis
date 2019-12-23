package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeGenericC<A> extends de.cas_ual_ty.visibilis.node.Node
{
    public Output<A> out1;
    public Input<A> in1;
    
    public NodeGenericC()
    {
        super();
        this.addOutput(this.out1 = new Output<A>(this, this.getDataType(), "out1"));
    }
    
    public abstract DataType getDataType();
    
    protected abstract A getConstant();
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return index == 0 ? (B) this.getConstant() : null;
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

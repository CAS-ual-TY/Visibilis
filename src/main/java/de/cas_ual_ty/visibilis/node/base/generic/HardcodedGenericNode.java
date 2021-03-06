package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class HardcodedGenericNode<B> extends Node
{
    public Output<B> out1B;
    
    public HardcodedGenericNode(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1B = new Output<>(this, this.getDataType(), "out1"));
    }
    
    public abstract DataType<B> getDataType();
    
    protected abstract B getConstant();
    
    @Override
    public boolean doCalculate(DataProvider context)
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
    
    public static <O> NodeType.IFactory<HardcodedGenericNode<O>> createTypeGenericConstant(DataType<O> dataType, O constant)
    {
        return (type) ->
        {
            return new HardcodedGenericNode<O>(type)
            {
                @Override
                public DataType<O> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected O getConstant()
                {
                    return constant;
                }
            };
        };
    }
}

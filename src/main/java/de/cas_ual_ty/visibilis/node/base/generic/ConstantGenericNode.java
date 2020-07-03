package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;

public abstract class ConstantGenericNode<B> extends PGenericNode<B>
{
    public ConstantGenericNode(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected B calculate(B input)
    {
        return input;
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
    
    public static <I> NodeType.IFactory<ConstantGenericNode<I>> createTypeGenericV(DataType<I> dataType)
    {
        return (type) ->
        {
            return new ConstantGenericNode<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
            };
        };
    }
}

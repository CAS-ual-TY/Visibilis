package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;

public abstract class NodeGenericConstant<B> extends NodeGenericP<B>
{
    public NodeGenericConstant(NodeType<?> type)
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
    
    public static <I> NodeType.IFactory<NodeGenericConstant<I>> createTypeGenericV(DataType<I> dataType)
    {
        return (type) ->
        {
            return new NodeGenericConstant<I>(type)
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

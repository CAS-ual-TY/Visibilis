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
    
    public static <I> NodeType<NodeGenericConstant<I>> createTypeGenericV(DataType<I> dataType)
    {
        return new NodeType<>((type) ->
        {
            return new NodeGenericConstant<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
            };
        });
    }
}
package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;

public abstract class NodeGenericV<B> extends NodeGenericP<B>
{
    public NodeGenericV(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected B calculate(B input)
    {
        return input;
    }
    
    public static <I> NodeType<NodeGenericV<I>> createType(DataType<I> dataType, String id)
    {
        return new NodeType<>((type) ->
        {
            return new NodeGenericV<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
}

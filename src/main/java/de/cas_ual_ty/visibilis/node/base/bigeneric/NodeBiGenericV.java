package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericV<O, I> extends NodeBiGenericP<O, I>
{
    public NodeBiGenericV(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected O calculate(I input)
    {
        return VUtility.cast(input);
    }
    
    public static <O, I> NodeType<NodeBiGenericV<O, I>> createTypeBiGenericV(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, Function<I, O> function)
    {
        return new NodeType<>((type) ->
        {
            return new NodeBiGenericV<O, I>(type)
            {
                @Override
                public DataType<O> getOutDataType()
                {
                    return dataTypeOut;
                }
                
                @Override
                public DataType<I> getInDataType()
                {
                    return dataTypeIn;
                }
                
                @Override
                protected O calculate(I input)
                {
                    return function.apply(input);
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

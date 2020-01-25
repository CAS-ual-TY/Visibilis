package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.function.BiFunction;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.trigeneric.NodeTriGenericP2;

public abstract class NodeBiGenericP2<O, I> extends NodeTriGenericP2<O, I, I>
{
    public NodeBiGenericP2(NodeType<?> type)
    {
        super(type);
    }
    
    public abstract DataType<I> getInDataType();
    
    @Override
    public DataType<I> getIn1DataType()
    {
        return this.getInDataType();
    }
    
    @Override
    public DataType<I> getIn2DataType()
    {
        return this.getInDataType();
    }
    
    public static <O, I> NodeType.IFactory<NodeBiGenericP2<O, I>> createTypeBiGenericP2(DataType<O> dataTypeOut, DataType<I> dataTypeIn, BiFunction<I, I, O> function)
    {
        return NodeBiGenericP2.createTypeBiGenericP2(dataTypeOut, dataTypeIn, function, (input, in2) -> true);
    }
    
    public static <O, I> NodeType.IFactory<NodeBiGenericP2<O, I>> createTypeBiGenericP2(DataType<O> dataTypeOut, DataType<I> dataTypeIn, BiFunction<I, I, O> function, BiFunction<I, I, Boolean> requirement)
    {
        return (type) ->
        {
            return new NodeBiGenericP2<O, I>(type)
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
                protected boolean canCalculate(I input, I in2)
                {
                    return requirement.apply(input, in2);
                }
                
                @Override
                protected O calculate(I input, I in2)
                {
                    return function.apply(input, in2);
                }
            };
        };
    }
}

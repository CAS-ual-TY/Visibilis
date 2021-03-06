package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.function.BiFunction;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.trigeneric.P2TriGenericNode;

public abstract class P2BiGenericNode<O, I> extends P2TriGenericNode<O, I, I>
{
    public P2BiGenericNode(NodeType<?> type)
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
    
    @Override
    public float[] getColor()
    {
        return this.getOutDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getOutDataType().getTextColor();
    }
    
    public static <O, I> NodeType.IFactory<P2BiGenericNode<O, I>> createTypeBiGenericP2(DataType<O> dataTypeOut, DataType<I> dataTypeIn, BiFunction<I, I, O> function)
    {
        return P2BiGenericNode.createTypeBiGenericP2(dataTypeOut, dataTypeIn, function, (input, in2) -> true);
    }
    
    public static <O, I> NodeType.IFactory<P2BiGenericNode<O, I>> createTypeBiGenericP2(DataType<O> dataTypeOut, DataType<I> dataTypeIn, BiFunction<I, I, O> function, BiFunction<I, I, Boolean> requirement)
    {
        return (type) ->
        {
            return new P2BiGenericNode<O, I>(type)
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

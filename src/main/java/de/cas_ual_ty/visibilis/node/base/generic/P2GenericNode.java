package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.BiFunction;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.P2BiGenericNode;

public abstract class P2GenericNode<B> extends P2BiGenericNode<B, B>
{
    public P2GenericNode(NodeType<?> type)
    {
        super(type);
    }
    
    public abstract DataType<B> getDataType();
    
    @Override
    public DataType<B> getOutDataType()
    {
        return this.getDataType();
    }
    
    @Override
    public DataType<B> getInDataType()
    {
        return this.getDataType();
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
    
    public static <I> NodeType.IFactory<P2GenericNode<I>> createTypeGenericP2(DataType<I> dataType, BiFunction<I, I, I> function)
    {
        return P2GenericNode.createTypeGenericP2(dataType, function, (input, in2) -> true);
    }
    
    public static <I> NodeType.IFactory<P2GenericNode<I>> createTypeGenericP2(DataType<I> dataType, BiFunction<I, I, I> function, BiFunction<I, I, Boolean> requirement)
    {
        return (type) ->
        {
            return new P2GenericNode<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected boolean canCalculate(I input, I in2)
                {
                    return requirement.apply(input, in2);
                }
                
                @Override
                protected I calculate(I input, I in2)
                {
                    return function.apply(input, in2);
                }
            };
        };
    }
}

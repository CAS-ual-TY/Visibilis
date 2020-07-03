package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.BiFunction;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.XP2BiGenericNode;

public abstract class XP2GenericNode<B> extends XP2BiGenericNode<B, B>
{
    public XP2GenericNode(NodeType<?> type)
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
    
    public static <I> NodeType.IFactory<XP2GenericNode<I>> createTypeGenericXP2(DataType<I> dataType, Function<I[], I> functionX, BiFunction<I, I, I> functionP2)
    {
        return XP2GenericNode.createTypeGenericXP2(dataType, functionX, functionP2, (inputs) -> true, (input, in2) -> true);
    }
    
    public static <I> NodeType.IFactory<XP2GenericNode<I>> createTypeGenericXP2(DataType<I> dataType, Function<I[], I> functionX, BiFunction<I, I, I> functionP2, Function<I[], Boolean> requirementX, BiFunction<I, I, Boolean> requirementP2)
    {
        return (type) ->
        {
            return new XP2GenericNode<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected boolean canCalculate(I[] inputs)
                {
                    return requirementX.apply(inputs);
                }
                
                @Override
                protected I calculate(I[] inputs)
                {
                    return functionX.apply(inputs);
                }
                
                @Override
                protected boolean canCalculate(I input, I in2)
                {
                    return requirementP2.apply(input, in2);
                }
                
                @Override
                protected I calculate(I input, I in2)
                {
                    return functionP2.apply(input, in2);
                }
            };
        };
    }
}

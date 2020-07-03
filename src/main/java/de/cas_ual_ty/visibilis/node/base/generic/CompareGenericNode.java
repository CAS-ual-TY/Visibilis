package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.BiFunction;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.P2BiGenericNode;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class CompareGenericNode<I> extends P2BiGenericNode<Boolean, I>
{
    public CompareGenericNode(NodeType<?> type)
    {
        super(type);
    }
    
    public abstract DataType<I> getDataType();
    
    protected abstract Boolean compare(I input, I in2);
    
    @Override
    protected Boolean calculate(I input, I in2)
    {
        return this.compare(input, in2);
    }
    
    @Override
    public DataType<Boolean> getOutDataType()
    {
        return VDataTypes.BOOLEAN;
    }
    
    @Override
    public DataType<I> getInDataType()
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
    
    public static <I> NodeType.IFactory<CompareGenericNode<I>> createTypeGenericCompare(DataType<I> dataType, BiFunction<I, I, Boolean> function)
    {
        return (type) ->
        {
            return new CompareGenericNode<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected Boolean compare(I input, I in2)
                {
                    return function.apply(input, in2);
                }
            };
        };
    }
}

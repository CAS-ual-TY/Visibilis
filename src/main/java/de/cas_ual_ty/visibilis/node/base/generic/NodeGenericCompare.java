package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.BiFunction;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeGenericCompare<I> extends NodeBiGenericP2<Boolean, I>
{
    public NodeGenericCompare(NodeType<?> type)
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
        return this.getOutDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getOutDataType().getTextColor();
    }
    
    public static <I> NodeType<NodeGenericCompare<I>> createTypeGenericCompare(DataType<I> dataType, BiFunction<I, I, Boolean> function)
    {
        return new NodeType<>((type) ->
        {
            return new NodeGenericCompare<I>(type)
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
        });
    }
}

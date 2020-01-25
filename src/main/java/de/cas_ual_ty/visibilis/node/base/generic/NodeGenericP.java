package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;

public abstract class NodeGenericP<B> extends NodeBiGenericP<B, B>
{
    public NodeGenericP(NodeType<?> type)
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
    
    public static <I> NodeType.IFactory<NodeGenericP<I>> createTypeGenericP(DataType<I> dataType, Function<I, I> function)
    {
        return NodeGenericP.createTypeGenericP(dataType, function, (input) -> true);
    }
    
    public static <I> NodeType.IFactory<NodeGenericP<I>> createTypeGenericP(DataType<I> dataType, Function<I, I> function, Function<I, Boolean> requirement)
    {
        return (type) ->
        {
            return new NodeGenericP<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected boolean canCalculate(I input)
                {
                    return requirement.apply(input);
                }
                
                @Override
                protected I calculate(I input)
                {
                    return function.apply(input);
                }
            };
        };
    }
}

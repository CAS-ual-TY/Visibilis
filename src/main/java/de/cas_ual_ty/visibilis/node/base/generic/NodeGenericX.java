package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericX;

public abstract class NodeGenericX<B> extends NodeBiGenericX<B, B>
{
    public NodeGenericX(NodeType<?> type)
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
    
    public static <I> NodeType<NodeGenericX<I>> createTypeGenericX(DataType<I> dataType, String id, Function<I[], I> function)
    {
        return NodeGenericX.createTypeGenericX(dataType, id, function, (inputs) -> true);
    }
    
    public static <I> NodeType<NodeGenericX<I>> createTypeGenericX(DataType<I> dataType, String id, Function<I[], I> function, Function<I[], Boolean> requirement)
    {
        return new NodeType<>((type) ->
        {
            return new NodeGenericX<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected boolean canCalculate(I[] inputs)
                {
                    return requirement.apply(inputs);
                }
                
                @Override
                protected I calculate(I[] inputs)
                {
                    return function.apply(inputs);
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

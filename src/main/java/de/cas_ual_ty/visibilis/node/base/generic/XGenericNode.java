package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.XBiGenericNode;

public abstract class XGenericNode<B> extends XBiGenericNode<B, B>
{
    public XGenericNode(NodeType<?> type)
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
    
    public static <I> NodeType.IFactory<XGenericNode<I>> createTypeGenericX(DataType<I> dataType, Function<I[], I> function)
    {
        return XGenericNode.createTypeGenericX(dataType, function, (inputs) -> true);
    }
    
    public static <I> NodeType.IFactory<XGenericNode<I>> createTypeGenericX(DataType<I> dataType, Function<I[], I> function, Function<I[], Boolean> requirement)
    {
        return (type) ->
        {
            return new XGenericNode<I>(type)
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
            };
        };
    }
}

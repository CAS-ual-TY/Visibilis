package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP2;

public abstract class NodeGenericP2<B> extends NodeBiGenericP2<B, B>
{
    public NodeGenericP2(NodeType<?> type)
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
    
    public static <I> NodeType<NodeGenericP2<I>> createType(DataType<I> dataType, String id, ICalculation<I> function)
    {
        return new NodeType<>((type) ->
        {
            return new NodeGenericP2<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected I calculate(I input, I in2)
                {
                    return function.calculate(input, in2);
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
    
    public static interface ICalculation<I>
    {
        I calculate(I input, I in2);
    }
}

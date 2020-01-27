package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericConstant<O, I> extends NodeBiGenericP<O, I>
{
    public NodeBiGenericConstant(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected O calculate(I input)
    {
        return VUtility.cast(input);
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
    
    public static <O, I> NodeType.IFactory<NodeBiGenericConstant<O, I>> createTypeBiGenericV(DataType<O> dataTypeOut, DataType<I> dataTypeIn, Function<I, O> function)
    {
        return (type) ->
        {
            return new NodeBiGenericConstant<O, I>(type)
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
                protected O calculate(I input)
                {
                    return function.apply(input);
                }
            };
        };
    }
}

package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;

public abstract class NodeGenericP<B> extends NodeBiGenericP<B, B>
{
    public NodeGenericP()
    {
        super();
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
    
    public static <H> NodeType<NodeGenericP<H>> createType(DataType<H> dataType, String id, ICalculation<H> function)
    {
        return new NodeType<>(() ->
        {
            return new NodeGenericP<H>()
            {
                @Override
                public DataType<H> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected H calculate(H input)
                {
                    return function.calculate(input);
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
        I calculate(I input);
    }
}

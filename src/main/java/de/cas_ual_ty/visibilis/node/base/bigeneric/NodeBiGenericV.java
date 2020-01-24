package de.cas_ual_ty.visibilis.node.base.bigeneric;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericV<O, I> extends NodeBiGenericP<O, I>
{
    public NodeBiGenericV()
    {
        super();
    }
    
    @Override
    protected O calculate(I input)
    {
        return VUtility.cast(input);
    }
    
    public static <O, I> NodeType<NodeBiGenericV<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculation<O, I> function, ICalculationRequirement<O, I> requirement)
    {
        return new NodeType<>(() ->
        {
            return new NodeBiGenericV<O, I>()
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
    
    public static interface ICalculation<O, I>
    {
        O calculate(I input);
    }
}

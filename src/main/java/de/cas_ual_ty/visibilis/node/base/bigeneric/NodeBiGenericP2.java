package de.cas_ual_ty.visibilis.node.base.bigeneric;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.trigeneric.NodeTriGenericP2;

public abstract class NodeBiGenericP2<O, I> extends NodeTriGenericP2<O, I, I>
{
    public NodeBiGenericP2()
    {
        super();
    }
    
    public abstract DataType<I> getInDataType();
    
    @Override
    public DataType<I> getIn1DataType()
    {
        return this.getInDataType();
    }
    
    @Override
    public DataType<I> getIn2DataType()
    {
        return this.getInDataType();
    }
    
    public static <O, I> NodeType<NodeBiGenericP2<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculation<O, I> function)
    {
        return NodeBiGenericP2.createType(dataTypeOut, dataTypeIn, id, function, (input, in2) -> true);
    }
    
    public static <O, I> NodeType<NodeBiGenericP2<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculation<O, I> function, ICalculationRequirement<O, I> requirement)
    {
        return new NodeType<>(() ->
        {
            return new NodeBiGenericP2<O, I>()
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
                protected boolean canCalculate(I input, I in2)
                {
                    return requirement.canCalculate(input, in2);
                }
                
                @Override
                protected O calculate(I input, I in2)
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
    
    public static interface ICalculationRequirement<O, I>
    {
        boolean canCalculate(I input, I in2);
    }
    
    public static interface ICalculation<O, I>
    {
        O calculate(I input, I in2);
    }
}

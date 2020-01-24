package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericX;

public abstract class NodeGenericX<B> extends NodeBiGenericX<B, B>
{
    public NodeGenericX()
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
    
    public static <I> NodeType<NodeGenericX<I>> createType(DataType<I> dataType, String id, ICalculation<I> function)
    {
        return NodeGenericX.createType(dataType, id, function, (inputs) -> true);
    }
    
    public static <I> NodeType<NodeGenericX<I>> createType(DataType<I> dataType, String id, ICalculation<I> function, ICalculationRequirement<I> requirement)
    {
        return new NodeType<>(() ->
        {
            return new NodeGenericX<I>()
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected boolean canCalculate(I[] inputs)
                {
                    return requirement.canCalculate(inputs);
                }
                
                @Override
                protected I calculate(I[] inputs)
                {
                    return function.calculate(inputs);
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
    
    public static interface ICalculationRequirement<I>
    {
        boolean canCalculate(I[] inputs);
    }
    
    public static interface ICalculation<I>
    {
        I calculate(I[] inputs);
    }
}

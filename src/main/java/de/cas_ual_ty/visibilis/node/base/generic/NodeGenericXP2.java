package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericXP2;

public abstract class NodeGenericXP2<B> extends NodeBiGenericXP2<B, B>
{
    public NodeGenericXP2(NodeType<?> type)
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
    
    public static <I> NodeType<NodeGenericXP2<I>> createType(DataType<I> dataType, String id, ICalculationX<I> functionX, ICalculationP2<I> functionP2)
    {
        return NodeGenericXP2.createType(dataType, id, functionX, functionP2, (inputs) -> true, (input, in2) -> true);
    }
    
    public static <I> NodeType<NodeGenericXP2<I>> createType(DataType<I> dataType, String id, ICalculationX<I> functionX, ICalculationP2<I> functionP2, ICalculationRequirementX<I> requirementX, ICalculationRequirementP2<I> requirementP2)
    {
        return new NodeType<>((type) ->
        {
            return new NodeGenericXP2<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
                
                @Override
                protected boolean canCalculate(I[] inputs)
                {
                    return requirementX.canCalculate(inputs);
                }
                
                @Override
                protected I calculate(I[] inputs)
                {
                    return functionX.calculate(inputs);
                }
                
                @Override
                protected boolean canCalculate(I input, I in2)
                {
                    return requirementP2.canCalculate(input, in2);
                }
                
                @Override
                protected I calculate(I input, I in2)
                {
                    return functionP2.calculate(input, in2);
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
    
    public static interface ICalculationRequirementX<I>
    {
        boolean canCalculate(I[] inputs);
    }
    
    public static interface ICalculationX<I>
    {
        I calculate(I[] inputs);
    }
    
    public static interface ICalculationRequirementP2<I>
    {
        boolean canCalculate(I input, I in2);
    }
    
    public static interface ICalculationP2<I>
    {
        I calculate(I input, I in2);
    }
}

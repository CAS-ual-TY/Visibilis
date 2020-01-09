package de.cas_ual_ty.visibilis.node.cast;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;

public class NodeFloatToInteger extends NodeBiGenericP<Float, Integer>
{
    public NodeFloatToInteger()
    {
        super();
    }
    
    @Override
    public DataType<Integer> getOutDataType()
    {
        return DataType.INTEGER;
    }
    
    @Override
    public DataType<Float> getInDataType()
    {
        return DataType.FLOAT;
    }
    
    @Override
    protected Integer calculate(Float input)
    {
        return (int)input.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "cast_integer";
    }
}

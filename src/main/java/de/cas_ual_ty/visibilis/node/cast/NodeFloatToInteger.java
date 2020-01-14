package de.cas_ual_ty.visibilis.node.cast;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeFloatToInteger extends NodeBiGenericP<Integer, Float>
{
    public NodeFloatToInteger()
    {
        super();
    }
    
    @Override
    public DataType<Integer> getOutDataType()
    {
        return VDataTypes.INTEGER;
    }
    
    @Override
    public DataType<Float> getInDataType()
    {
        return VDataTypes.FLOAT;
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

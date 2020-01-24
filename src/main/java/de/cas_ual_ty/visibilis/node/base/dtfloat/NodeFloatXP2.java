package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatXP2 extends NodeGenericXP2<Float>
{
    public NodeFloatXP2(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected boolean canCalculate(Float input, Float in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected abstract Float calculate(Float input, Float in2);
    
    @Override
    protected boolean canCalculate(Float[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Float calculate(Float[] inputs);
}

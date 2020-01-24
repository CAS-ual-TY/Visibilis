package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeDoubleXP2 extends NodeGenericXP2<Double>
{
    public NodeDoubleXP2(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    protected boolean canCalculate(Double input, Double in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected abstract Double calculate(Double input, Double in2);
    
    @Override
    protected boolean canCalculate(Double[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Double calculate(Double[] inputs);
}

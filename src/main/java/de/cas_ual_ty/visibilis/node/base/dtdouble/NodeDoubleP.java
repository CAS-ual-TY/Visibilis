package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeDoubleP extends NodeGenericP<Double>
{
    public NodeDoubleP(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    protected boolean canCalculate(Double input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected abstract Double calculate(Double input);
}

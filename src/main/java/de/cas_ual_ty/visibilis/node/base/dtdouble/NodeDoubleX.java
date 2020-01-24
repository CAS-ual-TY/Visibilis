package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeDoubleX extends NodeGenericX<Double>
{
    public NodeDoubleX(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    protected boolean canCalculate(Double[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Double calculate(Double[] inputs);
}

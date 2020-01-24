package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericCompare;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeDoubleCompare extends NodeGenericCompare<Double>
{
    public NodeDoubleCompare(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    protected abstract Boolean compare(Double input, Double in2);
}

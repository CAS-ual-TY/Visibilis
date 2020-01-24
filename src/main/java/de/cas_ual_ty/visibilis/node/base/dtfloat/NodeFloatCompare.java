package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericCompare;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatCompare extends NodeGenericCompare<Float>
{
    public NodeFloatCompare(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected abstract Boolean compare(Float input, Float in2);
}

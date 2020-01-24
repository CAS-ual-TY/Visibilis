package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatP extends NodeGenericP<Float>
{
    public NodeFloatP(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected boolean canCalculate(Float input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected abstract Float calculate(Float input);
}

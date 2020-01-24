package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatX extends NodeGenericX<Float>
{
    public NodeFloatX(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected boolean canCalculate(Float[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Float calculate(Float[] inputs);
}

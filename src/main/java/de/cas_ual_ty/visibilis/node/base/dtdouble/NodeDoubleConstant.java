package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeDoubleConstant extends NodeGenericConstant<Double>
{
    public NodeDoubleConstant(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    protected abstract Double getConstant();
}

package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeBooleanConstant extends NodeGenericConstant<Boolean>
{
    public NodeBooleanConstant(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return VDataTypes.BOOLEAN;
    }
    
    @Override
    protected abstract Boolean getConstant();
}

package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericV;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeBooleanV extends NodeGenericV<Boolean>
{
    public NodeBooleanV(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return VDataTypes.BOOLEAN;
    }
}

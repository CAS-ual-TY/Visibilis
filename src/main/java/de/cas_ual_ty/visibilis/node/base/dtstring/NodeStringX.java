package de.cas_ual_ty.visibilis.node.base.dtstring;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeStringX extends NodeGenericX<String>
{
    public NodeStringX(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<String> getDataType()
    {
        return VDataTypes.STRING;
    }
}

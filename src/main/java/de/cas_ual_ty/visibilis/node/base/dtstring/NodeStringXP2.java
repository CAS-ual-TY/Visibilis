package de.cas_ual_ty.visibilis.node.base.dtstring;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeStringXP2 extends NodeGenericXP2<String>
{
    public NodeStringXP2(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<String> getDataType()
    {
        return VDataTypes.STRING;
    }
}

package de.cas_ual_ty.visibilis.node.general.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeSingleXP;

public abstract class NodeBoolXP extends NodeSingleXP<Boolean>
{
    public NodeBoolXP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.BOOLEAN;
    }
}

package de.cas_ual_ty.visibilis.node.general.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeSingleX;

public abstract class NodeBoolX extends NodeSingleX<Boolean>
{
    public NodeBoolX()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.BOOLEAN;
    }
}

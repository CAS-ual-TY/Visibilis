package de.cas_ual_ty.visibilis.node.general.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeSingleP;

public abstract class NodeBoolP extends NodeSingleP<Boolean>
{
    public NodeBoolP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.BOOLEAN;
    }
}

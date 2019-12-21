package de.cas_ual_ty.visibilis.node.base.string;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeSingleX;

public abstract class NodeStringX extends NodeSingleX<String>
{
    public NodeStringX()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.STRING;
    }
}

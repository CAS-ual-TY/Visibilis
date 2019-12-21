package de.cas_ual_ty.visibilis.node.general.string;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeSingleX;

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

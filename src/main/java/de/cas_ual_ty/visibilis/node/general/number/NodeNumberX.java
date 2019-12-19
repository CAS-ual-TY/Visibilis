package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeSingleX;

public abstract class NodeNumberX extends NodeSingleX<Number>
{
    public NodeNumberX()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
}
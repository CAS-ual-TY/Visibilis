package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;

public abstract class NodeNumberP extends NodeSingleP<Number>
{
    public NodeNumberP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
}

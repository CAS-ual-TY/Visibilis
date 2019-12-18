package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;

public abstract class NodeNumberXP extends NodeSingleXP<Number>
{
    public NodeNumberXP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
}

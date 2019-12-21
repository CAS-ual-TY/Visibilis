package de.cas_ual_ty.visibilis.node.base.string;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeSingleXP;

public abstract class NodeStringXP extends NodeSingleXP<String>
{
    public NodeStringXP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.STRING;
    }
}

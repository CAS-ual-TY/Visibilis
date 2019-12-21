package de.cas_ual_ty.visibilis.node.general.string;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeSingleXP;

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

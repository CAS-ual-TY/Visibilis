package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericV;

public class NodeNumberV extends NodeGenericV<Number>
{
    public NodeNumberV()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    public String getID()
    {
        return "const_number";
    }
}

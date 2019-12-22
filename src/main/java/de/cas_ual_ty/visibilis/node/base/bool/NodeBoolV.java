package de.cas_ual_ty.visibilis.node.base.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericV;

public class NodeBoolV extends NodeGenericV<Boolean>
{
    public NodeBoolV()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    public String getID()
    {
        return "const_boolean";
    }
}

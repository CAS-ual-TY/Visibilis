package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericV;

public class NodeBooleanV extends NodeGenericV<Boolean>
{
    public NodeBooleanV()
    {
        super();
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    public String getID()
    {
        return "const_boolean";
    }
}

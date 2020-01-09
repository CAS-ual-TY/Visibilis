package de.cas_ual_ty.visibilis.node.base.dtstring;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;

public abstract class NodeStringX extends NodeGenericX<String>
{
    public NodeStringX()
    {
        super();
    }
    
    @Override
    public DataType<String> getDataType()
    {
        return DataType.STRING;
    }
}

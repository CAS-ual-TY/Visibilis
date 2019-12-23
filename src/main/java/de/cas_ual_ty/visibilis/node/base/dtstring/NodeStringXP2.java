package de.cas_ual_ty.visibilis.node.base.dtstring;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;

public abstract class NodeStringXP2 extends NodeGenericXP2<String>
{
    public NodeStringXP2()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.STRING;
    }
}
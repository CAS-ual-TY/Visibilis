package de.cas_ual_ty.visibilis.node.base.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericC;

public abstract class NodeBoolC extends NodeGenericC<Boolean>
{
    public NodeBoolC()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    protected abstract Boolean getConstant();
}

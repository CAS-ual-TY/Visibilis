package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericC;

public abstract class NodeNumberC extends NodeGenericC<Number>
{
    public NodeNumberC()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    protected abstract Number getConstant();
}

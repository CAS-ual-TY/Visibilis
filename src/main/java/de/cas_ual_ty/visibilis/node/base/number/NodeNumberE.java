package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericE;

public abstract class NodeNumberE extends NodeGenericE<Number>
{
    public NodeNumberE()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    protected abstract Boolean compare(Number input, Number in2);
}

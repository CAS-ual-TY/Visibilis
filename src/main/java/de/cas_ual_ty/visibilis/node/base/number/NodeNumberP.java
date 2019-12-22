package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericP;

public abstract class NodeNumberP extends NodeGenericP<Number>
{
    public NodeNumberP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    protected boolean canCalculate(Number input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected abstract Number calculate(Number input);
}

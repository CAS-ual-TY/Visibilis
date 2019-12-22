package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericX;

public abstract class NodeNumberX extends NodeGenericX<Number>
{
    public NodeNumberX()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    protected boolean canCalculate(Number[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Number calculate(Number[] inputs);
}

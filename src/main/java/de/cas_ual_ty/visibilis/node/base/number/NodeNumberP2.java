package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericP2;

public abstract class NodeNumberP2 extends NodeGenericP2<Number>
{
    public NodeNumberP2()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    protected boolean canCalculate(Number input, Number in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected abstract Number calculate(Number input, Number in2);
}

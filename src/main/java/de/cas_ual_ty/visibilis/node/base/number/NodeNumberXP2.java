package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericXP2;

public abstract class NodeNumberXP2 extends NodeGenericXP2<Number>
{
    public NodeNumberXP2()
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
    
    @Override
    protected boolean canCalculate(Number[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Number calculate(Number[] inputs);
}

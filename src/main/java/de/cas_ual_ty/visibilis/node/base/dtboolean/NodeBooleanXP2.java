package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;

public abstract class NodeBooleanXP2 extends NodeGenericXP2<Boolean>
{
    public NodeBooleanXP2()
    {
        super();
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    protected boolean canCalculate(Boolean input, Boolean in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected abstract Boolean calculate(Boolean input, Boolean in2);
    
    @Override
    protected boolean canCalculate(Boolean[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Boolean calculate(Boolean[] inputs);
}

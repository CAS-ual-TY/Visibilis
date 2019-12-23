package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP2;

public abstract class NodeBooleanP2 extends NodeGenericP2<Boolean>
{
    public NodeBooleanP2()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
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
}
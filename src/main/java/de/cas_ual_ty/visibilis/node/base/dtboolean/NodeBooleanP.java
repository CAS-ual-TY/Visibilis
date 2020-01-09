package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;

public abstract class NodeBooleanP extends NodeGenericP<Boolean>
{
    public NodeBooleanP()
    {
        super();
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    protected boolean canCalculate(Boolean input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected abstract Boolean calculate(Boolean input);
}

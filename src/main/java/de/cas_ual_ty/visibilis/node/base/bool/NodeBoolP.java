package de.cas_ual_ty.visibilis.node.base.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericP;

public abstract class NodeBoolP extends NodeGenericP<Boolean>
{
    public NodeBoolP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
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

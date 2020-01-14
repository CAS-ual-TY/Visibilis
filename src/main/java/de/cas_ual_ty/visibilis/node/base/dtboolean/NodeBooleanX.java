package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeBooleanX extends NodeGenericX<Boolean>
{
    public NodeBooleanX()
    {
        super();
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return VDataTypes.BOOLEAN;
    }
    
    @Override
    protected boolean canCalculate(Boolean[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Boolean calculate(Boolean[] inputs);
}

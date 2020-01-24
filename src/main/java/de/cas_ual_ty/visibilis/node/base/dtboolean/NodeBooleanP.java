package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeBooleanP extends NodeGenericP<Boolean>
{
    public NodeBooleanP(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return VDataTypes.BOOLEAN;
    }
    
    @Override
    protected boolean canCalculate(Boolean input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected abstract Boolean calculate(Boolean input);
}

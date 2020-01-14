package de.cas_ual_ty.visibilis.node.base.dtboolean;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericC;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeBooleanC extends NodeGenericC<Boolean>
{
    public NodeBooleanC()
    {
        super();
    }
    
    @Override
    public DataType<Boolean> getDataType()
    {
        return VDataTypes.BOOLEAN;
    }
    
    @Override
    protected abstract Boolean getConstant();
}

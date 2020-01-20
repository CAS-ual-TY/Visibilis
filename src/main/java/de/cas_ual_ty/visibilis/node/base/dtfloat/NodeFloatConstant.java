package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatConstant extends NodeGenericConstant<Float>
{
    public NodeFloatConstant()
    {
        super();
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected abstract Float getConstant();
}

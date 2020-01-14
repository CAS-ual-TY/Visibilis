package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericC;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatC extends NodeGenericC<Float>
{
    public NodeFloatC()
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

package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericE;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeFloatE extends NodeGenericE<Float>
{
    public NodeFloatE()
    {
        super();
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected abstract Boolean compare(Float input, Float in2);
}

package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericC;

public abstract class NodeFloatC extends NodeGenericC<Float>
{
    public NodeFloatC()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.FLOAT;
    }
    
    @Override
    protected abstract Float getConstant();
}

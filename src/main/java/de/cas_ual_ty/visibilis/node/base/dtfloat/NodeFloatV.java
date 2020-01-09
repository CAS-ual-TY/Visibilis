package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericV;

public class NodeFloatV extends NodeGenericV<Float>
{
    public NodeFloatV()
    {
        super();
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return DataType.FLOAT;
    }
    
    @Override
    public String getID()
    {
        return "const_float";
    }
}

package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericV;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeDoubleV extends NodeGenericV<Double>
{
    public NodeDoubleV()
    {
        super();
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    public String getID()
    {
        return "const_double";
    }
}

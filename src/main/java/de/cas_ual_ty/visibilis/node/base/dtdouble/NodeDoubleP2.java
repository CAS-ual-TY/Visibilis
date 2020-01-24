package de.cas_ual_ty.visibilis.node.base.dtdouble;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeDoubleP2 extends NodeGenericP2<Double>
{
    public NodeDoubleP2()
    {
        super();
    }
    
    @Override
    public DataType<Double> getDataType()
    {
        return VDataTypes.DOUBLE;
    }
    
    @Override
    protected boolean canCalculate(Double input, Double in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected abstract Double calculate(Double input, Double in2);
}

package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP2;

public abstract class NodeFloatP2 extends NodeGenericP2<Float>
{
    public NodeFloatP2()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.FLOAT;
    }
    
    @Override
    protected boolean canCalculate(Float input, Float in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected abstract Float calculate(Float input, Float in2);
}

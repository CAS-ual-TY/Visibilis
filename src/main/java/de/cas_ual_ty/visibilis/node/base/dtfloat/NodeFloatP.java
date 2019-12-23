package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.NodeGenericP;

public abstract class NodeFloatP extends NodeGenericP<Float>
{
    public NodeFloatP()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.FLOAT;
    }
    
    @Override
    protected boolean canCalculate(Float input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected abstract Float calculate(Float input);
}

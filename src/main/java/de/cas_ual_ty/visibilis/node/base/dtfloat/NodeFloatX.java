package de.cas_ual_ty.visibilis.node.base.dtfloat;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;

public abstract class NodeFloatX extends NodeGenericX<Float>
{
    public NodeFloatX()
    {
        super();
    }
    
    @Override
    public DataType<Float> getDataType()
    {
        return DataType.FLOAT;
    }
    
    @Override
    protected boolean canCalculate(Float[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected abstract Float calculate(Float[] inputs);
}

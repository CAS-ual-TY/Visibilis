package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP2;

public abstract class NodeGenericE<I> extends NodeBiGenericP2<Boolean, I>
{
    public NodeGenericE()
    {
        super();
    }
    
    public abstract DataType<I> getDataType();
    
    protected abstract Boolean compare(I input, I in2);
    
    @Override
    protected Boolean calculate(I input, I in2)
    {
        return this.compare(input, in2);
    }
    
    @Override
    public DataType<Boolean> getOutDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    public DataType<I> getInDataType()
    {
        return this.getDataType();
    }
}

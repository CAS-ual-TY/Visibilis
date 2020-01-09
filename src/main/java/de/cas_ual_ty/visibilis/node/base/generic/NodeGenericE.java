package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP2;

public abstract class NodeGenericE<A> extends NodeBiGenericP2<A, Boolean>
{
    public NodeGenericE()
    {
        super();
    }
    
    public abstract DataType<A> getDataType();
    
    protected abstract Boolean compare(A input, A in2);
    
    @Override
    protected Boolean calculate(A input, A in2)
    {
        return this.compare(input, in2);
    }
    
    @Override
    public DataType<Boolean> getOutDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    public DataType<A> getInDataType()
    {
        return this.getDataType();
    }
}

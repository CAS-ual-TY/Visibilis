package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic2.NodeGeneric2P2;

public abstract class NodeGenericE<A> extends NodeGeneric2P2<A, Boolean>
{
    public NodeGenericE()
    {
        super();
    }
    
    public abstract DataType getDataType();
    
    protected abstract Boolean compare(A input, A in2);
    
    @Override
    protected Boolean calculate(A input, A in2)
    {
        return this.compare(input, in2);
    }
    
    @Override
    public DataType getOutDataType()
    {
        return DataType.BOOLEAN;
    }
    
    @Override
    public DataType getInDataType()
    {
        return this.getDataType();
    }
}

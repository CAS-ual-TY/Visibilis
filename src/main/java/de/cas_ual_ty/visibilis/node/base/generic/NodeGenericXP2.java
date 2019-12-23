package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic2.NodeGeneric2XP2;

public abstract class NodeGenericXP2<A> extends NodeGeneric2XP2<A, A>
{
    public NodeGenericXP2()
    {
        super();
    }
    
    public abstract DataType getDataType();
    
    @Override
    public DataType getOutDataType()
    {
        return this.getDataType();
    }
    
    @Override
    public DataType getInDataType()
    {
        return this.getDataType();
    }
}

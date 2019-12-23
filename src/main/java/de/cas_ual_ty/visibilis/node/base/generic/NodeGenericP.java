package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.generic2.NodeGeneric2P;

public abstract class NodeGenericP<A> extends NodeGeneric2P<A, A>
{
    public NodeGenericP()
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

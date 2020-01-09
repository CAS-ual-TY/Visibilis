package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericXP2;

public abstract class NodeGenericXP2<A> extends NodeBiGenericXP2<A, A>
{
    public NodeGenericXP2()
    {
        super();
    }
    
    public abstract DataType<A> getDataType();
    
    @Override
    public DataType<A> getOutDataType()
    {
        return this.getDataType();
    }
    
    @Override
    public DataType<A> getInDataType()
    {
        return this.getDataType();
    }
}

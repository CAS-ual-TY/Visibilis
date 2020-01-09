package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;

public abstract class NodeGenericP<A> extends NodeBiGenericP<A, A>
{
    public NodeGenericP()
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

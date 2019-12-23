package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP2;

public abstract class NodeGenericP2<A> extends NodeBiGenericP2<A, A>
{
    public NodeGenericP2()
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

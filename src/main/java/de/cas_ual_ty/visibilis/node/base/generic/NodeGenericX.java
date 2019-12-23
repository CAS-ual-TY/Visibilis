package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericX;

public abstract class NodeGenericX<A> extends NodeBiGenericX<A, A>
{
    public NodeGenericX()
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

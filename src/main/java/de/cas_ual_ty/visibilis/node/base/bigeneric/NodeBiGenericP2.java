package de.cas_ual_ty.visibilis.node.base.bigeneric;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.base.trigeneric.NodeTriGenericP2;

public abstract class NodeBiGenericP2<O, I> extends NodeTriGenericP2<O, I, I>
{
    public NodeBiGenericP2()
    {
        super();
    }
    
    public abstract DataType<I> getInDataType();
    
    @Override
    public DataType<I> getIn1DataType()
    {
        return this.getInDataType();
    }
    
    @Override
    public DataType<I> getIn2DataType()
    {
        return this.getInDataType();
    }
}

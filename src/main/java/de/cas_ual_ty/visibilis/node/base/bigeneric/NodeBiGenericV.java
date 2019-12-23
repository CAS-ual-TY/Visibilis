package de.cas_ual_ty.visibilis.node.base.bigeneric;

public abstract class NodeBiGenericV<A, C> extends NodeBiGenericP<A, C>
{
    public NodeBiGenericV()
    {
        super();
    }
    
    @Override
    protected C calculate(A input)
    {
        return (C) input;
    }
}

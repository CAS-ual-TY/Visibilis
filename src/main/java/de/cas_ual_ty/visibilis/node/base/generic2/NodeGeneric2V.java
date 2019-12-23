package de.cas_ual_ty.visibilis.node.base.generic2;

public abstract class NodeGeneric2V<A, C> extends NodeGeneric2P<A, C>
{
    public NodeGeneric2V()
    {
        super();
    }
    
    @Override
    protected C calculate(A input)
    {
        return (C) input;
    }
}

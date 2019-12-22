package de.cas_ual_ty.visibilis.node.base;

public abstract class NodeGenericV<A> extends NodeGenericP<A>
{
    public NodeGenericV()
    {
        super();
    }
    
    @Override
    protected A calculate(A input)
    {
        return input;
    }
}

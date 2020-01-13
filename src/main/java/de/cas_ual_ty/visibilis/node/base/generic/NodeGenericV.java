package de.cas_ual_ty.visibilis.node.base.generic;

public abstract class NodeGenericV<B> extends NodeGenericP<B>
{
    public NodeGenericV()
    {
        super();
    }
    
    @Override
    protected B calculate(B input)
    {
        return input;
    }
}

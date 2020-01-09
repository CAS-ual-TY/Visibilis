package de.cas_ual_ty.visibilis.node.base.bigeneric;

public abstract class NodeBiGenericV<I, O> extends NodeBiGenericP<I, O>
{
    public NodeBiGenericV()
    {
        super();
    }
    
    @Override
    protected O calculate(I input)
    {
        return (O)input;
    }
}

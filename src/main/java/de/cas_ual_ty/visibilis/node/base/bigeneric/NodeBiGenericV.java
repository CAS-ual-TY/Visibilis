package de.cas_ual_ty.visibilis.node.base.bigeneric;

import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericV<O, I> extends NodeBiGenericP<O, I>
{
    public NodeBiGenericV()
    {
        super();
    }
    
    @Override
    protected O calculate(I input)
    {
        return VUtility.cast(input);
    }
}

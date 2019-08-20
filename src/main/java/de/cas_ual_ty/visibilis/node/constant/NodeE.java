package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeFloatConst;

public class NodeE extends NodeFloatConst
{
    public NodeE()
    {
        super();
    }
    
    @Override
    protected float getValue()
    {
        return (float) Math.E;
    }
    
    @Override
    public String getID()
    {
        return "e";
    }
}

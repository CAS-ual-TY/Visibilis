package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeNumber0to1;

public class NodeE extends NodeNumber0to1
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

package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeFloat0to1;

public class NodeE extends NodeFloat0to1
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

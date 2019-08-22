package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeFloat0to1;

public class NodePi extends NodeFloat0to1
{
    public NodePi()
    {
        super();
    }
    
    @Override
    protected float getValue()
    {
        return (float) Math.PI;
    }
    
    @Override
    public String getID()
    {
        return "pi";
    }
}

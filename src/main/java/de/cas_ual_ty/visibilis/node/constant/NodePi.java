package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeNumber0to1;

public class NodePi extends NodeNumber0to1
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

package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeFloatConst;

public class NodePi extends NodeFloatConst
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

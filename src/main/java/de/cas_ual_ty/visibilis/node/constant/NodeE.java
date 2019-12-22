package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberC;

public class NodeE extends NodeNumberC
{
    public NodeE()
    {
        super();
    }
    
    @Override
    protected Number getConstant()
    {
        return Math.E;
    }
    
    @Override
    public String getID()
    {
        return "e";
    }
}

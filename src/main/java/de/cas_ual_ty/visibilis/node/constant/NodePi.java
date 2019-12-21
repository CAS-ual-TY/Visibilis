package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberC;

public class NodePi extends NodeNumberC
{
    public NodePi()
    {
        super();
    }
    
    @Override
    protected Number getValue(int index)
    {
        return Math.PI;
    }
    
    @Override
    public String getID()
    {
        return "pi";
    }
}

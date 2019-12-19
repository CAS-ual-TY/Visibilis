package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberC;

public class NodeE extends NodeNumberC
{
    public NodeE()
    {
        super();
    }
    
    @Override
    protected Number getValue(int index)
    {
        return Math.E;
    }
    
    @Override
    public String getID()
    {
        return "e";
    }
}

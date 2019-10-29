package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.general.NodeBoolean1to1;

public class NodeNOT extends NodeBoolean1to1
{
    @Override
    protected boolean calculate(boolean in1)
    {
        return !in1;
    }
    
    @Override
    public String getID()
    {
        return "not";
    }
}

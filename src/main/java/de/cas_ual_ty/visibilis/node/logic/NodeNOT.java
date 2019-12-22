package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.base.bool.NodeBoolP;

public class NodeNOT extends NodeBoolP
{
    @Override
    protected Boolean calculate(Boolean in1)
    {
        return !in1;
    }
    
    @Override
    public String getID()
    {
        return "not";
    }
}

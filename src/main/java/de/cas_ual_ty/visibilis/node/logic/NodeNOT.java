package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanP;

public class NodeNOT extends NodeBooleanP
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

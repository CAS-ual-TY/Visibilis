package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.general.bool.NodeBoolP2;

public class NodeNOR extends NodeBoolP2
{
    public NodeNOR()
    {
        super();
    }
    
    @Override
    protected Boolean calculate(Boolean in1, Boolean in2)
    {
        return !(in1 || in2);
    }
    
    @Override
    public String getID()
    {
        return "nor";
    }
}

package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.bool.NodeBoolP1;

public class NodeBoolVar extends NodeBoolP1
{
    @Override
    protected Boolean calculate(Boolean in1)
    {
        return in1;
    }
    
    @Override
    public String getID()
    {
        return "const_number";
    }
}

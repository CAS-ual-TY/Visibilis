package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanXP2;

public class NodeAND extends NodeBooleanXP2
{
    public NodeAND()
    {
        super();
    }
    
    @Override
    protected Boolean calculate(Boolean in1, Boolean in2)
    {
        return in1 && in2;
    }
    
    @Override
    protected Boolean calculate(Boolean[] inputs)
    {
        for(Boolean input : inputs)
        {
            if(!input.booleanValue())
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String getID()
    {
        return "and";
    }
}

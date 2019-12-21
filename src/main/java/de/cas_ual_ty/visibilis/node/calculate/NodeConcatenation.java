package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.string.NodeStringXP2;

public class NodeConcatenation extends NodeStringXP2
{
    @Override
    protected String calculate(String[] inputs)
    {
        String value = "";
        
        for (String in : inputs)
        {
            value += in;
        }
        
        return value;
    }
    
    @Override
    protected String calculate(String in1, String in2)
    {
        return in1 + in2;
    }
    
    @Override
    public String getID()
    {
        return "concatenation";
    }
}

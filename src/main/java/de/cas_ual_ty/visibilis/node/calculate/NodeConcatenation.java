package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtstring.NodeStringXP2;

public class NodeConcatenation extends NodeStringXP2
{
    public NodeConcatenation(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected String calculate(String[] inputs)
    {
        String value = "";
        
        for(String in : inputs)
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

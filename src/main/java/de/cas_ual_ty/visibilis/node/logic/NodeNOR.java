package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanP2;

public class NodeNOR extends NodeBooleanP2
{
    public NodeNOR(NodeType<?> type)
    {
        super(type);
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

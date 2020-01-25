package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanP;

public class NodeNOT extends NodeBooleanP
{
    public NodeNOT(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Boolean calculate(Boolean in1)
    {
        return !in1;
    }
}

package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeFloat0to1;
import net.minecraft.util.math.MathHelper;

public class NodeSQRT2 extends NodeFloat0to1
{
    public NodeSQRT2()
    {
        super();
    }
    
    @Override
    protected float getValue()
    {
        return MathHelper.SQRT_2;
    }
    
    @Override
    public String getID()
    {
        return "sqrt_2";
    }
}

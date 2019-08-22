package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeFloatConst;
import net.minecraft.util.math.MathHelper;

public class NodeSQRT2 extends NodeFloatConst
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

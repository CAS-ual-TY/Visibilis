package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberC;
import net.minecraft.util.math.MathHelper;

public class NodeSQRT2 extends NodeNumberC
{
    public NodeSQRT2()
    {
        super();
    }
    
    @Override
    protected Number getValue(int index)
    {
        return MathHelper.SQRT_2;
    }
    
    @Override
    public String getID()
    {
        return "sqrt_2";
    }
}

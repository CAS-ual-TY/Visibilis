package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.FloatConst;

public class FloatConstPi extends FloatConst
{
    public FloatConstPi()
    {
        super();
    }
    
    @Override
    protected float getValue()
    {
        return (float) Math.PI;
    }
    
    @Override
    public String getID()
    {
        return "constant_pi";
    }
}

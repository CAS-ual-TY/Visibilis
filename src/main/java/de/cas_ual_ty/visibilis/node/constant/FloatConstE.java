package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.FloatConst;

public class FloatConstE extends FloatConst
{
    public FloatConstE()
    {
        super();
    }

    @Override
    protected float getValue()
    {
        return (float) Math.E;
    }

    @Override
    public String getID()
    {
        return "constant_e";
    }
}

package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.Float2to1;

public class FloatPlus extends Float2to1
{
    public FloatPlus()
    {
        super();
    }

    @Override
    protected float calculate(float in1, float in2)
    {
        return in1 + in2;
    }

    @Override
    public String getID()
    {
        return "calc_f_plus";
    }
}

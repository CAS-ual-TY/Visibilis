package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber2to1;

public class NodeAddition extends NodeNumber2to1
{
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return in1.floatValue() + in2.floatValue();
    }

    @Override
    public String getID()
    {
        return "addition";
    }
}

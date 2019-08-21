package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber2to1;

public class NodeModulo extends NodeNumber2to1
{
    @Override
    protected boolean canCalculate(Number in1, Number in2)
    {
        return in2.floatValue() != 0;
    }
    
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return in1.floatValue() % in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "modulo";
    }
}
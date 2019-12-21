package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberP2;

public class NodeModulo extends NodeNumberP2
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
        return "modulo"; //TODO low: Check lang file for translation of 1st input. Find out actual name of said input
    }
}

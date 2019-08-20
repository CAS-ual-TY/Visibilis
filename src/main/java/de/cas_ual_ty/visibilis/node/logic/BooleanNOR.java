package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.general.Boolean2to1;

public class BooleanNOR extends Boolean2to1
{
    public BooleanNOR()
    {
        super();
    }
    
    @Override
    protected boolean calculate(boolean in1, boolean in2)
    {
        return !in1 && !in2;
    }
    
    @Override
    public String getID()
    {
        return "logic_nor";
    }
}

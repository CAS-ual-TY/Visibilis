package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.Int2to1;

public class IntMinus extends Int2to1
{
	public IntMinus()
	{
		super();
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 - in2;
	}
	
	@Override
	public String getID()
	{
		return "calc_i_minus";
	}
}

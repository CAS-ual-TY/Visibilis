package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.Int1to1;

public class IntPower extends Int1to1
{
	public IntPower()
	{
		super();
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return (int) Math.pow(in1, in2);
	}
	
	@Override
	public String getID()
	{
		return "calc_i_pow";
	}
}

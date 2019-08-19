package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.Float0to1;

public class FloatConstPi extends Float0to1
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

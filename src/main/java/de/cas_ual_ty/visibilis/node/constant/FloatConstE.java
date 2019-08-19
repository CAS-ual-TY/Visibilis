package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.Float0to1;

public class FloatConstE extends Float0to1
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

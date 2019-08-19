package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NtoF;

public class E extends NtoF
{
	public E()
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

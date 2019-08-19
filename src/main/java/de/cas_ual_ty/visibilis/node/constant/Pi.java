package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NtoF;

public class Pi extends NtoF
{
	public Pi(int assignedID)
	{
		super(assignedID);
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

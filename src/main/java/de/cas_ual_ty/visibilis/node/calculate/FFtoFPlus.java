package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.FFtoF;

public class FFtoFPlus extends FFtoF
{
	public FFtoFPlus(int assignedID)
	{
		super(assignedID);
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return in1 + in2;
	}
	
	@Override
	public String getID()
	{
		return "calc_f_plus";
	}
}

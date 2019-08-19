package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.FFtoF;

public class FFtoFRoot extends FFtoF
{
	public FFtoFRoot(int assignedID)
	{
		super(assignedID);
	}
	
	@Override
	protected boolean canCalculate(float in1, float in2)
	{
		return in2 != 0F;
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return (float) Math.pow(Math.E, Math.log(in1) / in2);
	}
	
	@Override
	public String getID()
	{
		return "calc_f_root";
	}
}

package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.FFtoF;

public class FFtoFModulus extends FFtoF
{
	public FFtoFModulus(int assignedID)
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
		return in1 % in2;
	}
	
	@Override
	public String getID()
	{
		return "calc_f_modulus";
	}
}

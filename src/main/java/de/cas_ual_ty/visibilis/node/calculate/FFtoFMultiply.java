package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.FFtoF;

public class FFtoFMultiply extends FFtoF
{
	public FFtoFMultiply()
	{
		super();
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return in1 * in2;
	}
	
	@Override
	public String getID()
	{
		return "calc_f_multiply";
	}
}

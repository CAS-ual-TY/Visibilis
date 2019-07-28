package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.FFtoF;

public class FFtoFRoot extends FFtoF
{
	public FFtoFRoot(int posX, int posY)
	{
		super(posX, posY);
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
}

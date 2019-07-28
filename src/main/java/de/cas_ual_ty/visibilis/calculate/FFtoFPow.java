package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.FFtoF;

public class FFtoFPow extends FFtoF
{
	public FFtoFPow(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected boolean canCalculate(float in1, float in2)
	{
		//If in1 < 0 and in2 is not rounded, it would mean that you are trying to root a negative value.
		return in1 >= 0 || in2 != (int) in2;
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return (float) Math.pow(in1, in2);
	}
}

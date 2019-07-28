package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.IItoI;

public class IItoIModulus extends IItoI
{
	public IItoIModulus(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected boolean canCalculate(int in1, int in2)
	{
		return in2 != 0F;
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 % in2;
	}
}

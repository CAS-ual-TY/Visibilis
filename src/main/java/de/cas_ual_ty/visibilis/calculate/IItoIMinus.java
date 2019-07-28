package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.IItoI;

public class IItoIMinus extends IItoI
{
	public IItoIMinus(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 - in2;
	}
}

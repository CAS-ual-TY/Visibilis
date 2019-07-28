package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.IItoI;

public class IItoIPow extends IItoI
{
	public IItoIPow(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return (int) Math.pow(in1, in2);
	}
}

package de.cas_ual_ty.visibilis.constant;

public class Pi extends Node0toF
{
	public Pi(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float getValue()
	{
		return (float) Math.PI;
	}
}

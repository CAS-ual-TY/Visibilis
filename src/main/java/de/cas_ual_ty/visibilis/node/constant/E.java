package de.cas_ual_ty.visibilis.node.constant;

public class E extends Node0toF
{
	public E(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float getValue()
	{
		return (float) Math.E;
	}

	@Override
	public String getID()
	{
		return "constant_e";
	}
}

package de.cas_ual_ty.visibilis.node.constant;

public class E extends Node0toF
{
	public E()
	{
		super();
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

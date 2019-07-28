package de.cas_ual_ty.visibilis.general;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.Input;
import de.cas_ual_ty.visibilis.Node;
import de.cas_ual_ty.visibilis.Output;

public abstract class IItoI extends Node
{
	public final Input<Integer> in1;
	public final Input<Integer> in2;
	public final Output<Integer> out;
	
	public int value;
	
	public IItoI(int posX, int posY)
	{
		super(posX, posY, 1, 2);
		this.in1 = new Input<Integer>(0, this, EnumVDataType.INTEGER.dataTypeString, "integer");
		this.in2 = new Input<Integer>(0, this, EnumVDataType.INTEGER.dataTypeString, "integer");
		this.out = new Output<Integer>(0, this, EnumVDataType.INTEGER.dataTypeString, "integer");
	}
	
	@Override
	public boolean doCalculate()
	{
		if(!this.canCalculate(this.in1.getValue(), this.in2.getValue()))
		{
			return false;
		}
		
		this.calculate(this.in1.getValue(), this.in2.getValue());
		
		return true;
	}
	
	/**
	 * Can this node calculate or are there going to be errors (example: 1 / 0)?
	 * @param in1 The first input
	 * @param in2 The 2nd input
	 * @return <b>true</b> if this node can calculate.
	 */
	protected boolean canCalculate(int in1, int in2)
	{
		return true;
	}
	
	/**
	 * Calculate the result using the 2 input numbers.
	 * @param in1 The first input
	 * @param in2 The 2nd input
	 * @return The result.
	 */
	protected abstract int calculate(int in1, int in2);
	
	@Override
	public <B> B getOutputValue(int index)
	{
		switch(index)
		{
			case 0: return (B) (Integer) this.value;
		}
		
		return null;
	}
}

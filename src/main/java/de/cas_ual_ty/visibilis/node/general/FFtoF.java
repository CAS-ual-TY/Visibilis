package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class FFtoF extends Node
{
	public final Input<Float> in1;
	public final Input<Float> in2;
	public final Output<Float> out;
	
	public float value;
	
	public FFtoF()
	{
		super(1, 2);
		this.in1 = new Input<Float>(0, this, EnumVDataType.FLOAT.dataTypeString, "float");
		this.in2 = new Input<Float>(1, this, EnumVDataType.FLOAT.dataTypeString, "float");
		this.out = new Output<Float>(0, this, EnumVDataType.FLOAT.dataTypeString, "float");
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
	protected boolean canCalculate(float in1, float in2)
	{
		return true;
	}
	
	/**
	 * Calculate the result using the 2 input numbers.
	 * @param in1 The first input
	 * @param in2 The 2nd input
	 * @return The result.
	 */
	protected abstract float calculate(float in1, float in2);
	
	@Override
	public <B> B getOutputValue(int index)
	{
		if(index == this.out.id)
		{
			return (B) (Float) this.value;
		}
		
		return null;
	}
}

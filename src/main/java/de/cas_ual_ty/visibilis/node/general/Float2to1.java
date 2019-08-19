package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class Float2to1 extends Node
{
	public final Output<Float> out1;
	public final Input<Float> in1;
	public final Input<Float> in2;
	
	public float value;
	
	public Float2to1(int outputAmt, int inputAmt)
	{
		super(outputAmt, inputAmt);
		this.out1 = new Output<Float>(0, this, EnumVDataType.FLOAT.dataTypeString, "float");
		this.in1 = new Input<Float>(0, this, EnumVDataType.FLOAT.dataTypeString, "float");
		this.in2 = new Input<Float>(1, this, EnumVDataType.FLOAT.dataTypeString, "float");
	}
	
	public Float2to1()
	{
		this(1, 2);
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
		if(index == this.out1.id)
		{
			return (B) (Float) this.value;
		}
		
		return null;
	}
}

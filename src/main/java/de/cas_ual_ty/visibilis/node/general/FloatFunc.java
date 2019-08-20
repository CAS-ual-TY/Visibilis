package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.VDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class FloatFunc extends Node
{
    public final Output<Float> out1;
    public final Input<Float> in1;

    public float value;

    public FloatFunc(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Float>(0, this, VDataType.FLOAT, "float");
        this.in1 = new Input<Float>(0, this, VDataType.FLOAT, "float");
    }

    public FloatFunc()
    {
        this(1, 2);
    }

    @Override
    public boolean doCalculate()
    {
        if (!this.canCalculate(this.in1.getValue()))
        {
            return false;
        }

        this.value = this.calculate(this.in1.getValue());

        return true;
    }

    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
     * 
     * @param in1
     *            The first input
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(float in1)
    {
        return true;
    }

    /**
     * Calculate the result using the input number.
     * 
     * @param in1
     *            The first input
     * @return The result.
     */
    protected abstract float calculate(float in1);

    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.id)
        {
            return (B) (Float) this.value;
        }

        return null;
    }
}

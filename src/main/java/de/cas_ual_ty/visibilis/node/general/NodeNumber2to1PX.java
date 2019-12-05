package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber2to1PX extends NodeParallelizable
{
    public final Output<Number> out1;
    
    public Number value;
    public Number[] values;
    
    public NodeNumber2to1PX()
    {
        super();
        this.addOutput(this.out1 = new Output<>(this, DataType.NUMBER, "out1"));
        this.addInput(new Input<Number>(this, DataType.NUMBER, "in1"));
        this.addInput(new Input<Number>(this, DataType.NUMBER, "in2"));
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        Number[] inputs = new Number[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = (Number) this.getInput(i).getValue();
        }
        
        if (!this.parallelized)
        {
            if (!this.canCalculate(inputs))
            {
                return false;
            }
            
            this.value = this.calculate(inputs);
        }
        else
        {
            Number n = inputs[this.getOutputAmt()];
            this.values = new Number[this.getOutputAmt()];
            
            for (int i = 0; i < this.getOutputAmt(); ++i)
            {
                if (!this.canCalculate(inputs[i], n))
                {
                    return false;
                }
                else
                {
                    this.values[i] = this.calculate(inputs[i], n);
                }
            }
        }
        
        return true;
    }
    
    /**
     * Can this node calculate in unparallelized status or are there going to be errors (example: 1 / 0)?
     * 
     * @param inputs
     *            All inputs
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(Number[] inputs)
    {
        return true;
    }
    
    /**
     * Can this node calculate in parallelized status or are there going to be errors (example: 1 / 0)?
     * 
     * @param in1 The parallel number.
     * @param in2 The number to calculate with.
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(Number in1, Number in2)
    {
        return true;
    }
    
    /**
     * Calculate the result using the input numbers when in unparallelized status.
     * 
     * @param inputs
     *            All inputs
     * @return The result.
     */
    protected abstract Number calculate(Number[] inputs);
    
    /**
     * Calculate the result using the input numbers when in parallelized status.
     * 
     * @param in1 The parallel number.
     * @param in2 The number to calculate with.
     * @return The result.
     */
    protected abstract Number calculate(Number in1, Number in2);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (this.parallelized)
        {
            if (index == this.out1.getId())
            {
                return (B) this.value;
            }
        }
        else
        {
            if (index >= 0 && index < this.values.length)
            {
                return (B) this.values[index];
            }
        }
        
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return DataType.NUMBER.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return DataType.NUMBER.getTextColor();
    }
    
    @Override
    public void expand()
    {
        if (this.parallelized)
        {
            Input in = this.inputFields.remove(this.inputFields.size() - 1);
            
            this.addInput(new Input<Number>(this, DataType.NUMBER, "in1"));
            this.addOutput(new Output<Number>(this, DataType.NUMBER, "out1"));
            
            this.inputFields.add(in);
            in.recalculateId();
        }
        else
        {
            this.addInput(new Input<Number>(this, DataType.NUMBER, "in1"));
        }
    }
    
    @Override
    public void shrink()
    {
        if (this.parallelized)
        {
            this.removeInput(this.getInput(this.getInputAmt() - 2));
            this.removeOutput(this.getOutput(this.getOutputAmt() - 1));
            this.getInput(this.getInputAmt() - 1).recalculateId();
        }
        else
        {
            this.removeInput(this.getInput(this.getInputAmt() - 1));
        }
    }
    
    @Override
    public void parallelize()
    {
        for (int i = this.getOutputAmt(); i < this.getInputAmt() - 1; ++i)
        {
            this.addOutput(new Output<Number>(this, DataType.NUMBER, "out1"));
        }
    }
    
    @Override
    public void unparallelize()
    {
        for (int i = this.getOutputAmt() - 1; i > 0; --i)
        {
            this.removeOutput(this.getOutput(i));
        }
    }
}

package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.NodeParallelizable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber2Xto1 extends NodeParallelizable
{
    public final Output<Number> out1;
    
    public int expansion;
    public Number value;
    public Number[] values;
    
    public NodeNumber2Xto1()
    {
        super();
        this.out1 = new Output<>(this, DataType.NUMBER, "out1");
        new Input<Number>(this, DataType.NUMBER, "in1");
        new Input<Number>(this, DataType.NUMBER, "in1");
        
        this.expansion = 0;
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
        if (index == this.out1.getId())
        {
            return (B) this.value;
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
        new Input<Number>(this, DataType.NUMBER, "in1");
        
        if (this.parallelized)
        {
            new Output<Number>(this, DataType.NUMBER, "out1");
            //TODO keep last in as last
        }
    }
    
    @Override
    public void shrink()
    {
        this.removeInput((Input) this.getInput(this.getInputAmt() - 1));
        
        if (this.parallelized)
        {
            this.removeOutput(this.getOutput(this.getOutputAmt() - 2));
        }
    }
    
    @Override
    public void parallelize()
    {
        for (int i = this.getOutputAmt(); i < this.getInputAmt() - 1; ++i)
        {
            new Output<Number>(this, DataType.NUMBER, "out");
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

package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber2to1PX extends NodeParallelizableNumber
{
    public NodeNumber2to1PX()
    {
        super();
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
            Number n = inputs[this.getInputAmt() - 1];
            this.values = new Number[this.getInputAmt() - 1];
            
            for (int i = 0; i < this.getInputAmt() - 1; ++i)
            {
                if (!this.canCalculate(inputs[i], n))
                {
                    return false;
                }
                
                this.values[i] = this.calculate(inputs[i], n);
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
            this.addOutput(this.createDynamicOutput());
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

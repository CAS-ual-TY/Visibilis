package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;

public abstract class NodeNumberXP2 extends NodeNumberXP
{
    public NodeNumberXP2()
    {
        super();
        this.addInput(new Input<Number>(this, this.getDataType(), "in2"));
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
    public int getExtraInAmt()
    {
        return 1;
    }
}

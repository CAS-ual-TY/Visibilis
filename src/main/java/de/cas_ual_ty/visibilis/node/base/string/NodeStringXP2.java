package de.cas_ual_ty.visibilis.node.base.string;

import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;

public abstract class NodeStringXP2 extends NodeStringXP
{
    public NodeStringXP2()
    {
        super();
        this.addInput(new Input<String>(this, this.getDataType(), "in2"));
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        String[] inputs = new String[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = this.getInput(i).getValue().toString();
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
            String n = inputs[this.getInputAmt() - 1];
            this.values = new String[this.getInputAmt() - 1];
            
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
    protected boolean canCalculate(String[] inputs)
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
    protected boolean canCalculate(String in1, String in2)
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
    protected abstract String calculate(String[] inputs);
    
    /**
     * Calculate the result using the input numbers when in parallelized status.
     * 
     * @param in1 The parallel number.
     * @param in2 The number to calculate with.
     * @return The result.
     */
    protected abstract String calculate(String in1, String in2);
    
    @Override
    public int getExtraInAmt()
    {
        return 1;
    }
}

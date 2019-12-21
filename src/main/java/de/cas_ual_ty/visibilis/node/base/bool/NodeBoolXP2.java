package de.cas_ual_ty.visibilis.node.base.bool;

import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;

public abstract class NodeBoolXP2 extends NodeBoolXP
{
    public NodeBoolXP2()
    {
        super();
        this.addInput(new Input<Number>(this, this.getDataType(), "in2"));
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        Boolean[] inputs = new Boolean[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = (Boolean) this.getInput(i).getValue();
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
            Boolean n = inputs[this.getInputAmt() - 1];
            this.values = new Boolean[this.getInputAmt() - 1];
            
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
    protected boolean canCalculate(Boolean[] inputs)
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
    protected boolean canCalculate(Boolean in1, Boolean in2)
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
    protected abstract Boolean calculate(Boolean[] inputs);
    
    /**
     * Calculate the result using the input numbers when in parallelized status.
     * 
     * @param in1 The parallel number.
     * @param in2 The number to calculate with.
     * @return The result.
     */
    protected abstract Boolean calculate(Boolean in1, Boolean in2);
    
    @Override
    public int getExtraInAmt()
    {
        return 1;
    }
}

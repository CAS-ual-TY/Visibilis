package de.cas_ual_ty.visibilis.node.general.bool;

import de.cas_ual_ty.visibilis.node.ExecProvider;

public abstract class NodeBoolP1 extends NodeBoolP
{
    public NodeBoolP1()
    {
        super();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        Boolean[] inputs = new Boolean[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = (Boolean) this.getInput(i).getValue();
        }
        
        this.values = new Boolean[this.getOutputAmt()];
        
        for (int i = 0; i < this.getOutputAmt(); ++i)
        {
            if (!this.canCalculate(inputs[i]))
            {
                return false;
            }
            else
            {
                this.values[i] = this.calculate(inputs[i]);
            }
        }
        
        return true;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
     * 
     * @param in1 The parallel number.
     * @return <b>true</b> if this node can calculate.
     */
    protected Boolean canCalculate(Boolean in1)
    {
        return true;
    }
    
    /**
     * Calculate the result using the input numbers.
     * 
     * @param in1 The parallel number.
     * @return The result.
     */
    protected abstract Boolean calculate(Boolean in1);
}

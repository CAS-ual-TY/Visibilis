package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.node.ExecProvider;

public abstract class NodeNumberP1 extends NodeNumberP
{
    public NodeNumberP1()
    {
        super();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        Number[] inputs = new Number[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = (Number) this.getInput(i).getValue();
        }
        
        this.values = new Number[this.getOutputAmt()];
        
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
    protected boolean canCalculate(Number in1)
    {
        return true;
    }
    
    /**
     * Calculate the result using the input numbers.
     * 
     * @param in1 The parallel number.
     * @return The result.
     */
    protected abstract Number calculate(Number in1);
}

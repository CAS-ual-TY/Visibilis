package de.cas_ual_ty.visibilis.node.base.string;

import de.cas_ual_ty.visibilis.node.ExecProvider;

public abstract class NodeStringX1 extends NodeStringX
{
    public NodeStringX1()
    {
        super();
        this.expand();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        String[] inputs = new String[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = (String) this.getInput(i).getValue();
        }
        
        if (!this.canCalculate(inputs))
        {
            return false;
        }
        
        this.value = this.calculate(inputs);
        
        return true;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
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
     * Calculate the result using the input numbers.
     * 
     * @param inputs
     *            All inputs
     * @return The result.
     */
    protected abstract String calculate(String[] inputs);
    
    @Override
    public int getExtraInAmt()
    {
        return 1;
    }
}

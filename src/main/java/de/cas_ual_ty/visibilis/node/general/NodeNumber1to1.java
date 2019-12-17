package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber1to1 extends NodeParallel
{
    public Number[] values;
    
    public NodeNumber1to1()
    {
        super();
    }
    
    @Override
    public Output createDynamicOutput()
    {
        return new Output<Number>(this, DataType.NUMBER, "out1");
    }
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<Number>(this, DataType.NUMBER, "in1");
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        Number in;
        
        for (int i = 0; i < this.expansion; ++i)
        {
            in = (Number) this.getInput(i).getValue();
            
            if (!this.canCalculate(in))
            {
                return false;
            }
            else
            {
                this.values[i] = this.calculate(in);
            }
        }
        
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index >= 0 && index < this.expansion)
        {
            return (B) this.values[index];
        }
        
        return null;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
     * 
     * @param in1
     *            The first input
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(Number in1)
    {
        return true;
    }
    
    /**
     * Calculate the result using the 2 input numbers.
     * 
     * @param in1
     *            The first input
     * @param in2
     *            The 2nd input
     * @return The result.
     */
    protected abstract Number calculate(Number in1);
}

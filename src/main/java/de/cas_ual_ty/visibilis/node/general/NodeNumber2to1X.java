package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber2to1X extends NodeExpandable
{
    public final Output<Number> out1;
    
    public Number value;
    
    public NodeNumber2to1X()
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
    protected boolean canCalculate(Number[] inputs)
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
    protected abstract Number calculate(Number[] inputs);
    
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
        this.addInput(new Input<Number>(this, DataType.NUMBER, "in1"));
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.getInput(this.getInputAmt() - 1));
    }
}

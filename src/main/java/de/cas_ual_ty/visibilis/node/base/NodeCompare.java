package de.cas_ual_ty.visibilis.node.base;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeCompare<A> extends NodeP
{
    public Boolean[] values;
    
    public NodeCompare()
    {
        super();
        this.addOutput(this.createDynamicOutput());
        this.addInput(this.createDynamicInput());
        this.addInput(new Input<A>(this, this.getDataType(), "in2"));
    }
    
    public abstract DataType getDataType();
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        A in2 = (A) this.getInput(this.getInputAmt() - 1).getValue();
        
        this.values = new Boolean[this.getOutputAmt()];
        
        A value;
        
        for (int i = 0; i < this.getInputAmt() - 1; ++i)
        {
            value = (A) this.getInput(i).getValue();
            
            if (!this.canCalculate(value))
            {
                return false;
            }
            else
            {
                this.values[i] = this.calculate(value, in2);
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
    protected boolean canCalculate(A in1)
    {
        return true;
    }
    
    /**
     * Calculate the result using the input numbers.
     * 
     * @param in1 The parallel number.
     * @return The result.
     */
    protected abstract Boolean calculate(A in1, A in2);
    
    @Override
    public Output createDynamicOutput()
    {
        return new Output<Boolean>(this, DataType.BOOLEAN, "out1");
    }
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getDataType(), "in1");
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index >= 0 && index < this.values.length)
        {
            return (B) this.values[index];
        }
        
        return null;
    }
    
    @Override
    public int getExtraInAmt()
    {
        return 1;
    }
    
    @Override
    public float[] getColor()
    {
        return this.getDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getDataType().getTextColor();
    }
}

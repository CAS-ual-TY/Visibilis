package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber1to1P extends NodeExpandable
{
    public final Output<Number> out1;
    
    public Number value;
    public Number[] values;
    
    public NodeNumber1to1P()
    {
        super();
        this.addOutput(this.out1 = new Output<>(this, DataType.NUMBER, "out1"));
        this.addInput(new Input<Number>(this, DataType.NUMBER, "in1"));
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
     * Calculate the result using the input numbers when in parallelized status.
     * 
     * @param in1 The parallel number.
     * @return The result.
     */
    protected abstract Number calculate(Number in1);
    
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
        this.addOutput(new Output<Number>(this, DataType.NUMBER, "out1"));
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.getInput(this.getInputAmt() - 1));
        this.removeOutput(this.getOutput(this.getOutputAmt() - 1));
    }
    
    @Override
    public String getFieldName(NodeField field)
    {
        return super.getFieldName(field) + " " + (field.getId() + 1);
    }
}

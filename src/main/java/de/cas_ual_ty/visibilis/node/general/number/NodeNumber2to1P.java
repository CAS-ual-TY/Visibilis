package de.cas_ual_ty.visibilis.node.general.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeNumber2to1P extends NodeParallelNumber
{
    public NodeNumber2to1P()
    {
        super();
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
        
        return true;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
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
     * Calculate the result using the input numbers.
     * 
     * @param in1 The parallel number.
     * @param in2 The number to calculate with.
     * @return The result.
     */
    protected abstract Number calculate(Number in1, Number in2);
    
    @Override
    public void expand()
    {
        Input in = this.inputFields.remove(this.inputFields.size() - 1);
        
        this.addInput(new Input<Number>(this, DataType.NUMBER, "in1"));
        this.addOutput(new Output<Number>(this, DataType.NUMBER, "out1"));
        
        this.inputFields.add(in);
        in.recalculateId();
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.getInput(this.getInputAmt() - 2));
        this.removeOutput(this.getOutput(this.getOutputAmt() - 1));
        this.getInput(this.getInputAmt() - 1).recalculateId();
    }
}

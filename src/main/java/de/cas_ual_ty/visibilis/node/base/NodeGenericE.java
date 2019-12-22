package de.cas_ual_ty.visibilis.node.base;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeGenericE<A> extends NodeExpandable
{
    public LinkedList<Output<Boolean>> expansionOutputs;
    public LinkedList<Input<A>> expansionInputs;
    
    public Input<A> in2;
    
    public Boolean[] values;
    
    public NodeGenericE()
    {
        super();
        this.expansionOutputs = new LinkedList<Output<Boolean>>();
        this.expansionInputs = new LinkedList<Input<A>>();
        this.createBaseFields();
    }
    
    public void createBaseFields()
    {
        this.addInput(this.in2 = new Input<A>(this, this.getDataType(), "in2"));
        this.expand();
    }
    
    public abstract DataType getDataType();
    
    public void addDynamicOutput(Output out)
    {
        this.addOutput(out, this.getOutputAmt());
        this.expansionOutputs.addLast(out);
    }
    
    public Output createDynamicOutput()
    {
        return new Output<Boolean>(this, DataType.BOOLEAN, "out1");
    }
    
    public void addDynamicInput(Input in)
    {
        this.addInput(in, this.getInputAmt() - 1);
        this.expansionInputs.addLast(in);
    }
    
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getDataType(), "in1");
    }
    
    @Override
    public void expand()
    {
        this.addDynamicOutput(this.createDynamicOutput());
        this.addDynamicInput(this.createDynamicInput());
    }
    
    @Override
    public void shrink()
    {
        this.removeOutput(this.expansionOutputs.removeLast().getId());
        this.removeInput(this.expansionInputs.removeLast().getId());
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        A[] inputs = (A[]) new Object[this.expansionInputs.size()];
        A in2 = this.in2.getValue();
        
        int i = 0;
        for (Input<A> input : this.expansionInputs)
        {
            inputs[i++] = input.getValue();
        }
        
        for (A a : inputs)
        {
            if (!this.canCalculate(a, in2))
            {
                return false;
            }
        }
        
        this.values = new Boolean[this.expansionOutputs.size()];
        
        i = 0;
        for (A a : inputs)
        {
            this.values[i++] = this.compare(a, in2);
        }
        
        return true;
    }
    
    protected boolean canCalculate(A input, A in2)
    {
        return true;
    }
    
    protected abstract Boolean compare(A input, A in2);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        int i = 0;
        for (Output<Boolean> output : this.expansionOutputs)
        {
            if (output.getId() == index)
            {
                return (B) this.values[i];
            }
            
            ++i;
        }
        
        return null;
    }
    
    /*@Override
    public String getFieldName(NodeField field)
    {
        return super.getFieldName(field) + (field.isOutput() || (field.isInput() && field.getId() < (this.getInputAmt() - this.getExtraInAmt())) ? " " + (field.getId() + 1) : "");
    }*/
    
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

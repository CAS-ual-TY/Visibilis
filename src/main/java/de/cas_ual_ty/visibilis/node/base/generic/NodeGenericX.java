package de.cas_ual_ty.visibilis.node.base.generic;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.base.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeGenericX<A> extends NodeExpandable
{
    public LinkedList<Input<A>> expansionInputs;
    
    public Output<A> out1;
    
    public A value;
    
    protected int inAmt;
    
    public NodeGenericX()
    {
        super();
        this.expansionInputs = new LinkedList<Input<A>>();
        this.createBaseFields();
    }
    
    public void countBaseFields()
    {
        this.inAmt = this.getInputAmt();
    }
    
    public void createBaseFields()
    {
        this.addOutput(this.out1 = new Output<A>(this, this.getDataType(), "out1"));
        
        this.countBaseFields();
        
        this.expand();
        this.expand();
    }
    
    public abstract DataType getDataType();
    
    public void addDynamicInput(Input in)
    {
        this.addInput(in, this.getInputAmt() - this.inAmt);
    }
    
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getDataType(), "in1");
    }
    
    @Override
    public void expand()
    {
        Input<A> input = this.createDynamicInput();
        this.addDynamicInput(input);
        this.expansionInputs.addLast(input);
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.expansionInputs.removeLast().getId());
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        A[] inputs = (A[]) new Object[this.expansionInputs.size()];
        
        int i = 0;
        for (Input<A> input : this.expansionInputs)
        {
            inputs[i++] = input.getValue();
        }
        
        if (!this.canCalculate(inputs))
        {
            return false;
        }
        
        this.value = this.calculate(inputs);
        
        return true;
    }
    
    protected boolean canCalculate(A[] inputs)
    {
        return true;
    }
    
    protected abstract A calculate(A[] inputs);
    
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
        return this.getDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getDataType().getTextColor();
    }
}

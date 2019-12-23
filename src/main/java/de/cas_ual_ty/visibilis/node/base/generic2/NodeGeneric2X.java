package de.cas_ual_ty.visibilis.node.base.generic2;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.base.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeGeneric2X<A, C> extends NodeExpandable
{
    public LinkedList<Input<A>> expansionInputs;
    
    public Output<C> out1;
    
    public C value;
    
    protected int inAmt;
    
    public NodeGeneric2X()
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
        this.addOutput(this.out1 = new Output<C>(this, this.getOutDataType(), "out1"));
        
        this.countBaseFields();
        
        this.expand();
        this.expand();
    }
    
    public abstract DataType getOutDataType();
    
    public abstract DataType getInDataType();
    
    public void addDynamicInput(Input in)
    {
        this.addInput(in, this.getInputAmt() - this.inAmt);
    }
    
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getInDataType(), "in1");
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
    
    protected abstract C calculate(A[] inputs);
    
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
        return this.getOutDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getOutDataType().getTextColor();
    }
}

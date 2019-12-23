package de.cas_ual_ty.visibilis.node.base.generic2;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.base.NodeParallelizable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeGeneric2XP2<A, C> extends NodeParallelizable
{
    public LinkedList<Output<C>> expansionOutputs;
    public LinkedList<Input<A>> expansionInputs;
    
    public Input<A> in2;
    
    public C value;
    public C[] values;
    
    protected int inAmt;
    protected int outAmt;
    
    public NodeGeneric2XP2()
    {
        super();
        this.expansionOutputs = new LinkedList<Output<C>>();
        this.expansionInputs = new LinkedList<Input<A>>();
        this.createBaseFields();
    }
    
    public void countBaseFields()
    {
        this.inAmt = this.getInputAmt();
        this.outAmt = this.getOutputAmt();
    }
    
    public void createBaseFields()
    {
        this.addInput(this.in2 = this.createDynamicInput());
        this.expansionInputs.add(this.in2);
        
        this.countBaseFields();
        
        this.addDynamicOutput(this.createDynamicOutput());
        this.expand();
    }
    
    public abstract DataType getOutDataType();
    
    public abstract DataType getInDataType();
    
    public void addDynamicOutput(Output out)
    {
        this.addOutput(out, this.getOutputAmt() - this.outAmt);
        this.expansionOutputs.addLast(out);
    }
    
    public Output createDynamicOutput()
    {
        return new Output<C>(this, this.getOutDataType(), "out1");
    }
    
    public void addDynamicInput(Input in)
    {
        this.addInput(in, this.getInputAmt() - this.inAmt);
        
        if (this.parallelized)
        {
            this.expansionInputs.addLast(in);
        }
        else
        {
            this.expansionInputs.add(this.getInputAmt() - 1, in);
        }
    }
    
    public Input createDynamicInput()
    {
        return new Input<A>(this, this.getInDataType(), "in1");
    }
    
    @Override
    public void expand()
    {
        if (this.parallelized)
        {
            this.addDynamicOutput(this.createDynamicOutput());
        }
        
        this.addDynamicInput(this.createDynamicInput());
    }
    
    @Override
    public void shrink()
    {
        if (this.parallelized)
        {
            this.removeOutput(this.expansionOutputs.removeLast().getId());
        }
        
        this.removeInput(this.expansionInputs.removeLast().getId());
    }
    
    @Override
    public void parallelize()
    {
        Visibilis.debug("parallelize");
        Visibilis.debug("I:" + this.getInputAmt() + " | O:" + this.getOutputAmt());
        Visibilis.debug("eI:" + this.expansionInputs.size() + " | eO:" + this.expansionOutputs.size());
        
        this.expansionInputs.removeLast();
        for (int i = 1; i < this.expansionInputs.size(); ++i)
        {
            this.addDynamicOutput(this.createDynamicOutput());
        }
        
        Visibilis.debug("I:" + this.getInputAmt() + " | O:" + this.getOutputAmt());
        Visibilis.debug("eI:" + this.expansionInputs.size() + " | eO:" + this.expansionOutputs.size());
        Visibilis.debug("---");
    }
    
    @Override
    public void unparallelize()
    {
        Visibilis.debug("unparallelize");
        Visibilis.debug("I:" + this.getInputAmt() + " | O:" + this.getOutputAmt());
        Visibilis.debug("eI:" + this.expansionInputs.size() + " | eO:" + this.expansionOutputs.size());
        
        int size = this.expansionOutputs.size();
        for (int i = 1; i < size; ++i)
        {
            this.removeOutput(this.expansionOutputs.removeLast().getId());
        }
        this.expansionInputs.addLast(this.in2);
        
        Visibilis.debug("I:" + this.getInputAmt() + " | O:" + this.getOutputAmt());
        Visibilis.debug("eI:" + this.expansionInputs.size() + " | eO:" + this.expansionOutputs.size());
        Visibilis.debug("---");
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        A[] inputs = (A[]) new Object[this.expansionInputs.size()];
        
        if (this.parallelized)
        {
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
            
            this.values = (C[]) new Object[this.expansionOutputs.size()];
            
            i = 0;
            for (A a : inputs)
            {
                this.values[i++] = this.calculate(a, in2);
            }
        }
        else
        {
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
        }
        
        return true;
    }
    
    protected boolean canCalculate(A input, A in2) // Injective (Parallel)
    {
        return true;
    }
    
    protected abstract C calculate(A input, A in2); // Injective (Parallel)
    
    protected boolean canCalculate(A[] inputs) // Not Injective
    {
        return true;
    }
    
    protected abstract C calculate(A[] inputs); // Not Injective
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (this.parallelized)
        {
            int i = 0;
            for (Output<C> output : this.expansionOutputs)
            {
                if (output.getId() == index)
                {
                    return (B) this.values[i];
                }
                
                ++i;
            }
        }
        else
        {
            if (this.expansionOutputs.getFirst().getId() == index)
            {
                return (B) this.value;
            }
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
        return this.getOutDataType().getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return this.getOutDataType().getTextColor();
    }
}

package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.NodeParallelizable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericXP2<O, I> extends NodeParallelizable
{
    public LinkedList<Output<O>> expansionOutputs;
    public LinkedList<Input<I>> expansionInputs;
    
    public Input<I> in2;
    
    public O value;
    public O[] values;
    
    protected int inAmt;
    protected int outAmt;
    
    public NodeBiGenericXP2(NodeType<?> type)
    {
        super(type);
        this.expansionOutputs = new LinkedList<>();
        this.expansionInputs = new LinkedList<>();
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
    
    public abstract DataType<O> getOutDataType();
    
    public abstract DataType<I> getInDataType();
    
    public void addDynamicOutput(Output<O> out)
    {
        this.addOutput(out, this.getOutputAmt() - this.outAmt);
        this.expansionOutputs.addLast(out);
    }
    
    public Output<O> createDynamicOutput()
    {
        return new Output<>(this, this.getOutDataType(), "out1");
    }
    
    public void addDynamicInput(Input<I> in)
    {
        this.addInput(in, this.getInputAmt() - this.inAmt);
        this.expansionInputs.addLast(in);
    }
    
    public Input<I> createDynamicInput()
    {
        return new Input<>(this, this.getInDataType(), "in1");
    }
    
    @Override
    public void expand()
    {
        if(this.parallelized)
        {
            this.addDynamicOutput(this.createDynamicOutput());
        }
        
        this.addDynamicInput(this.createDynamicInput());
    }
    
    @Override
    public void shrink()
    {
        if(this.parallelized)
        {
            this.removeOutput(this.expansionOutputs.removeLast().getId());
        }
        
        this.removeInput(this.expansionInputs.removeLast().getId());
    }
    
    @Override
    public void parallelize()
    {
        this.expansionInputs.removeLast();
        for(int i = 1; i < this.expansionInputs.size(); ++i)
        {
            this.addDynamicOutput(this.createDynamicOutput());
        }
    }
    
    @Override
    public void unparallelize()
    {
        int size = this.expansionOutputs.size();
        for(int i = 1; i < size; ++i)
        {
            this.removeOutput(this.expansionOutputs.removeLast().getId());
        }
        this.expansionInputs.addLast(this.in2);
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        I[] inputs = VUtility.createGenericArray(this.expansionInputs.size());
        
        if(this.parallelized)
        {
            I in2 = this.in2.getValue();
            
            int i = 0;
            for(Input<I> input : this.expansionInputs)
            {
                inputs[i++] = input.getValue();
            }
            
            for(I a : inputs)
            {
                if(!this.canCalculate(a, in2))
                {
                    return false;
                }
            }
            
            this.values = VUtility.createGenericArray(this.expansionOutputs.size());
            
            i = 0;
            for(I a : inputs)
            {
                this.values[i++] = this.calculate(a, in2);
            }
        }
        else
        {
            int i = 0;
            for(Input<I> input : this.expansionInputs)
            {
                inputs[i++] = input.getValue();
            }
            
            if(!this.canCalculate(inputs))
            {
                return false;
            }
            
            this.value = this.calculate(inputs);
        }
        
        return true;
    }
    
    protected boolean canCalculate(I input, I in2) // Injective (Parallel)
    {
        return true;
    }
    
    protected abstract O calculate(I input, I in2); // Injective (Parallel)
    
    protected boolean canCalculate(I[] inputs) // Not Injective
    {
        return true;
    }
    
    protected abstract O calculate(I[] inputs); // Not Injective
    
    @Override
    public <A> A getOutputValue(Output<A> out)
    {
        if(this.parallelized)
        {
            int i = 0;
            for(Output<O> output : this.expansionOutputs)
            {
                if(output == out)
                {
                    return VUtility.cast(this.values[i]);
                }
                
                ++i;
            }
        }
        else
        {
            if(this.expansionOutputs.getFirst() == out)
            {
                return VUtility.cast(this.value);
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
    
    public static <O, I> NodeType<NodeBiGenericXP2<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculationX<O, I> functionX, ICalculationP2<O, I> functionP2)
    {
        return NodeBiGenericXP2.createType(dataTypeOut, dataTypeIn, id, functionX, functionP2, (inputs) -> true, (input, in2) -> true);
    }
    
    public static <O, I> NodeType<NodeBiGenericXP2<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculationX<O, I> functionX, ICalculationP2<O, I> functionP2, ICalculationRequirementX<O, I> requirementX, ICalculationRequirementP2<O, I> requirementP2)
    {
        return new NodeType<>((type) ->
        {
            return new NodeBiGenericXP2<O, I>(type)
            {
                @Override
                public DataType<O> getOutDataType()
                {
                    return dataTypeOut;
                }
                
                @Override
                public DataType<I> getInDataType()
                {
                    return dataTypeIn;
                }
                
                @Override
                protected boolean canCalculate(I[] inputs)
                {
                    return requirementX.canCalculate(inputs);
                }
                
                @Override
                protected O calculate(I[] inputs)
                {
                    return functionX.calculate(inputs);
                }
                
                @Override
                protected boolean canCalculate(I input, I in2)
                {
                    return requirementP2.canCalculate(input, in2);
                }
                
                @Override
                protected O calculate(I input, I in2)
                {
                    return functionP2.calculate(input, in2);
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
    
    public static interface ICalculationRequirementX<O, I>
    {
        boolean canCalculate(I[] inputs);
    }
    
    public static interface ICalculationX<O, I>
    {
        O calculate(I[] inputs);
    }
    
    public static interface ICalculationRequirementP2<O, I>
    {
        boolean canCalculate(I input, I in2);
    }
    
    public static interface ICalculationP2<O, I>
    {
        O calculate(I input, I in2);
    }
}

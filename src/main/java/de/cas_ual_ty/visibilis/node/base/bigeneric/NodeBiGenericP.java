package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.LinkedList;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.data.DataProvider;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericP<O, I> extends NodeExpandable
{
    public LinkedList<Output<O>> expansionOutputs;
    public LinkedList<Input<I>> expansionInputs;
    
    public O[] values;
    
    protected int inAmt;
    protected int outAmt;
    
    public NodeBiGenericP(NodeType<?> type)
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
        this.countBaseFields();
        
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
    public boolean doCalculate(DataProvider context)
    {
        I[] inputs = this.getInDataType().createArray(this.expansionInputs.size());
        
        int i = 0;
        for(Input<I> input : this.expansionInputs)
        {
            inputs[i++] = input.getValue();
        }
        
        for(I a : inputs)
        {
            if(!this.canCalculate(a))
            {
                return false;
            }
        }
        
        this.values = this.getOutDataType().createArray(this.expansionOutputs.size());
        
        i = 0;
        for(I a : inputs)
        {
            this.values[i++] = this.calculate(a);
        }
        
        return true;
    }
    
    protected boolean canCalculate(I input)
    {
        return true;
    }
    
    protected abstract O calculate(I input);
    
    @Override
    public <H> H getOutputValue(Output<H> out)
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
    
    public static <O, I> NodeType.IFactory<NodeBiGenericP<O, I>> createTypeBiGenericP(DataType<O> dataTypeOut, DataType<I> dataTypeIn, Function<I, O> function)
    {
        return NodeBiGenericP.createTypeBiGenericP(dataTypeOut, dataTypeIn, function, (input) -> true);
    }
    
    public static <O, I> NodeType.IFactory<NodeBiGenericP<O, I>> createTypeBiGenericP(DataType<O> dataTypeOut, DataType<I> dataTypeIn, Function<I, O> function, Function<I, Boolean> requirement)
    {
        return (type) ->
        {
            return new NodeBiGenericP<O, I>(type)
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
                protected boolean canCalculate(I input)
                {
                    return requirement.apply(input);
                }
                
                @Override
                protected O calculate(I input)
                {
                    return function.apply(input);
                }
            };
        };
    }
}

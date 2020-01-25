package de.cas_ual_ty.visibilis.node.base.trigeneric;

import java.util.LinkedList;
import java.util.function.BiFunction;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeTriGenericP2<O, I1, I2> extends NodeExpandable
{
    public LinkedList<Output<O>> expansionOutputs;
    public LinkedList<Input<I1>> expansionInputs;
    
    public O[] values;
    
    protected int inAmt;
    protected int outAmt;
    
    public Input<I2> in2;
    
    public NodeTriGenericP2(NodeType<?> type)
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
        this.addInput(this.in2 = new Input<>(this, this.getIn2DataType(), "in2"));
        this.countBaseFields();
        this.expand();
    }
    
    public abstract DataType<O> getOutDataType();
    
    public abstract DataType<I1> getIn1DataType();
    
    public abstract DataType<I2> getIn2DataType();
    
    public void addDynamicOutput(Output<O> out)
    {
        this.addOutput(out, this.getOutputAmt() - this.outAmt);
        this.expansionOutputs.addLast(out);
    }
    
    public Output<O> createDynamicOutput()
    {
        return new Output<>(this, this.getOutDataType(), "out1");
    }
    
    public void addDynamicInput(Input<I1> in)
    {
        this.addInput(in, this.getInputAmt() - this.inAmt);
        this.expansionInputs.addLast(in);
    }
    
    public Input<I1> createDynamicInput()
    {
        return new Input<>(this, this.getIn1DataType(), "in1");
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
    public boolean doCalculate(ExecContext context)
    {
        I1[] inputs = VUtility.createGenericArray(this.expansionInputs.size());
        I2 in2 = this.in2.getValue();
        
        int i = 0;
        for(Input<I1> input : this.expansionInputs)
        {
            inputs[i++] = input.getValue();
        }
        
        for(I1 a : inputs)
        {
            if(!this.canCalculate(a, in2))
            {
                return false;
            }
        }
        
        this.values = VUtility.createGenericArray(this.expansionOutputs.size());
        
        i = 0;
        for(I1 a : inputs)
        {
            this.values[i++] = this.calculate(a, in2);
        }
        
        return true;
    }
    
    protected boolean canCalculate(I1 input, I2 in2)
    {
        return true;
    }
    
    protected abstract O calculate(I1 input, I2 in2);
    
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
    
    public static <O, I1, I2> NodeType<NodeTriGenericP2<O, I1, I2>> createTypeTriGenericP2(DataType<O> dataTypeOut, DataType<I1> dataTypeIn1, DataType<I2> dataTypeIn2, String id, BiFunction<I1, I2, O> function)
    {
        return NodeTriGenericP2.createTypeTriGenericP2(dataTypeOut, dataTypeIn1, dataTypeIn2, id, function, (input, in2) -> true);
    }
    
    public static <O, I1, I2> NodeType<NodeTriGenericP2<O, I1, I2>> createTypeTriGenericP2(DataType<O> dataTypeOut, DataType<I1> dataTypeIn1, DataType<I2> dataTypeIn2, String id, BiFunction<I1, I2, O> function, BiFunction<I1, I2, Boolean> requirement)
    {
        return new NodeType<>((type) ->
        {
            return new NodeTriGenericP2<O, I1, I2>(type)
            {
                @Override
                public DataType<O> getOutDataType()
                {
                    return dataTypeOut;
                }
                
                @Override
                public DataType<I1> getIn1DataType()
                {
                    return dataTypeIn1;
                }
                
                @Override
                public DataType<I2> getIn2DataType()
                {
                    return dataTypeIn2;
                }
                
                @Override
                protected boolean canCalculate(I1 input, I2 in2)
                {
                    return requirement.apply(input, in2);
                }
                
                @Override
                protected O calculate(I1 input, I2 in2)
                {
                    return function.apply(input, in2);
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
}

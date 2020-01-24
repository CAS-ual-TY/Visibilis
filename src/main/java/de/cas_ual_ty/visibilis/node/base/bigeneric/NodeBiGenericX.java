package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeBiGenericX<O, I> extends NodeExpandable
{
    public LinkedList<Input<I>> expansionInputs;
    
    public Output<O> out1O;
    
    public O value;
    
    protected int inAmt;
    
    public NodeBiGenericX(NodeType<?> type)
    {
        super(type);
        this.expansionInputs = new LinkedList<>();
        this.createBaseFields();
    }
    
    public void countBaseFields()
    {
        this.inAmt = this.getInputAmt();
    }
    
    public void createBaseFields()
    {
        this.addOutput(this.out1O = new Output<>(this, this.getOutDataType(), "out1"));
        
        this.countBaseFields();
        
        this.expand();
        this.expand();
    }
    
    public abstract DataType<O> getOutDataType();
    
    public abstract DataType<I> getInDataType();
    
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
        this.addDynamicInput(this.createDynamicInput());
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.expansionInputs.removeLast().getId());
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        I[] inputs = VUtility.createGenericArray(this.expansionInputs.size());
        
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
        
        return true;
    }
    
    protected boolean canCalculate(I[] inputs)
    {
        return true;
    }
    
    protected abstract O calculate(I[] inputs);
    
    @Override
    public <A> A getOutputValue(Output<A> out)
    {
        return out == this.out1O ? VUtility.cast(this.value) : null;
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
    
    public static <O, I> NodeType<NodeBiGenericX<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculation<O, I> function)
    {
        return NodeBiGenericX.createType(dataTypeOut, dataTypeIn, id, function, (input) -> true);
    }
    
    public static <O, I> NodeType<NodeBiGenericX<O, I>> createType(DataType<O> dataTypeOut, DataType<I> dataTypeIn, String id, ICalculation<O, I> function, ICalculationRequirement<O, I> requirement)
    {
        return new NodeType<>((type) ->
        {
            return new NodeBiGenericX<O, I>(type)
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
                    return requirement.canCalculate(inputs);
                }
                
                @Override
                protected O calculate(I[] inputs)
                {
                    return function.calculate(inputs);
                }
                
                @Override
                public String getID()
                {
                    return id;
                }
            };
        });
    }
    
    public static interface ICalculationRequirement<O, I>
    {
        boolean canCalculate(I[] inputs);
    }
    
    public static interface ICalculation<O, I>
    {
        O calculate(I[] inputs);
    }
}

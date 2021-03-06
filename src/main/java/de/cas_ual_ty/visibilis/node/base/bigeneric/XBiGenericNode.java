package de.cas_ual_ty.visibilis.node.base.bigeneric;

import java.util.LinkedList;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.ExpandableNode;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class XBiGenericNode<O, I> extends ExpandableNode
{
    public LinkedList<Input<I>> expansionInputs;
    
    public Output<O> out1O;
    
    public O value;
    
    protected int inAmt;
    
    public XBiGenericNode(NodeType<?> type)
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
    public boolean doCalculate(DataProvider context)
    {
        I[] inputs = this.getInDataType().createArray(this.expansionInputs.size());
        
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
    
    public static <O, I> NodeType.IFactory<XBiGenericNode<O, I>> createTypeBiGenericX(DataType<O> dataTypeOut, DataType<I> dataTypeIn, Function<I[], O> function)
    {
        return XBiGenericNode.createTypeBiGenericX(dataTypeOut, dataTypeIn, function, (inputs) -> true);
    }
    
    public static <O, I> NodeType.IFactory<XBiGenericNode<O, I>> createTypeBiGenericX(DataType<O> dataTypeOut, DataType<I> dataTypeIn, Function<I[], O> function, Function<I[], Boolean> requirement)
    {
        return (type) ->
        {
            return new XBiGenericNode<O, I>(type)
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
                    return requirement.apply(inputs);
                }
                
                @Override
                protected O calculate(I[] inputs)
                {
                    return function.apply(inputs);
                }
            };
        };
    }
}

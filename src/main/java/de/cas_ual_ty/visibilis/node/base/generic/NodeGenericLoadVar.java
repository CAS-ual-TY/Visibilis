package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeGenericLoadVar<A> extends Node
{
    public final Output<A> out1Var;
    public final Input<String> in1String;
    
    protected A value;
    
    public NodeGenericLoadVar(NodeType<?> type)
    {
        super(type);
        
        this.addOutput(this.out1Var = new Output<>(this, this.getDataType(), "out1"));
        this.addInput(this.in1String = new Input<>(this, VDataTypes.STRING, "in1"));
    }
    
    public abstract DataType<A> getDataType();
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        String key = this.in1String.getValue();
        this.value = context.getPrint().getVariable(this.getDataType(), key);
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out1Var ? VUtility.cast(this.value) : null;
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
    
    public static <I> NodeType.IFactory<NodeGenericLoadVar<I>> createTypeGenericLoadVar(DataType<I> dataType)
    {
        return (type) ->
        {
            return new NodeGenericLoadVar<I>(type)
            {
                @Override
                public DataType<I> getDataType()
                {
                    return dataType;
                }
            };
        };
    }
}

package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public abstract class NodeGenericSaveVar<A> extends Node
{
    public final Output<Object> out1Exec;
    public final Input<Object> in1Exec;
    public final Input<A> in2Var;
    public final Input<String> in3String;
    
    public NodeGenericSaveVar(NodeType<?> type)
    {
        super(type);
        
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2Var = new Input<>(this, this.getDataType(), "in2"));
        this.addInput(this.in3String = new Input<>(this, VDataTypes.STRING, "in3"));
    }
    
    public abstract DataType<A> getDataType();
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        A value = this.in2Var.getValue();
        String key = this.in3String.getValue();
        
        context.getPrint().putVariable(this.getDataType(), key, value);
        return true;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1Exec : null;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return null;
    }
    
    public static <I> NodeType.IFactory<NodeGenericSaveVar<I>> createTypeGenericSaveVar(DataType<I> dataType)
    {
        return (type) ->
        {
            return new NodeGenericSaveVar<I>(type)
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

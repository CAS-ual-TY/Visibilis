package de.cas_ual_ty.visibilis.node.base.generic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class NodeGenericLoadVarOptional<A> extends Node
{
    public final Output<Object> out1Exec;
    public final Output<A> out2Var;
    public final Output<Object> out3Exec;
    public final Input<Object> in1Exec;
    public final Input<String> in2String;
    
    protected A value;
    
    public NodeGenericLoadVarOptional(NodeType<?> type)
    {
        super(type);
        
        this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1");
        this.out2Var = new Output<>(this, this.getDataType(), "out2");
        this.out3Exec = new Output<>(this, VDataTypes.EXEC, "out3");
        this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1");
        this.in2String = new Input<>(this, VDataTypes.STRING, "in2");
    }
    
    public abstract DataType<A> getDataType();
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        String key = this.in2String.getValue();
        this.value = context.getPrint().getVariable(this.getDataType(), key);
        return true;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? (this.value != null ? this.out1Exec : this.out3Exec) : null;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out2Var ? VUtility.cast(this.value) : null;
    }
    
    public static <I> NodeType.IFactory<NodeGenericLoadVarOptional<I>> createTypeGenericLoadVarOptional(DataType<I> dataType)
    {
        return (type) ->
        {
            return new NodeGenericLoadVarOptional<I>(type)
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

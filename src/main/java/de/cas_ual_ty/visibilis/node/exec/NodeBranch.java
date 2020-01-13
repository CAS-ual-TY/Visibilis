package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeBranch extends Node implements INodeExec
{
    public final Output<Object> out1Exec;
    public final Output<Object> out2Exec;
    public final Input<Object> in1Exec;
    public final Input<Boolean> in2Boolean;
    
    public boolean value;
    
    public NodeBranch()
    {
        super();
        this.addOutput(this.out1Exec = new Output<>(this, DataType.EXEC, "out1"));
        this.addOutput(this.out2Exec = new Output<>(this, DataType.EXEC, "out2"));
        this.addInput(this.in1Exec = new Input<>(this, DataType.EXEC, "in1"));
        this.addInput(this.in2Boolean = new Input<>(this, DataType.BOOLEAN, "in2"));
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? (this.value ? this.out1Exec : this.out2Exec) : null;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.value = this.in2Boolean.getValue();
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return null;
    }
    
    @Override
    public String getID()
    {
        return "branch";
    }
}

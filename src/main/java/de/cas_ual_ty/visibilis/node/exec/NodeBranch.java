package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeBranch extends Node implements INodeExec
{
    public final Output outExec1;
    public final Output outExec2;
    public final Input inExec;
    public final Input<Boolean> inBoolean;
    
    public boolean value;
    
    public NodeBranch()
    {
        super();
        this.addOutput(this.outExec1 = new Output(this, DataType.EXEC, "out1"));
        this.addOutput(this.outExec2 = new Output(this, DataType.EXEC, "out2"));
        this.addInput(this.inExec = new Input(this, DataType.EXEC, "in1"));
        this.addInput(this.inBoolean = new Input<>(this, DataType.BOOLEAN, "in2"));
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? (this.value ? this.outExec1 : this.outExec2) : null;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.value = this.inBoolean.getValue();
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return null;
    }
    
    @Override
    public String getID()
    {
        return "branch";
    }
}

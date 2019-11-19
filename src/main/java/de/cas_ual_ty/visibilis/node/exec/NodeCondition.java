package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeCondition extends NodeExec
{
    public final Output outExec1;
    public final Output outExec2;
    public final Input inExec;
    public final Input<Boolean> inBoolean;
    
    public boolean value;
    
    public NodeCondition(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec1 = new Output(this, DataType.EXEC, "exec");
        this.outExec2 = new Output(this, DataType.EXEC, "exec");
        this.inExec = new Input(this, DataType.EXEC, "exec");
        this.inBoolean = new Input<>(this, DataType.BOOLEAN, "boolean");
    }
    
    public NodeCondition(int inputAmt)
    {
        this(2, inputAmt);
    }
    
    public NodeCondition()
    {
        this(2);
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
        return "condition";
    }
}

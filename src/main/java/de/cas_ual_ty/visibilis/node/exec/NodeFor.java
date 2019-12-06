package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeFor extends Node implements INodeExec
{
    public final Output outExec1;
    public final Output<Integer> outIndex2;
    public final Output outExec3;
    public final Input inExec;
    public final Input<Integer> inLength;
    
    public int length;
    public int value;
    
    public NodeFor()
    {
        super();
        this.addOutput(this.outExec1 = new Output(this, DataType.EXEC, "out1"));
        this.addOutput(this.outIndex2 = new Output(this, DataType.INTEGER, "out2"));
        this.addOutput(this.outExec3 = new Output(this, DataType.EXEC, "out3"));
        this.addInput(this.inExec = new Input(this, DataType.EXEC, "in1"));
        this.addInput(this.inLength = new Input<>(this, DataType.INTEGER, "in2"));
    }
    
    @Override
    public Output getOutExec(int index)
    {
        this.value = index;
        return index == this.length ? this.outExec3 : (index < this.length ? this.outExec1 : null);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.length = this.inLength.getValue();
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return index == 1 ? (B) (Integer) this.value : null;
    }
    
    @Override
    public String getID()
    {
        return "for";
    }
}

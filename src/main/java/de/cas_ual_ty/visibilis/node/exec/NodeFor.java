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
    
    public NodeFor(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec1 = new Output(this, DataType.EXEC, "out1");
        this.outIndex2 = new Output(this, DataType.INTEGER, "out2");
        this.outExec3 = new Output(this, DataType.EXEC, "out3");
        this.inExec = new Input(this, DataType.EXEC, "in1");
        this.inLength = new Input<>(this, DataType.INTEGER, "in2");
    }
    
    public NodeFor(int inputAmt)
    {
        this(2, inputAmt);
    }
    
    public NodeFor()
    {
        this(2);
    }
    
    @Override
    public Output getOutExec(int index)
    {
        this.value = index;
        return index == this.length ? this.outIndex2 : (index < this.length ? this.outExec1 : null);
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
        return index == 2 ? (B) (Integer) this.value : null;
    }
    
    @Override
    public String getID()
    {
        return "for";
    }
}

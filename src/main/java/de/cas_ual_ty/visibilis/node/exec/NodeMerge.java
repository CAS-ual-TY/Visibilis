package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeMerge extends NodeExpandable implements INodeExec
{
    public final Output outExec1;
    
    public NodeMerge(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec1 = new Output(this, DataType.EXEC, "out1");
        new Input(this, DataType.EXEC, "in");
        new Input(this, DataType.EXEC, "in");
    }
    
    public NodeMerge(int inputAmt)
    {
        this(2, inputAmt);
    }
    
    public NodeMerge()
    {
        this(2);
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.outExec1 : null;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
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
        return "merge";
    }
    
    @Override
    public boolean canExpand()
    {
        return true;
    }
    
    @Override
    public void expand()
    {
        new Input<>(this, DataType.EXEC, "in");
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.getInput(this.getInputAmt() - 1));
    }
}
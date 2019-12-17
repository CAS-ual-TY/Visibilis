package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.node.general.NodeExpandable;

public class NodeMerge extends NodeExpandable implements INodeExec
{
    public final Output outExec1;
    
    public NodeMerge()
    {
        super();
        this.addOutput(this.outExec1 = new Output(this, DataType.EXEC, "out1"));
        this.addInput(new Input(this, DataType.EXEC, "in1"));
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
    public Input createDynamicInput()
    {
        return new Input(this, DataType.EXEC, "in1");
    }
}

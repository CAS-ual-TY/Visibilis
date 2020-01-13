package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.NBTUtility;

public class VNodePrintDebug extends Node implements INodeExec
{
    public Output<Object> out1Exec;
    public Input<Object> in1Exec;
    
    public VNodePrintDebug()
    {
        super();
        this.addOutput(this.out1Exec = new Output<>(this, DataType.EXEC, "out1"));
        this.addInput(this.in1Exec = new Input<>(this, DataType.EXEC, "in1"));
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1Exec : null;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        NBTUtility.printTree(NBTUtility.savePrintToNBT(provider.getPrint()));
        
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
        return "vprintdebug";
    }
}

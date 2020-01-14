package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;

public class NodeFor extends Node implements INodeExec
{
    public final Output<Object> out1Exec;
    public final Output<Integer> out2Index;
    public final Output<Object> out3Exec;
    public final Input<Object> in1Exec;
    public final Input<Integer> in2Length;
    
    public int length;
    public int value;
    
    public NodeFor()
    {
        super();
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(this.out2Index = new Output<>(this, VDataTypes.INTEGER, "out2"));
        this.addOutput(this.out3Exec = new Output<>(this, VDataTypes.EXEC, "out3"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2Length = new Input<>(this, VDataTypes.INTEGER, "in2"));
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        this.value = index;
        return index == this.length ? this.out3Exec : (index < this.length ? this.out1Exec : null);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.length = this.in2Length.getValue();
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out2Index ? VUtility.cast(this.value) : null;
    }
    
    @Override
    public String getID()
    {
        return "for";
    }
}

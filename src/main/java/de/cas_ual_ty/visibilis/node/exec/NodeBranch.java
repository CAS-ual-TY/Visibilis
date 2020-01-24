package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeBranch extends Node
{
    public final Output<Object> out1Exec;
    public final Output<Object> out2Exec;
    public final Input<Object> in1Exec;
    public final Input<Boolean> in2Boolean;
    
    public boolean value;
    
    public NodeBranch(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(this.out2Exec = new Output<>(this, VDataTypes.EXEC, "out2"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2Boolean = new Input<>(this, VDataTypes.BOOLEAN, "in2"));
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? (this.value ? this.out1Exec : this.out2Exec) : null;
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
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
    public float[] getColor()
    {
        return VDataTypes.EXEC.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.EXEC.getTextColor();
    }
    
    @Override
    public String getID()
    {
        return "branch";
    }
}

package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeWhile extends Node
{
    public final Output<Object> out2Exec;
    public final Output<Object> out1Exec;
    public final Input<Object> in1Exec;
    public final Input<Boolean> in2Condition;
    
    public boolean condition;
    public boolean exec2;
    
    public NodeWhile(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(this.out2Exec = new Output<>(this, VDataTypes.EXEC, "out2").setTriggerRecalculation());
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2Condition = new Input<>(this, VDataTypes.BOOLEAN, "in2").setTriggerRecalculation());
        this.exec2 = false;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        this.condition = this.in2Condition.getValue();
        
        if(this.exec2)
        {
            return null;
        }
        else if(this.condition)
        {
            return this.out2Exec;
        }
        else
        {
            this.exec2 = true;
            return this.out1Exec;
        }
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
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
    public void resetValues()
    {
        super.resetValues();
        this.exec2 = false;
    }
}

package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeWhile extends Node implements INodeExec
{
    public final Output outExec1;
    public final Output outExec2;
    public final Input inExec;
    public final Input<Boolean> inCondition;
    
    public boolean condition;
    public boolean exec2;
    
    public NodeWhile()
    {
        super();
        this.outExec1 = new Output(this, DataType.EXEC, "out1");
        this.outExec2 = new Output(this, DataType.EXEC, "out2");
        this.inExec = new Input(this, DataType.EXEC, "in1");
        this.inCondition = new Input<>(this, DataType.BOOLEAN, "in2");
        this.exec2 = false;
    }
    
    @Override
    public Output getOutExec(int index)
    {
        if (this.exec2)
        {
            return null;
        }
        else if (this.condition)
        {
            this.triggerRecalculation(this.inCondition);
            return this.outExec1;
        }
        else
        {
            this.exec2 = true;
            return this.outExec2;
        }
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.condition = this.inCondition.getValue();
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
        return "while";
    }
    
    @Override
    public void resetValues()
    {
        super.resetValues();
        this.exec2 = false;
    }
}
